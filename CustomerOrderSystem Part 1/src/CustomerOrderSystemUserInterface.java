import java.util.Scanner;

/**
 * Represents a Main Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CustomerOrderSystemUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerOrderSystem customerOrderSystem;
    private final Bank cosBank;
    private final Scanner scanner;

// ------------------- Constructors -------------------
    /**
     * Create a customerOrderSystemUserInterface object
     * Load in the cos, and bank to the cosUI object
     * Create a scanner object
     *
     * @param cosUserInterface the cos user interface
     * @param cosBank          the cos bank
     */
    public CustomerOrderSystemUserInterface(CustomerOrderSystem cosUserInterface, Bank cosBank) {
        this.customerOrderSystem = cosUserInterface;
        this.cosBank = cosBank;
        this.scanner = new Scanner(System.in);

        // Controllers
        this.customerOrderSystemUserInterfaceController = new CustomerOrderSystemUserInterfaceController(customerOrderSystem, cosBank, scanner);
    }



// ------------------- Main User Interface -------------------
    /**
     * cosMainUI menu switch case system
     * Displays all the options
     */
    public void cosMainUI() {
        int choice;

        while (true) {
            System.out.println("\n===== Customer Order System =====");
            System.out.println("1. Log On");
            System.out.println("2. Admin Log On");
            System.out.println("3. Create Account");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Make sure the user input is a number
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number between 1 and 4.");
                continue;
            }

            // Switch case choose option
            switch (choice) {
                case 1:
                    useCaseLogOn();
                    break;
                case 2:
                    useCaseAdminLogOn();
                    break;
                case 3:
                    useCaseCreateAccount();
                    break;
                case 4:
                    System.out.println("Exiting Program...");
                    return;
                default:
                    System.out.println("Invalid choice. Enter a number between 1 and 3.");
                    break;
            }
        }
    }



// ------------------- Use Cases -------------------
    /**
     * Customer login using user input
     */
    public void useCaseLogOn() {
        Customer loggedInCustomer = customerOrderSystemUserInterfaceController.useCaseLogOn();
        if (loggedInCustomer != null) {
            System.out.println("You have successfully logged in");
            // Create a new Customer user interface
            CustomerUserInterface newCustomerUserInterface = new CustomerUserInterface(scanner, loggedInCustomer, cosBank.getCustomersBank(loggedInCustomer), customerOrderSystemUserInterfaceController);
            newCustomerUserInterface.CustomerMenu();
        } else {
            System.out.println("Customer Log On Failed or Canceled");
        }
    }

    /**
     * Admin long using user input
     */
    public void useCaseAdminLogOn() {
        String adminID;
        String adminPassword;

        int attempts = 0;
        while (attempts < 3) {
            // Find customerID
            System.out.println("Enter -1 to exit");
            System.out.print("Enter admin ID: ");
            adminID = scanner.nextLine();
            if (adminID.equals("-1")) {
                return;
            }
            if (!adminID.equals("Admin")) {
                System.out.println("Incorrect admin ID. Try again later.");
                return;
            }

            // Check password match
            System.out.println("Enter -1 to exit");
            System.out.print("Enter Password: ");
            adminPassword = scanner.nextLine();
            if (adminPassword.equals("-1")) {
                return;
            }
            if (adminPassword.equals("Password")) {
                break;
            } else {
                System.out.println("Password is incorrect");
                attempts++;
            }
        }
        if (attempts >= 3) {
            System.out.println("Too many failed login attempts. Try again later.");
            return;
        }

        System.out.println("You have successfully logged in");
        // Create a new Admin user interface
        AdminUserInterface newAdminUserInterface = new AdminUserInterface(scanner, customerOrderSystem, cosBank, customerOrderSystemUserInterfaceController);
        newAdminUserInterface.AdminMenu();
    }

    /**
     * Create a new account using the parameter
     */
    public void useCaseCreateAccount() {
        Customer newCustomer = customerOrderSystemUserInterfaceController.useCaseCreateAccountController();
        if (newCustomer != null) {
            System.out.print("1 - Login | Other - Return to main menu: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                CustomerUserInterface newCustomerUserInterface = new CustomerUserInterface(scanner, newCustomer, cosBank.getCustomersBank(newCustomer), customerOrderSystemUserInterfaceController);
                newCustomerUserInterface.CustomerMenu();
            }
        } else {
            System.out.println("Customer Creation Failed or Canceled");
        }
    }
}