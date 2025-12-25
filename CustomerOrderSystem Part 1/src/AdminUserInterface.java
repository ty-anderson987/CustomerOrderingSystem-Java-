import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents an Admin User Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 2.0
 */
public class AdminUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerOrderSystem customerOrderSystem;
    private final AdminController adminController;
    private final Bank cosBank;
    private final Scanner scanner;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Admin user interface.
     *
     * @param scanner                                    the scanner
     * @param customerOrderSystem                        the customer order system
     * @param cosBank                                    the cos bank
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public AdminUserInterface(Scanner scanner, CustomerOrderSystem customerOrderSystem, Bank cosBank, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystem = customerOrderSystem;
        this.cosBank = cosBank;
        this.scanner = scanner;
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.adminController = new AdminController(customerOrderSystem, customerOrderSystemUserInterfaceController, cosBank, scanner);
    }



// ------------------- Admin User Interface -------------------
    /**
     * Admin menu.
     */
    public void AdminMenu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1.  View all Customers");
            System.out.println("2.  View all CustomerBanks");
            System.out.println("3.  Deposit Amount to CustomerBank");
            System.out.println("4.  Withdraw Amount from CustomerBank");
            System.out.println("5.  View all Orders");
            System.out.println("6.  View all Products");
            System.out.println("7.  Add new Customer");
            System.out.println("8.  Add new Order");
            System.out.println("9. Add new Product");
            System.out.println("10. Delete Customer");
            System.out.println("11. Delete Customer Bank");
            System.out.println("12. Delete Order");
            System.out.println("13. Delete Product");
            System.out.println("14. Update Customer");
            System.out.println("15. Update Customer Bank");
            System.out.println("16. Update Order");
            System.out.println("17. Update Product");
            System.out.println("18. Log Out");
            System.out.print("Enter your choice: ");

            // Make sure the user input is a number
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 18.");
                continue;
            }

            // Switch case choose option
            switch (choice) {
                case 1:
                    viewAllCustomers();
                    break;
                case 2:
                    viewAllCustomerBanks();
                    break;
                case 3:
                    depositAmountToCustomerBank();
                    break;
                case 4:
                    withdrawAmountFromCustomerBank();
                    break;
                case 5:
                    viewAllOrders();
                    break;
                case 6:
                    viewAllProducts();
                    break;
                case 7:
                    addNewCustomer();
                    break;
                case 8:
                    addNewOrder();
                    break;
                case 9:
                    addNewProduct();
                    break;
                case 10:
                    deleteCustomer();
                    break;
                case 11:
                    deleteCustomerBank();
                    break;
                case 12:
                    deleteOrder();
                    break;
                case 13:
                    deleteProduct();
                    break;
                case 14:
                    updateCustomer();
                    break;
                case 15:
                    updateCustomerBank();
                    break;
                case 16:
                    updateOrder();
                    break;
                case 17:
                    updateProduct();
                    break;
                case 18:
                    useCaseLogOut();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 19.");
                    break;
            }
        }
    }



// ------------------- Utilities -------------------
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
     * View all customer banks.
     */
    public void viewAllCustomerBanks() {
        adminController.viewAllCustomerBanksController();

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Choose customer bank.
     *
     * @return the customer bank
     */
    public CustomerBank chooseCustomerBank() {
        ArrayList<CustomerBank> customerBank = adminController.chooseCustomerBankController();

        // Make sure user enters a number valid
        // Select customer
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the customerBank you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return null;
            }

            if (choice < 1 || choice > customerBank.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + customerBank.size() + ".");
                return null;
            }

            // Return chosen customer bank account
            return customerBank.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + customerBank.size() + ".");
            return null;
        }
    }

    /**
     * Initiates the process of depositing money into a customer's bank account.
     * Calls the AdminController to handle the deposit logic.
     */
    public void depositAmountToCustomerBank() {
        adminController.depositAmountToCustomerBankController(this);
    }

    /**
     * Initiates the process of withdrawing money from a customer's bank account.
     * Calls the AdminController to handle the withdrawal logic.
     */
    public void withdrawAmountFromCustomerBank() {
        adminController.withdrawAmountFromCustomerBankController(this);
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

        String method = customerOrderSystemUserInterfaceController.chooseDeliveryMethod(cart);
        if (method == null) {
            return;
        }

        Order order = adminController.addNewOrderController(cart, customer, cosBank.getCustomersBank(customer), method);
        if (order != null) {
            System.out.println("Order submitted successfully!");
        }

        // Prompt user to continue
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

        // Remove customer bank account
        CustomerBank customerBank = customerOrderSystem.getCosBankCustomerBank(customerChosen);
        if (customerBank != null) {
            customerOrderSystem.getCosBank().removeCustomerFromBank(customerBank);
        }

        // Remove customer
        customerOrderSystem.getCustomers().remove(customerChosen);
        System.out.println("Customer removed successfully!");

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Delete customer bank.
     */
    public void deleteCustomerBank () {
        CustomerBank customerBankChosen = chooseCustomerBank();
        if (customerBankChosen == null) {
            System.out.println("No customerBank selected. Returning to menu");
            return;
        }

        // Remove Customer's Order
        adminController.removeCustomerOrdersController(customerBankChosen.getCustomer());
        customerOrderSystem.getCosBank().removeCustomerFromBank(customerBankChosen);
        System.out.println("CustomerBank removed successfully!");

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
     * Update customer bank.
     */
    public void updateCustomerBank() {
        // Delete old customerBank and replace with new customer bank
        chooseCustomerBank();
        deleteCustomerBank();
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
}