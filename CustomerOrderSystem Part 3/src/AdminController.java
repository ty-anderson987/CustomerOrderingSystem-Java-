import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents an Admin Controller in the Customer Order System (COS).
 * Controls the logic for Admin UI
 *
 * @author Ty Anderson R11885063
 * @version 3.0
 */
public class AdminController {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerOrderSystem customerOrderSystem;
    private final Scanner scanner;

// ------------------- Constructors -------------------
    /**
     * Constructs an AdminController with required system references.
     *
     * @param customerOrderSystem                        The main COS system managing customers, orders, and products.
     * @param customerOrderSystemUserInterfaceController Controller for UI-related operations.
     * @param scanner                                    Scanner object for user input.
     */
    public AdminController (CustomerOrderSystem customerOrderSystem, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController, Scanner scanner) {
        this.customerOrderSystem = customerOrderSystem;
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.scanner = scanner;
    }



// ------------------- Utilities -------------------
    /**
     * Prints all customers to the console using their UI string representation.
     */
    public void viewAllCustomersController() {
        ArrayList<Customer> customerArrayList = customerOrderSystem.getCustomers();
        for (Customer customer : customerArrayList) {
            System.out.println(customer.toUIString());
        }
    }

    /**
     * Prints a numbered list of customers and returns the list for selection.
     *
     * @return ArrayList of customers.
     */
    public ArrayList<Customer> chooseCustomerController() {
        ArrayList<Customer> customers = customerOrderSystem.getCustomers();

        // ------------------- Print all customers with index choice-------------------
        int size = customers.size();
        for (int i = 1; i < size + 1; i++) {
            System.out.println(i + ":" + customers.get(i - 1).toUIString());
        }
        return customers;
    }

    /**
     * Uses the UI controller to create a new customer account.
     * Prints a success message or error if creation fails.
     *
     * @return the customer
     */
    public Customer addNewCustomerController() {
        System.out.println("=== Existing Customer IDs ===");
        ArrayList<Customer> customerList = customerOrderSystem.getCustomers();
        for (Customer customer : customerList) {
            System.out.print(customer.getCustomerID() + " ");
        }

        // Create a new customer and return the customer
        System.out.println();
        return customerOrderSystemUserInterfaceController.useCaseCreateAccountController();
    }



// ------------------- ChaseBankCustomer Management -------------------
    /**
     * Prints all ChaseBank customers using their UI string.
     */
    public void viewAllChaseBankCustomersController() {
        ArrayList<ChaseBankCustomer> chaseCustomers = ChaseBank.getInstance().getCustomersBank();
        for (ChaseBankCustomer customer : chaseCustomers) {
            System.out.println(customer.toUIString());
        }
    }

    /**
     * Returns a numbered list of all ChaseBank customers for selection.
     */
    public ArrayList<ChaseBankCustomer> chooseChaseBankCustomersController() {
        ArrayList<ChaseBankCustomer> chaseCustomers = ChaseBank.getInstance().getCustomersBank();

        int size = chaseCustomers.size();
        for (int i = 1; i <= size; i++) {
            System.out.println(i + ": " + chaseCustomers.get(i - 1).toUIString());
        }

        return chaseCustomers;
    }

    /**
     * View all chase bank customer controller.
     */
    public void viewAllChaseBankCustomerController() {
        ArrayList<ChaseBankCustomer> chaseBankCustomerArrayList = ChaseBank.getInstance().getCustomersBank();
        for (ChaseBankCustomer chaseBankCustomer : chaseBankCustomerArrayList) {
            System.out.println(chaseBankCustomer.toAccountDetailsString());
        }
    }

    /**
     * Choose credit card limit controller array list.
     *
     * @param chaseBankCustomer the chase bank customer
     * @return the array list
     */
    public ArrayList<CreditCardLimit> chooseCreditCardLimitController(ChaseBankCustomer chaseBankCustomer) {
        ArrayList<CreditCardLimit> creditCardLimits = chaseBankCustomer.getCustomerCreditCardLimit();

        int size = creditCardLimits.size();
        for (int i = 1; i <= size; i++) {
            System.out.println(i + ": " + creditCardLimits.get(i - 1).toUIString());
        }

        return creditCardLimits;
    }

    /**
     * Deposit to credit card.
     *
     * @param chaseCustomer the chase customer
     * @param creditCard    the credit card
     * @param amount        the amount
     */
    public void depositToCreditCard(ChaseBankCustomer chaseCustomer, CreditCardLimit creditCard, double amount) {
        if (chaseCustomer == null || creditCard == null || amount <= 0) return;
        creditCard.addMoney(amount);
    }

    /**
     * Withdraw from credit card.
     *
     * @param chaseCustomer the chase customer
     * @param creditCard    the credit card
     * @param amount        the amount
     */
    public void withdrawFromCreditCard(ChaseBankCustomer chaseCustomer, CreditCardLimit creditCard, double amount) {
        if (chaseCustomer == null || creditCard == null || amount <= 0) return;
        creditCard.removeMoney(amount);
    }



// ------------------- Order Management -------------------
    /**
     * Prints all orders in the system, including the associated customer information.
     */
    public void viewAllOrdersController() {
        // Find all the customers.
        // Find all the orders.
        // Find the orders with the customer ID and prints it
        for (Customer customer : customerOrderSystem.getCustomers()) {
            for (Order order : customerOrderSystem.getOrders()) {
                if (order.getCustomerID().equals(customer.getCustomerID())) {
                    System.out.println(order.toUIString(customer));
                }
            }
        }
    }

    /**
     * Prints all orders with numbered indexes for selection.
     *
     * @return ArrayList of orders, or null if no orders exist.
     */
    public ArrayList<Order> chooseOrderController() {
        ArrayList<Order> orders = customerOrderSystem.getOrders();
        ArrayList<Customer> customers = customerOrderSystem.getCustomers();

        // ------------------- Print all orders -------------------
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return null;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Customer customerChosen = null;

            // find matching customer for the order
            for (Customer customer : customers) {
                if (order.getCustomerID().equals(customer.getCustomerID())) {
                    customerChosen = customer;
                    break;
                }
            }

            System.out.println((i + 1) + ": " + order.toUIString(customerChosen));
        }
        return orders;
    }

    /**
     * Creates a new order using the UI controller.
     *
     * @param cart           Cart object containing products.
     * @param customer       Customer placing the order.
     * @param chaseBankCustomer   ChaseCustomerBank account used for payment.
     * @param deliveryMethod Delivery method for the order.
     * @return The created Order object, or null if creation fails.
     */
    public Order addNewOrderController(Cart cart, Customer customer, ChaseBankCustomer chaseBankCustomer, String deliveryMethod) {
        return customerOrderSystemUserInterfaceController
                .getOrderController()
                .createSubmitOrderController(cart, customer, chaseBankCustomer, deliveryMethod);
    }





// ------------------- Product Management -------------------
    /**
     * Prints all products in the system using their UI string.
     */
    public void viewAllProductsController() {
        ArrayList<Product> productArrayList = customerOrderSystem.getProducts();
        for (Product product : productArrayList) {
            System.out.println(product.toUIString());
        }
    }

    /**
     * Prints all products with numbered indexes for selection.
     *
     * @return ArrayList of products.
     */
    public ArrayList<Product> chooseProductController() {
        ArrayList<Product> products = customerOrderSystem.getProducts();

        // ------------------- Make Cart-------------------
        int size = products.size();
        for (int i = 1; i < size + 1; i++) {
            System.out.println(i + ":" + products.get(i - 1).toUIString());
        }
        return products;
    }

    /**
     * Adds a new product (quantity-based or weighted) to the system.
     * Prompts admin for product details via Scanner.
     */
    public void addNewProductController() {
        try {
            String category;
            String productName;
            String productDescription;
            double productRegularPrice;
            double productSalePrice;
            int quantityPerPackage;
            double weightPerPackage;

            // Determines if the new product is a quantity/weighted category
            System.out.print("1-Quantity Product | 2-Weighted Product: ");
            category = scanner.nextLine();
            if (!(category.equals("1") || category.equals("2"))) {
                System.out.println("Invalid input. Enter a number 1 or 2");
                return;
            }

            // Get the information
            System.out.print("Enter product name: ");
            productName = scanner.nextLine();
            System.out.print("Enter product Description: ");
            productDescription = scanner.nextLine();
            System.out.print("Enter product regular price: ");
            productRegularPrice = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter product sale price: ");
            productSalePrice = Double.parseDouble(scanner.nextLine());

            // Enter quantity or weight based on previous response
            // Creates a new quantity/weighted product
            // Add new product to the list
            if (category.equals("1")) {
                // Quantity Product Create
                System.out.print("Enter product quantity per package: ");
                quantityPerPackage = Integer.parseInt(scanner.nextLine());
                QuantityProducts quantityProduct = new QuantityProducts(productName, productDescription, productRegularPrice, productSalePrice, quantityPerPackage);
                customerOrderSystem.addProduct(quantityProduct);
                System.out.println("Quantity product added successfully!");
            } else {
                // Weighted Product Create
                System.out.print("Enter product weight per package: ");
                weightPerPackage = Double.parseDouble(scanner.nextLine());
                WeightedProducts weightedProducts = new WeightedProducts(productName, productDescription, productRegularPrice, productSalePrice, weightPerPackage);
                customerOrderSystem.addProduct(weightedProducts);
                System.out.println("Weighted product added successfully!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }



// ------------------- Utilities -------------------
    // Remove
    /**
     * Removes all orders associated with the given customer.
     *
     * @param customer Customer whose orders will be removed.
     */
    public void removeCustomerOrdersController(Customer customer) {
        ArrayList<Order> ordersToRemove = new ArrayList<>();
        for (Order order : customerOrderSystem.getOrders()) {
            if (order.getCustomerID().equals(customer.getCustomerID())) {
                ordersToRemove.add(order);
            }
        }
        customerOrderSystem.getOrders().removeAll(ordersToRemove);
    }

    /**
     * Remove product orders controller int.
     *
     * @param product the product
     * @return the int
     */
    public int removeProductOrdersController(Product product) {
        int removedCount = 0;

        for (Order order : customerOrderSystem.getOrders()) {
            Cart orderCart = order.getOrderCart();

            // Collect cart items to remove
            ArrayList<CartItem> itemsToRemove = new ArrayList<>();

            for (CartItem cartItem : orderCart.getItems()) {
                if (cartItem.getProduct().equalsProduct(product)) {
                    itemsToRemove.add(cartItem);
                }
            }

            // Remove matching items from the cart
            if (!itemsToRemove.isEmpty()) {
                orderCart.getItems().removeAll(itemsToRemove);
                removedCount += itemsToRemove.size();
            }
        }
        return removedCount;
    }
}