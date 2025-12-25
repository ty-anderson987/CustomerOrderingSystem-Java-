import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The type Customer gui.
 */
public class CustomerGUI {

    private final Stage stage;
    private final Customer customer;
    private final CustomerOrderSystem cos;

    private final CustomerOrderSystemUserInterfaceController uiController;
    private final CustomerController customerController;

    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    private final ListView<CartItem> cartListView = new ListView<>(cartItems);
    private final TextArea ordersTextArea = new TextArea();

    private final ToggleGroup deliveryToggle = new ToggleGroup();

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer gui.
     *
     * @param stage    the stage
     * @param customer the customer
     * @param cos      the cos
     */
    public CustomerGUI(Stage stage, Customer customer, CustomerOrderSystem cos) {
        this.stage = stage;
        this.customer = customer;
        this.cos = cos;
        this.uiController = new CustomerOrderSystemUserInterfaceController(cos, null);
        this.customerController = new CustomerController(uiController);
    }

// ------------------- Utilities -------------------
    /**
     * Show customer menu.
     */
    public void showCustomerMenu() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // ------------------- Top: Delivery Method + Logout -------------------
        HBox topBox = new HBox(20);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER_LEFT);

        Label deliveryLabel = new Label("Delivery Method:");

        RadioButton mailButton = new RadioButton("Mail");
        RadioButton pickupButton = new RadioButton("In-Store Pickup");
        mailButton.setToggleGroup(deliveryToggle);
        pickupButton.setToggleGroup(deliveryToggle);
        mailButton.setSelected(true);

        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(e -> stage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBox.getChildren().addAll(deliveryLabel, mailButton, pickupButton, spacer, logoutButton);
        root.setTop(topBox);

        // ------------------- Left: Product List -------------------
        VBox productBox = new VBox(10);
        productBox.setPadding(new Insets(10));
        productBox.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        Label productLabel = new Label("Available Products:");

        ListView<Product> productListView = new ListView<>();
        productListView.setItems(FXCollections.observableArrayList(cos.getProducts()));

        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity / weight");

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> addToCart(productListView, quantityField));
        quantityField.setOnAction(e -> addToCartButton.fire());

        productBox.getChildren().addAll(productLabel, productListView, quantityField, addToCartButton);
        root.setLeft(productBox);

        // ------------------- Center: Cart -------------------
        VBox cartBox = new VBox(10);
        cartBox.setPadding(new Insets(10));
        cartBox.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        Label cartLabel = new Label("My Cart:");

        cartListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getProduct().getProductName() + " x " + item.getQuantity());
            }
        });

        Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(e -> {
            CartItem selected = cartListView.getSelectionModel().getSelectedItem();
            if (selected != null) cartItems.remove(selected);
        });

        Button submitOrderButton = new Button("Submit Order");
        submitOrderButton.setOnAction(e -> submitOrder());

        cartBox.getChildren().addAll(cartLabel, cartListView, removeButton, submitOrderButton);
        root.setCenter(cartBox);

        // ------------------- Right: Orders -------------------
        VBox ordersBox = new VBox(10);
        ordersBox.setPadding(new Insets(10));
        ordersBox.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        Label ordersLabel = new Label("My Orders:");

        ordersTextArea.setEditable(false);
        Button refreshOrdersButton = new Button("Refresh Orders");
        refreshOrdersButton.setOnAction(e -> refreshOrders());

        ordersBox.getChildren().addAll(ordersLabel, ordersTextArea, refreshOrdersButton);
        root.setRight(ordersBox);

        // ------------------- Scene -------------------
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Customer Portal - " + customer.getCustomerName());
        stage.show();

        refreshOrders();
    }

    private void addToCart(ListView<Product> productListView, TextField quantityField) {
        Product selected = productListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a product first!").showAndWait();
            return;
        }

        String text = quantityField.getText().trim();
        if (text.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Enter a quantity or weight!").showAndWait();
            return;
        }

        try {
            if (selected instanceof WeightedProducts) {
                double weight = Double.parseDouble(text.replace(",", "."));
                if (weight <= 0) throw new NumberFormatException();
                cartItems.add(new CartItem(selected, weight));
            } else { // QuantityProducts
                int quantity = Integer.parseInt(text);
                if (quantity <= 0) throw new NumberFormatException();
                cartItems.add(new CartItem(selected, quantity));
            }
            quantityField.clear();
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.WARNING, "Enter a valid positive number!").showAndWait();
        }
    }

    private void submitOrder() {
        if (cartItems.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty!").showAndWait();
            return;
        }

        try {
            Cart cart = new Cart();
            for (CartItem item : cartItems) {
                if (item.getWeight() > 0) {
                    cart.addItem(new CartItem(item.getProduct(), item.getWeight()));
                } else {
                    cart.addItem(new CartItem(item.getProduct(), item.getQuantity()));
                }
            }

            ChaseBank chaseBank = ChaseBank.getInstance();
            ChaseBankCustomer bankCustomer = chaseBank.getChaseBankCustomerByCustomer(customer);
            if (bankCustomer == null) {
                new Alert(Alert.AlertType.WARNING, "No bank account linked!").showAndWait();
                return;
            }

            Toggle selectedToggle = deliveryToggle.getSelectedToggle();
            if (selectedToggle == null) {
                new Alert(Alert.AlertType.WARNING, "Select a delivery method!").showAndWait();
                return;
            }
            String deliveryMethod = ((RadioButton) selectedToggle).getText();

            Order order = uiController.getOrderController()
                    .createSubmitOrderController(cart, customer, bankCustomer, deliveryMethod);

            cos.addOrder(order);
            cartItems.clear();
            refreshOrders();

            new Alert(Alert.AlertType.INFORMATION, "Order submitted! ID: " + order.getOrderID()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Order submission failed: " + e.getMessage()).showAndWait();
        }
    }

    private void refreshOrders() {
        ordersTextArea.clear();
        for (Order order : cos.getCustomerOrders(customer)) {
            ordersTextArea.appendText(order.toUIString(customer) + "\n\n");
        }
    }
}
