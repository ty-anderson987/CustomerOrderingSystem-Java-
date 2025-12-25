import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The type Customer order system gui.
 */
public class CustomerOrderSystemGUI {
    private final CustomerOrderSystem customerOrderSystem;
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer order system gui.
     *
     * @param customerOrderSystem the customer order system
     */
    public CustomerOrderSystemGUI(CustomerOrderSystem customerOrderSystem) {
        this.customerOrderSystem = customerOrderSystem;

        this.customerOrderSystemUserInterfaceController = new CustomerOrderSystemUserInterfaceController(customerOrderSystem, null);
    }



    /**
     * Start.
     *
     * @param primaryStage the primary stage
     */
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Welcome to Customer Order System GUI");

        Button customerLoginButton = new Button("COS Customer Login");
        customerLoginButton.setOnAction(e -> openCustomerLoginWindow());

        Button customerCreateAccountButton = new Button("COS Customer Create Account");
        customerCreateAccountButton.setOnAction(e -> openCustomerCreateAccountWindow());

        Button chaseBankLoginButton = new Button("Chase Bank Login (Not working currently)");
        chaseBankLoginButton.setOnAction(e -> openChaseBankCustomerBankLoginWindow());

        Button chaseBankCreateAccountButton = new Button("Chase Bank Create Account (Not working currently)");
        chaseBankCreateAccountButton.setOnAction(e -> openChaseBankCustomerCreateAccountWindow());

        Button adminLoginButton = new Button("Admin Login (Not working currently)");
        adminLoginButton.setOnAction(e -> openAdminLogonWindow());

        Button fullConsoleButton = new Button("Switch to Full Console UI");
        fullConsoleButton.setOnAction(e -> {
            Stage stage = (Stage) fullConsoleButton.getScene().getWindow();
            stage.close();

            CustomerOrderSystemUserInterface ui =
                    new CustomerOrderSystemUserInterface(customerOrderSystem);
            ui.cosMainUI();
        });

        // --- Exit Button ---
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> Platform.exit()); // closes entire app

        VBox vbox = new VBox(10, titleLabel, customerLoginButton, customerCreateAccountButton,
                chaseBankLoginButton, chaseBankCreateAccountButton, adminLoginButton,
                fullConsoleButton, exitButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setTitle("Customer Order System GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



// ------------------- Utilities -------------------
    private void openCustomerLoginWindow() {
        Stage loginStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField securityAnswerField = new TextField();

        Button idSubmitButton = new Button("Submit ID");
        Button passwordSubmitButton = new Button("Submit Password");
        Button securitySubmitButton = new Button("Submit Answer");
        Label messageLabel = new Label();

        vbox.getChildren().addAll(new Label("Customer ID"), idField, idSubmitButton, messageLabel);

        final Customer[] selectedCustomer = {null};
        final int[] attempts = {0};

        // --- ID Submit ---
        idSubmitButton.setOnAction(e -> {
            String customerID = idField.getText().trim();
            if (customerID.equals("-1")) {
                loginStage.close();
                return;
            }

            selectedCustomer[0] = null;
            for (Customer customer : customerOrderSystem.getCustomers()) {
                if (customerID.equalsIgnoreCase(customer.getCustomerID().trim())) {
                    selectedCustomer[0] = customer;
                    break;
                }
            }

            if (selectedCustomer[0] == null) {
                messageLabel.setText("No account with that ID");
            } else {
                // Move to password entry
                messageLabel.setText("");
                vbox.getChildren().clear();
                vbox.getChildren().addAll(new Label("Password"), passwordField, passwordSubmitButton, messageLabel);
            }
        });

        // --- Password Submit ---
        passwordSubmitButton.setOnAction(e -> {
            if (selectedCustomer[0] == null) return;

            String password = passwordField.getText().trim();
            if (password.equals("-1")) {
                loginStage.close();
                return;
            }

            if (password.equals(selectedCustomer[0].getCustomerPassword())) {
                // Correct password → show security question
                vbox.getChildren().clear();
                vbox.getChildren().addAll(
                        new Label("Security Question: " + selectedCustomer[0].getCustomerSecurityQuestion()),
                        securityAnswerField, securitySubmitButton, messageLabel
                );
                messageLabel.setText("");
            } else {
                attempts[0]++;
                if (attempts[0] >= 3) {
                    messageLabel.setText("Too many failed attempts. Login failed.");
                    passwordSubmitButton.setDisable(true);
                } else {
                    messageLabel.setText("Password incorrect. Attempts left: " + (3 - attempts[0]));
                }
            }
        });

        // --- Security Answer Submit ---
        securitySubmitButton.setOnAction(e -> {
            if (selectedCustomer[0] == null) return;

            String answer = securityAnswerField.getText().trim();
            if (answer.equals("-1")) {
                loginStage.close();
                return;
            }

            if (answer.equalsIgnoreCase(selectedCustomer[0].getCustomerSecurityAnswer())) {
                messageLabel.setText("Login successful! Welcome " + selectedCustomer[0].getCustomerName());
                loginStage.close();
                openCustomerMenu(selectedCustomer[0]);
            } else {
                messageLabel.setText("Security answer incorrect. Login failed.");
            }
        });

        loginStage.setScene(new Scene(vbox, 400, 250));
        loginStage.setTitle("Customer Login");
        loginStage.show();
    }

    private void openCustomerCreateAccountWindow() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label messageLabel = new Label();

        // Input fields
        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        TextField cardField = new TextField();
        TextField securityAnswerField = new TextField();

        ComboBox<String> securityQuestionBox = new ComboBox<>();
        securityQuestionBox.getItems().addAll(
                "What is your favorite color?",
                "What is your mother's maiden name?",
                "What city were you born in?",
                "What is your first pet's name?"
        );

        // Buttons
        Button submitIDButton = new Button("Submit ID");
        Button submitPasswordButton = new Button("Submit Password");
        Button submitInfoButton = new Button("Submit Info");
        Button submitCardButton = new Button("Submit Card");
        Button submitSecurityButton = new Button("Submit Security Info");

        vbox.getChildren().addAll(new Label("Enter Customer ID"), idField, submitIDButton, messageLabel);

        final String[] customerID = {null};
        final String[] customerPassword = {null};
        final String[] customerName = {null};
        final String[] customerAddress = {null};
        final String[] cardNumber = {null};

        // --- STEP 1: SUBMIT ID ---
        submitIDButton.setOnAction(e -> {
            String id = idField.getText().trim();
            if (id.equals("-1")) {
                stage.close();
                return;
            }

            // Check duplicate
            boolean exists = customerOrderSystem.getCustomers().stream()
                    .anyMatch(c -> c.getCustomerID().equals(id));

            if (exists) {
                messageLabel.setText("ID already exists. Enter another ID.");
                return;
            }

            customerID[0] = id;

            // Move to password
            messageLabel.setText("");
            vbox.getChildren().clear();
            vbox.getChildren().addAll(
                    new Label("Password must be 6+ chars, contain UPPERCASE, digit, special symbol (@,#,$,%,&,*)"),
                    passwordField, submitPasswordButton, messageLabel
            );
        });

        // --- STEP 2: SUBMIT PASSWORD ---
        submitPasswordButton.setOnAction(e -> {
            String pass = passwordField.getText().trim();

            if (pass.equals("-1")) {
                stage.close();
                return;
            }

            if (!Customer.isValidPassword(pass)) {
                messageLabel.setText("Invalid password. Try again.");
                return;
            }

            customerPassword[0] = pass;

            // Move to basic info
            vbox.getChildren().clear();
            vbox.getChildren().addAll(
                    new Label("Enter Name (First Last)"), nameField,
                    new Label("Enter Address"), addressField,
                    submitInfoButton, messageLabel
            );
            messageLabel.setText("");
        });

        // --- STEP 3: NAME & ADDRESS ---
        submitInfoButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();

            if (name.equals("-1") || address.equals("-1")) {
                stage.close();
                return;
            }

            if (name.isEmpty() || address.isEmpty()) {
                messageLabel.setText("Name and address cannot be empty.");
                return;
            }

            customerName[0] = name;
            customerAddress[0] = address;

            // Move to credit card
            vbox.getChildren().clear();
            vbox.getChildren().addAll(
                    new Label("Enter 16-digit Credit Card Number"),
                    cardField, submitCardButton, messageLabel
            );
            messageLabel.setText("");
        });

        // --- STEP 4: CREDIT CARD ---
        submitCardButton.setOnAction(e -> {
            String card = cardField.getText().trim();

            if (card.equals("-1")) {
                stage.close();
                return;
            }

            if (!card.matches("\\d{16}")) {
                messageLabel.setText("Invalid card number. Must be 16 digits.");
                return;
            }

            cardNumber[0] = card;

            // Move to security question/answer
            vbox.getChildren().clear();
            vbox.getChildren().addAll(
                    new Label("Select Security Question:"),
                    securityQuestionBox,
                    new Label("Enter Security Answer"),
                    securityAnswerField,
                    submitSecurityButton,
                    messageLabel
            );
            messageLabel.setText("");
        });

        // --- STEP 5: SECURITY QUESTION + ANSWER ---
        submitSecurityButton.setOnAction(e -> {
            String question = securityQuestionBox.getValue();
            String answer = securityAnswerField.getText().trim();

            if (answer.equals("-1")) {
                stage.close();
                return;
            }

            if (question == null || answer.isEmpty()) {
                messageLabel.setText("Select a question and enter an answer.");
                return;
            }

            // CREATE CUSTOMER & ADD TO SYSTEM
            Customer newCustomer = new Customer(
                    customerID[0],
                    customerName[0],
                    customerAddress[0],
                    customerPassword[0],
                    question,
                    answer,
                    cardNumber[0]
            );

            customerOrderSystem.addCustomer(newCustomer);

            messageLabel.setText("Account created successfully!");
            stage.close();

            // OPTIONAL: auto-login or open menu
            openCustomerMenu(newCustomer);
        });

        stage.setScene(new Scene(vbox, 450, 300));
        stage.setTitle("Create Customer Account");
        stage.show();
    }

    private void openChaseBankCustomerBankLoginWindow() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label messageLabel = new Label();

        TextField bankIDField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button submitIDButton = new Button("Submit Bank ID");
        Button submitPasswordButton = new Button("Submit Password");

        vbox.getChildren().addAll(new Label("Enter Bank ID:"), bankIDField, submitIDButton, messageLabel);

        final ChaseBankCustomer[] loggedCustomer = {null};

        // --- STEP 1: BANK ID ---
        submitIDButton.setOnAction(e -> {
            String bankID = bankIDField.getText().trim();
            if (bankID.equals("-1")) {
                stage.close();
                return;
            }

            ChaseBank chaseBank = ChaseBank.getInstance();
            loggedCustomer[0] = null;

            for (ChaseBankCustomer c : chaseBank.getCustomersBank()) {
                if (c.getCustomerBankID().equals(bankID)) {
                    loggedCustomer[0] = c;
                    break;
                }
            }

            if (loggedCustomer[0] == null) {
                messageLabel.setText("Bank ID not found.");
            } else {
                vbox.getChildren().clear();
                vbox.getChildren().addAll(new Label("Enter Password"), passwordField, submitPasswordButton, messageLabel);
            }
        });

        // --- STEP 2: PASSWORD ---
        submitPasswordButton.setOnAction(e -> {
            if (loggedCustomer[0] == null) return;

            String pw = passwordField.getText().trim();
            if (pw.equals("-1")) {
                stage.close();
                return;
            }

            if (pw.equals(loggedCustomer[0].getCustomerPassword())) {
                messageLabel.setText("Login Successful!");
                stage.close();

                new ChaseBankCustomerUserInterface(null, loggedCustomer[0]).ChaseBankCustomerMenu();
            } else {
                messageLabel.setText("Invalid password.");
            }
        });

        stage.setScene(new Scene(vbox, 450, 250));
        stage.setTitle("Chase Bank Login");
        stage.show();
    }

    private void openChaseBankCustomerCreateAccountWindow() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label messageLabel = new Label();

        TextField nameField = new TextField();
        TextField addressField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField depositField = new TextField();

        Button submitNameButton = new Button("Submit Name & Address");
        Button submitPasswordButton = new Button("Submit Password");
        Button submitDepositButton = new Button("Submit Deposit / Finish");

        final String[] customerName = {null};
        final String[] customerAddress = {null};
        final String[] customerPassword = {null};
        final double[] depositAmount = {0.0};

        // --- STEP 1: NAME + ADDRESS ---
        vbox.getChildren().addAll(
                new Label("Enter Customer Name:"), nameField,
                new Label("Enter Address:"), addressField,
                submitNameButton, messageLabel
        );

        submitNameButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String addr = addressField.getText().trim();

            if (name.equals("-1") || addr.equals("-1")) {
                stage.close();
                return;
            }

            if (name.isEmpty() || addr.isEmpty()) {
                messageLabel.setText("Name and Address required.");
                return;
            }

            customerName[0] = name;
            customerAddress[0] = addr;

            vbox.getChildren().clear();
            vbox.getChildren().addAll(
                    new Label("Password must be 6+ chars, include digit, uppercase, special symbol"),
                    passwordField, submitPasswordButton, messageLabel
            );
        });

        // --- STEP 2: PASSWORD ---
        submitPasswordButton.setOnAction(e -> {
            String pass = passwordField.getText().trim();
            if (pass.equals("-1")) {
                stage.close();
                return;
            }

            if (!Customer.isValidPassword(pass)) {
                messageLabel.setText("Invalid password.");
                return;
            }

            customerPassword[0] = pass;

            // Check if card exists or if deposit needed
            Customer existingCustomer = customerOrderSystem.getCustomerByNameAddress(customerName[0], customerAddress[0]);
            if (existingCustomer == null)
                existingCustomer = new Customer(customerName[0], customerAddress[0]);

            String cardNum = existingCustomer.getDefaultCreditCardNumber();
            ChaseBank bank = ChaseBank.getInstance();

            boolean cardExists = bank.getCreditCardLimitByNumber(cardNum) != null;

            if (!cardExists) {
                vbox.getChildren().clear();
                vbox.getChildren().addAll(
                        new Label("Enter Initial Deposit:"), depositField,
                        submitDepositButton, messageLabel
                );
            } else {
                // No deposit needed → finish account
                finishChaseBankCustomerCreation(stage, existingCustomer, customerPassword[0], 0.0);
            }
        });

        // --- STEP 3: DEPOSIT ---
        submitDepositButton.setOnAction(e -> {
            String dep = depositField.getText().trim();
            if (dep.equals("-1")) {
                stage.close();
                return;
            }

            try {
                double amount = Double.parseDouble(dep);
                if (amount <= 0) {
                    messageLabel.setText("Deposit must be positive.");
                    return;
                }
                depositAmount[0] = amount;
            } catch (Exception ex) {
                messageLabel.setText("Numeric deposit required.");
                return;
            }

            Customer existingCustomer = customerOrderSystem.getCustomerByNameAddress(customerName[0], customerAddress[0]);
            if (existingCustomer == null)
                existingCustomer = new Customer(customerName[0], customerAddress[0]);

            finishChaseBankCustomerCreation(stage, existingCustomer, customerPassword[0], depositAmount[0]);
        });

        stage.setScene(new Scene(vbox, 500, 350));
        stage.setTitle("Create Chase Bank Account");
        stage.show();
    }

    private void finishChaseBankCustomerCreation(Stage stage, Customer existingCustomer, String password, double deposit) {
        ChaseBank bank = ChaseBank.getInstance();

        ChaseBankCustomer bankCustomer =
                bank.createChaseBankCustomer(existingCustomer, password);

        String cardNum = existingCustomer.getDefaultCreditCardNumber();

        CreditCardLimit cardLimit =
                bankCustomer.createCreditCardLimit(deposit, cardNum);

        // Show confirmation window
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Created");
        alert.setHeaderText("Your Chase Bank Account Has Been Created");
        alert.setContentText(
                bankCustomer.toUIString() +
                        "\n------------------------------------" +
                        "\nCard Number : " + cardLimit.getCreditCardNumber() +
                        "\nCredit Limit: $" + String.format("%,.2f", cardLimit.getCreditLimit()) +
                        "\n------------------------------------"
        );
        alert.showAndWait();

        stage.close();

        new ChaseBankCustomerUserInterface(null, bankCustomer)
                .ChaseBankCustomerMenu();
    }

    private void openAdminLogonWindow() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label messageLabel = new Label();

        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button submitIDButton = new Button("Submit Admin ID");
        Button submitPasswordButton = new Button("Submit Password");

        vbox.getChildren().addAll(new Label("Admin ID:"), idField, submitIDButton, messageLabel);

        final int[] attempts = {0};

        // --- STEP 1: ID ---
        submitIDButton.setOnAction(e -> {
            String id = idField.getText().trim();
            if (id.equals("-1")) {
                stage.close();
                return;
            }

            if (!id.equals("Admin")) {
                messageLabel.setText("Incorrect Admin ID. Returning to main menu.");
                stage.close();
                return;
            }

            vbox.getChildren().clear();
            vbox.getChildren().addAll(new Label("Enter Password"), passwordField, submitPasswordButton, messageLabel);
        });

        // --- STEP 2: PASSWORD ---
        submitPasswordButton.setOnAction(e -> {
            String pass = passwordField.getText().trim();
            if (pass.equals("-1")) {
                stage.close();
                return;
            }

            if (pass.equals("Password")) {
                stage.close();
                new AdminUserInterface(null, customerOrderSystem, customerOrderSystemUserInterfaceController)
                        .AdminMenu();
            } else {
                attempts[0]++;
                if (attempts[0] >= 3) {
                    messageLabel.setText("Too many incorrect attempts. Try again later.");
                    submitPasswordButton.setDisable(true);
                } else {
                    messageLabel.setText("Incorrect password. Attempts left: " + (3 - attempts[0]));
                }
            }
        });

        stage.setScene(new Scene(vbox, 400, 250));
        stage.setTitle("Admin Login");
        stage.show();
    }

    /**
     * Open customer menu.
     *
     * @param customer the customer
     */
    public void openCustomerMenu(Customer customer) {
        // new window (Stage)
        Stage customerStage = new Stage();

        CustomerGUI customerGUI = new CustomerGUI(customerStage, customer, customerOrderSystem);

        // show the customer menu
        customerGUI.showCustomerMenu();

        // optional: set the title for the window
        customerStage.setTitle("Customer Portal");
    }

}
