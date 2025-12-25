import java.util.Scanner;

/**
 * Represents a Main Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 3.0
 */
public class CustomerOrderSystemUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerOrderSystem customerOrderSystem;
    private final Scanner scanner;

// ------------------- Constructors -------------------
    /**
     * Create a customerOrderSystemUserInterface object
     * Load in the COS to the cosUI object
     * Create a scanner object
     *
     * @param cosUserInterface the cos user interface
     */
    public CustomerOrderSystemUserInterface(CustomerOrderSystem cosUserInterface) {
        this.customerOrderSystem = cosUserInterface;
        this.scanner = new Scanner(System.in);

        // Controllers
        this.customerOrderSystemUserInterfaceController = new CustomerOrderSystemUserInterfaceController(customerOrderSystem, scanner);
    }



// ------------------- Main User Interface -------------------
    /**
     * cosMainUI menu switch case system
     * Displays all the options
     */
    public void cosMainUI() {
        int choice;

        while (true) {
            System.out.println("\n===== Customer Order System Main Menu =====");

            // ----- COS Section -----
            System.out.println("\n--- Customer Order System ---");
            System.out.println("1. Log On");
            System.out.println("2. Create Account");

            // ----- Chase Bank Section -----
            System.out.println("\n--- Chase Bank ---");
            System.out.println("3. Chase Bank Log On");
            System.out.println("4. Create Chase Bank Account");

            // ----- Admin & Exit -----
            System.out.println("\n--- Admin & Exit ---");
            System.out.println("5. Admin Log On");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            // Validate user input
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                // COS
                case 1 -> useCaseLogOn();
                case 2 -> useCaseCreateAccount();

                // Chase Bank
                case 3 -> chaseBankCustomerLogOn();
                case 4 -> chaseBankCustomerCreateAccount();

                // Admin & Exit
                case 5 -> useCaseAdminLogOn();
                case 6 -> {
                    System.out.println("Exiting Program...");
                    return;
                }

                default -> System.out.println("Invalid choice. Enter a number between 1 and 6.");
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
            System.out.println("Welcome " + loggedInCustomer.getCustomerName() + "! You have successfully logged in.");
            // Create a new Customer user interface
            CustomerUserInterface newCustomerUserInterface = new CustomerUserInterface(scanner, loggedInCustomer, customerOrderSystemUserInterfaceController);
            newCustomerUserInterface.CustomerMenu();
        } else {
            System.out.println("Customer Log On Failed or Canceled");
        }
    }

    /**
     * Create a new account using the parameter
     */
    public void useCaseCreateAccount() {
        Customer newCustomer = customerOrderSystemUserInterfaceController.useCaseCreateAccountController();
        System.out.println("---------- You successfully created an account ----------");
        System.out.println(newCustomer.toUIString());
        if (newCustomer != null) {
            System.out.print("\n1 - Login | Other - Return to main menu: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                CustomerUserInterface newCustomerUserInterface = new CustomerUserInterface(scanner, newCustomer, customerOrderSystemUserInterfaceController);
                newCustomerUserInterface.CustomerMenu();
            }
        } else {
            System.out.println("Customer Creation Failed or Canceled");
        }
    }

    /**
     * ChaseBankCustomer login using user input
     */
    public void chaseBankCustomerLogOn() {
        ChaseBankCustomer loggedInChaseBankCustomer = customerOrderSystemUserInterfaceController.chaseBankCustomerLogOn();
        if (loggedInChaseBankCustomer != null) {
            System.out.println("Welcome " + loggedInChaseBankCustomer.getCustomerName() + ". You have successfully logged in.");
            // Create a new ChaseBankCustomer User Interface
            ChaseBankCustomerUserInterface newChaseBankCustomerUserInterface = new ChaseBankCustomerUserInterface(scanner, loggedInChaseBankCustomer);
            newChaseBankCustomerUserInterface.ChaseBankCustomerMenu();
        } else {
            System.out.println("Customer Log On Failed or Canceled");
        }
    }

    /**
     * Create a new ChaseBankCustomer using the paramter
     */
    public void chaseBankCustomerCreateAccount() {
        ChaseBankCustomer newChaseBankCustomer = customerOrderSystemUserInterfaceController.chaseBankCustomerCreateAccountController();
        if (newChaseBankCustomer != null) {
            System.out.print("1 - Login | Other - Return to main menu: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                ChaseBankCustomerUserInterface newChaseBankCustomerUserInterface = new ChaseBankCustomerUserInterface(scanner, newChaseBankCustomer);
                newChaseBankCustomerUserInterface.ChaseBankCustomerMenu();
            }
        } else {
            System.out.println("Customer Creation Failed or Canceled");
        }
    }

    /**
     * Admin long using user input
     */
    public void useCaseAdminLogOn() {
        String adminID;
        String adminPassword;

        int attempts = 0;
        System.out.println("Enter -1 to exit");
        while (attempts < 3) {
            // Find customerID
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

        System.out.println("Welcome Admin! You have successfully logged in.");
        // Create a new Admin user interface
        AdminUserInterface newAdminUserInterface = new AdminUserInterface(scanner, customerOrderSystem, customerOrderSystemUserInterfaceController);
        newAdminUserInterface.AdminMenu();
    }
}