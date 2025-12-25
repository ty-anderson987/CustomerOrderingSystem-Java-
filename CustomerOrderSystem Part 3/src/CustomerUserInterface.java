import java.util.Scanner;

/**
 * Represents a Customer User Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 3.0
 */
public class CustomerUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerController customerController;
    private final Customer customer;
    private final ChaseBankCustomer chaseBankCustomer;
    private final Scanner scanner;
    private Cart cart;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer user interface.
     *
     * @param scanner                                    the scanner
     * @param customer                                   the customer
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public CustomerUserInterface(Scanner scanner, Customer customer, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.customerController = customerOrderSystemUserInterfaceController.getCustomerController();
        this.customer = customer;
        this.scanner = scanner;

        ChaseBank chaseBank = ChaseBank.getInstance();
        this.chaseBankCustomer = chaseBank.getChaseBankCustomerByCustomer(customer);
    }


// ------------------- Customer User Interface -------------------
    /**
     * Customer menu.
     */
    public void CustomerMenu() {
        int choice;

        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Products and Create a Cart");
            System.out.println("2. Submit your Order");
            System.out.println("3. View Orders");
            System.out.println("4. Log Out");
            System.out.print("Enter your choice: ");

            // Make sure the user input is a number
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            // Switch case choose option
            switch (choice) {
                case 1:
                    useCaseSelectItems();
                    break;
                case 2:
                    useCaseMakeOrder();
                    break;
                case 3:
                    useCaseViewOrder();
                    break;
                case 4:
                    useCaseLogOut();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    break;
            }
        }
    }



// ------------------- Use Cases -------------------
    /**
     * Use case select items.
     */
    public void useCaseSelectItems() {
        // Create a new cart
        this.cart = customerOrderSystemUserInterfaceController.makeCart();
        if (cart == null) {
            System.out.println("Invalid cart.");

            // Prompt user to continue
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
            return;
        }
        if (cart.isEmpty()) {
            System.out.println("\nNo products selected. Returning to main menu...");

            // Prompt user to continue
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
            return;
        }

        // Ask user to submit order or not?
        try {
            System.out.print("1-Continue to checkout | Other-Return to menus: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                useCaseMakeOrder();
            } else {
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a number between 1 or other.");
        }
    }

    /**
     * Use case make order.
     */
    public void useCaseMakeOrder() {
        if (cart == null) {
            System.out.println("Invalid cart. Please enter a cart.");

            // Prompt user to continue
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
            return;
        }
        if (customer == null) {
            System.out.println("Invalid customer.");

            // Prompt user to continue
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
            return;
        }
        if (chaseBankCustomer == null) {
            System.out.println("You need to have a bank account first before you can place an order.");

            // Prompt user to continue
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
            return;
        }

        Order customerOrder = customerController.createOrderController(cart, customer, chaseBankCustomer);
        if (customerOrder == null) {
            System.out.println("Order could not be created.");
        }
        // Empty the cart
        cart = null;
    }

    /**
     * Use case view order.
     */
    public void useCaseViewOrder() {
        customerController.viewCustomerOrderController(customer);

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case log out.
     */
    public void useCaseLogOut() {
        System.out.println("Logging out... Returning to main menu.");
    }
}