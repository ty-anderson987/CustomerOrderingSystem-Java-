import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents an Admin User Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 3.0
 */
public class AdminUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerOrderSystem customerOrderSystem;
    private final AdminController adminController;
    private final Scanner scanner;
    private final ChaseBank chaseBank = ChaseBank.getInstance();

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Admin user interface.
     *
     * @param scanner                                    the scanner
     * @param customerOrderSystem                        the customer order system
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public AdminUserInterface(Scanner scanner, CustomerOrderSystem customerOrderSystem, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystem = customerOrderSystem;
        this.scanner = scanner;

        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.adminController = new AdminController(customerOrderSystem, customerOrderSystemUserInterfaceController, scanner);
    }



// ------------------- Admin User Interface -------------------
    /**
     * Admin menu.
     */
    public void AdminMenu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");

            // ----- COS Section -----
            System.out.println("\n--- Customer Order System (COS) ---");
            System.out.println("1. View all Customers");
            System.out.println("2. View all Orders");
            System.out.println("3. View all Products");
            System.out.println("4. Add new Customer");
            System.out.println("5. Add new Order");
            System.out.println("6. Add new Product");
            System.out.println("7. Delete Customer");
            System.out.println("8. Delete Order");
            System.out.println("9. Delete Product");
            System.out.println("10. Update Customer");
            System.out.println("11. Update Order");
            System.out.println("12. Update Product");

            // ----- Chase Bank Section -----
            System.out.println("\n--- Chase Bank ---");
            System.out.println("13. View all ChaseBankCustomer Accounts");
            System.out.println("14. Deposit Amount to ChaseBankCustomer Account");
            System.out.println("15. Withdraw Amount from ChaseBankCustomer Account");
            System.out.println("16. Add new ChaseBankCustomer");
            System.out.println("17. Delete ChaseBankCustomer Account");
            System.out.println("18. Update ChaseBankCustomer Account");

            // ----- Exit -----
            System.out.println("\n19. Log Out");

            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 19.");
                continue;
            }

            switch (choice) {
                // COS
                case 1 -> viewAllCustomers();
                case 2 -> viewAllOrders();
                case 3 -> viewAllProducts();
                case 4 -> addNewCustomer();
                case 5 -> addNewOrder();
                case 6 -> addNewProduct();
                case 7 -> deleteCustomer();
                case 8 -> deleteOrder();
                case 9 -> deleteProduct();
                case 10 -> updateCustomer();
                case 11 -> updateOrder();
                case 12 -> updateProduct();

                // Chase Bank
                case 13 -> viewAllChaseBankCustomerAccount();
                case 14 -> depositAmountToCustomerBank();
                case 15 -> withdrawAmountFromCustomerBank();
                case 16 -> addChaseBankCustomer();
                case 17 -> deleteChaseBankCustomer();
                case 18 -> updateChaseBankCustomer();

                case 19 -> {
                    useCaseLogOut();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 19.");
            }
        }
    }



// ------------------- COS Utilities -------------------
    /**
     * View all customers.
     */
    public void viewAllCustomers() {
        adminController.viewAllCustomersController();

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose customer.
     *
     * @return the customer
     */
    public Customer chooseCustomer() {
        ArrayList<Customer> customers = adminController.chooseCustomerController();

        // Make sure user enters a number valid
        // Select customer
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the customer you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > customers.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + customers.size() + ".");
                return null;
            }

            // Return chosen customer
            return customers.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + customers.size() + ".");
            return null;
        }
    }

    /**
     * View all orders.
     */
    public void viewAllOrders() {
        adminController.viewAllOrdersController();

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose order.
     *
     * @return the order
     */
    public Order chooseOrder() {
        ArrayList<Order> orders = adminController.chooseOrderController();

        // Make sure user enters a number valid
        // Select customer
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the order you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > orders.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + orders.size() + ".");
                return null;
            }

            return orders.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + orders.size() + ".");
            return null;
        }
    }

    /**
     * View all products.
     */
    public void viewAllProducts() {
        adminController.viewAllProductsController();

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose  product.
     *
     * @return the product
     */
    public Product chooseProduct() {
        ArrayList<Product> products = adminController.chooseProductController();

        // Make sure user enters a number valid
        // Change the selected product
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the product you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > products.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + products.size() + ".");
                return null;
            }

            return products.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + products.size() + ".");
            return null;
        }
    }



    // Add
    /**
     * Add new customer.
     */
    public void addNewCustomer() {
        Customer customer = adminController.addNewCustomerController();
        if (customer == null) {
            System.out.println("Unexpected error occurred while adding the customer.");
        }

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Add new order.
     */
    public void addNewOrder() {
        Cart cart = customerOrderSystemUserInterfaceController.makeCart();
        if (cart == null) {
            return;
        }

        Customer customer = chooseCustomer();
        if (customer == null) {
            return;
        }

        // 1️⃣ Choose a ChaseBankCustomer to charge
        ChaseBank chaseBank = ChaseBank.getInstance();
        ArrayList<ChaseBankCustomer> bankCustomers = chaseBank.getCustomersBank();

        if (bankCustomers.isEmpty()) {
            System.out.println("No ChaseBank customers found. Cannot create order.");
            return;
        }

        System.out.println("\nSelect ChaseBankCustomer to use for this order:");
        for (int i = 0; i < bankCustomers.size(); i++) {
            ChaseBankCustomer c = bankCustomers.get(i);
            System.out.println((i + 1) + ". " + c.getCustomerName() + " (" + c.getCustomerBankID() + ")");
        }

        ChaseBankCustomer selectedBankCustomer = null;
        try {
            System.out.print("Enter number (0 to cancel): ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                System.out.println("Order cancelled.");
                return;
            }
            if (choice >= 1 && choice <= bankCustomers.size()) {
                selectedBankCustomer = bankCustomers.get(choice - 1);
            } else {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        // 2️⃣ Choose delivery method
        String method = customerOrderSystemUserInterfaceController.chooseDeliveryMethod(cart);
        if (method == null) {
            return;
        }

        // 3️⃣ Pass everything to the AdminController
        Order order = adminController.addNewOrderController(cart, customer, selectedBankCustomer, method);
        if (order != null) {
            System.out.println("✅ Order submitted successfully!");
        }

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Add new product.
     */
    public void addNewProduct() {
        adminController.addNewProductController();

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }



    // Delete
    /**
     * Delete customer.
     */
    public void deleteCustomer() {
        Customer customerChosen = chooseCustomer();
        if (customerChosen == null) {
            System.out.println("No customer selected.");
            return;
        }

        // Remove customer orders
        adminController.removeCustomerOrdersController(customerChosen);

        // Remove customer
        customerOrderSystem.getCustomers().remove(customerChosen);
        System.out.println("Customer removed successfully!");

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Delete order.
     */
    public void deleteOrder () {
        Order orderChosen = chooseOrder();
        if (orderChosen == null) {
            System.out.println("No order selected. Returning to menu.");
            return;
        }
        customerOrderSystem.getOrders().remove(orderChosen);
        System.out.println("Order removed successfully!");

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Delete product.
     */
    public void deleteProduct() {
        Product productChosen = chooseProduct();
        if (productChosen == null) {
            System.out.println("No product selected. Returning to menu.");
            return;
        }

        // Remove order involving the product
        int removedCount = adminController.removeProductOrdersController(productChosen);
        if (removedCount > 0) {
            System.out.println("Removed " + productChosen.getProductName() + " from all (" + removedCount + ") existing orders.");
        } else {
            System.out.println("No orders contained this product.");
        }
        customerOrderSystem.getProducts().remove(productChosen);
        System.out.println("Product removed successfully!");

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }



    // Update (Remove and Add)
    /**
     * Update customer.
     */
    public void updateCustomer() {
        // Delete old customer and replace with new customer
        deleteCustomer();
        addNewCustomer();
    }

    /**
     * Update order.
     */
    public void updateOrder() {
        // Delete old order and replaces it with a new order
        deleteOrder();
        addNewOrder();
    }

    /**
     * Update product.
     */
    public void updateProduct() {
        // Delete old product and replaces it with a new product
        deleteProduct();
        addNewProduct();
    }

    /**
     * Use case log out.
     */
    public void useCaseLogOut() {
        System.out.println("Logging out... Returning to main menu.");
    }



// ------------------- Chase Bank Utilities -------------------
    // View
    /**
     * View all ChaseBankCustomer accounts.
     */
    public void viewAllChaseBankCustomerAccount() {
        adminController.viewAllChaseBankCustomersController();

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose a ChaseBankCustomer.
     *
     * @return the selected ChaseBankCustomer or null if canceled
     */
    public ChaseBankCustomer chooseChaseBankCustomer() {
        ArrayList<ChaseBankCustomer> customers = adminController.chooseChaseBankCustomersController();

        try {
            System.out.print("\nEnter 0 to cancel \nEnter the number corresponding to the ChaseBankCustomer you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > customers.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + customers.size() + ".");
                return null;
            }

            return customers.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + customers.size() + ".");
            return null;
        }
    }

    /**
     * View all credit card limits.
     *
     * @param chaseBankCustomer the chase bank customer
     */
    public void viewAllCreditCardLimits(ChaseBankCustomer chaseBankCustomer) {
        if (chaseBankCustomer == null) {
            System.out.println("No ChaseBankCustomer selected.");
            return;
        }

        adminController.chooseCreditCardLimitController(chaseBankCustomer);

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose credit card limit credit card limit.
     *
     * @return the credit card limit
     */
    public CreditCardLimit chooseCreditCardLimit() {
        ChaseBankCustomer chaseCustomer = chooseChaseBankCustomer();
        if (chaseCustomer == null) {
            System.out.println("No ChaseBankCustomer selected.");
            return null;
        }

        ArrayList<CreditCardLimit> creditCardLimits = adminController.chooseCreditCardLimitController(chaseCustomer);
        if (creditCardLimits == null || creditCardLimits.isEmpty()) {
            System.out.println("This customer has no credit cards.");
            return null;
        }

        try {
            System.out.print("\nEnter 0 to cancel\nEnter the number of the credit card: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > creditCardLimits.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + creditCardLimits.size() + ".");
                return null;
            }

            return creditCardLimits.get(choice - 1);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + creditCardLimits.size() + ".");
            return null;
        }
    }

    /**
     * Deposit money into a ChaseBankCustomer's credit card.
     */
    public void depositAmountToCustomerBank() {
        // Step 1: Choose credit card limit (and associated customer)
        CreditCardLimit selectedCard = chooseCreditCardLimit();
        if (selectedCard == null) {
            System.out.println("Deposit canceled.");
            return;
        }

        ChaseBankCustomer selectedCustomer = selectedCard.getChaseBankCustomer();
        if (selectedCustomer == null) {
            System.out.println("Error: No associated Chase Bank customer found.");
            return;
        }

        // Step 2: Get deposit amount
        double amount;
        while (true) {
            try {
                System.out.println(selectedCustomer.getCustomerName() + "'s current credit limit: $" + selectedCard.getCreditLimit());
                System.out.println("Enter 0 to cancel");
                System.out.print("Enter amount to deposit: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Deposit canceled.");
                    return;
                }
                if (amount > 10_000_000) {
                    System.out.println("Transaction too large. Enter a smaller amount.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid deposit. Enter a valid number greater than 0.");
            }
        }

        // Step 3: Perform deposit
        adminController.depositToCreditCard(selectedCustomer, selectedCard, amount);

        // Step 4: Print result
        System.out.printf("Successfully deposited $%,.2f.%n", amount);
        System.out.printf("New total credit limit: $%,.2f%n", selectedCard.getCreditLimit());

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Withdraw money from a ChaseBankCustomer's credit card.
     */
    public void withdrawAmountFromCustomerBank() {
        // Step 1: Choose credit card limit
        CreditCardLimit selectedCard = chooseCreditCardLimit();
        if (selectedCard == null) {
            System.out.println("Withdrawal canceled.");
            return;
        }

        ChaseBankCustomer selectedCustomer = selectedCard.getChaseBankCustomer();
        if (selectedCustomer == null) {
            System.out.println("Error: No associated Chase Bank customer found.");
            return;
        }

        // Step 2: Get withdrawal amount
        double amount;
        while (true) {
            try {
                System.out.println(selectedCustomer.getCustomerName() + "'s current credit limit: $" + selectedCard.getCreditLimit());
                System.out.println("Enter 0 to cancel");
                System.out.print("Enter amount to withdraw: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Withdrawal canceled.");
                    return;
                }
                if (amount > selectedCard.getCreditLimit()) {
                    System.out.println("Insufficient funds. Try a smaller amount.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid withdrawal amount. Enter a valid number.");
            }
        }

        // Step 3: Perform withdrawal
        adminController.withdrawFromCreditCard(selectedCustomer, selectedCard, amount);

        // Step 4: Print result
        System.out.printf("Successfully withdrew $%,.2f.%n", amount);
        System.out.printf("New total credit limit: $%,.2f%n", selectedCard.getCreditLimit());

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }



    // Add
    /**
     * Allows Admin to add a new ChaseBankCustomer.
     */
    public void addChaseBankCustomer() {
        customerOrderSystemUserInterfaceController.chaseBankCustomerCreateAccountController();

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }



    // Delete
    /**
     * Delete a ChaseBankCustomer from the bank.
     */
    public void deleteChaseBankCustomer() {
        ArrayList<ChaseBankCustomer> chaseCustomers = adminController.chooseChaseBankCustomersController();
        ChaseBank chaseBank = ChaseBank.getInstance();

        if (chaseCustomers.isEmpty()) {
            System.out.println("There are no ChaseBank customers to delete.");
            return;
        }

        ChaseBankCustomer chosenCustomer = null;
        try {
            System.out.print("\nEnter 0 to cancel \nEnter the number corresponding to the customer you want to delete: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Deletion canceled.");
                return;
            }

            if (choice < 1 || choice > chaseCustomers.size()) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + chaseCustomers.size() + ".");
                return;
            }

            chosenCustomer = chaseCustomers.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        // Remove all orders associated with the customer linked to this ChaseBankCustomer
        Customer linkedCustomer = customerOrderSystemUserInterfaceController.getCustomerOrderSystem()
                .getCustomerByNameAddress(chosenCustomer.getCustomerName(), chosenCustomer.getCustomerAddress());
        if (linkedCustomer != null) {
            adminController.removeCustomerOrdersController(linkedCustomer);
            System.out.println("All orders associated with this customer have been removed.");
        }

        // Perform deletion using ChaseBank instance
        chaseBank.removeCustomerFromBank(chosenCustomer);
        System.out.println("ChaseBank customer removed successfully!");

        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }



    // Update (Remove and Add)
    /**
     * Updates a ChaseBankCustomer by deleting and re-adding them.
     */
    public void updateChaseBankCustomer() {
        ArrayList<ChaseBankCustomer> chaseCustomers = adminController.chooseChaseBankCustomersController();

        if (chaseCustomers.isEmpty()) {
            System.out.println("There are no ChaseBank customers to update.");
            return;
        }

        ChaseBankCustomer chosenCustomer = null;
        try {
            System.out.print("\nEnter 0 to cancel \nEnter the number corresponding to the customer you want to update: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Update canceled.");
                return;
            }

            if (choice < 1 || choice > chaseCustomers.size()) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + chaseCustomers.size() + ".");
                return;
            }

            chosenCustomer = chaseCustomers.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        System.out.println("Updating ChaseBankCustomer: " + chosenCustomer.getCustomerName());
        System.out.println("This will delete the old customer and create a new one.");

        System.out.print("Are you sure you want to continue? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        if (!confirm.equals("Y")) {
            System.out.println("Update canceled.");
            return;
        }

        // Delete old customer
        ChaseBank.getInstance().removeCustomerFromBank(chosenCustomer);
        System.out.println("Old ChaseBankCustomer removed.");

        // Call the add method to create the new one
        addChaseBankCustomer();
    }
}