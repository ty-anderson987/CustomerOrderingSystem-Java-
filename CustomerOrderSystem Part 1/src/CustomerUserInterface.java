import java.util.Scanner;

/**
 * Represents a Customer User Interface in the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CustomerUserInterface {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;
    private final CustomerController customerController;
    private final Customer customer;
    private CustomerBank customerBank;
    private final Scanner scanner;
    private Cart cart;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer user interface.
     *
     * @param scanner                                    the scanner
     * @param customer                                   the customer
     * @param customerBank                               the customer bank
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public CustomerUserInterface(Scanner scanner, Customer customer, CustomerBank customerBank, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.customer = customer;
        this.customerBank = customerBank;
        this.customerController = customerOrderSystemUserInterfaceController.getCustomerController();
        this.scanner = scanner;
    }

    /**
     * Instantiates a new Customer user interface.
     *
     * @param scanner                                    the scanner
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public CustomerUserInterface(Scanner scanner, CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
        this.customerController = customerOrderSystemUserInterfaceController.getCustomerController();
        this.customer = null;
        this.customerBank = null;
        this.scanner = scanner;
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
            System.out.println("4. View Credit Limit");
            System.out.println("5. Deposit Money");
            System.out.println("6. Withdraw Money");
            System.out.println("7. Log Out");
            System.out.print("Enter your choice: ");

            // Make sure the user input is a number
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
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
                    useCaseViewCreditLimit();
                    break;
                case 5:
                    useCaseDepositMoney();
                    break;
                case 6:
                    useCaseRemoveMoney();
                    break;
                case 7:
                    useCaseLogOut();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
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

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case make order.
     */
    public void useCaseMakeOrder() {
        if (cart == null) {
            System.out.println("Invalid cart. Please enter a cart.");
            return;
        }

        if (customerBank == null) {
            System.out.println("Create a customer bank account to continue to checkout.");
            return;
        }

        customerController.createOrderController(cart, customer, customerBank);
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
     * Use case view credit limit.
     */
    public void useCaseViewCreditLimit() {
        if (customerBank == null) {
            System.out.println("Customer does not have a customer bank account registered.");
            return;
        }
        System.out.println(customer.getCustomerName() + "'s Credit Limit: " + customerBank.getCustomerCreditLimit());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case deposit money.
     */
    public void useCaseDepositMoney() {
        if (customerBank == null) {
            System.out.println("Customer does not have a customer bank account registered.");
            return;
        }
        double amount;
        while (true) {
            try {
                System.out.println(customerBank.getCustomer().getCustomerName() + "'s Credit Limit: $" + customerBank.getCustomerCreditLimit());
                System.out.println("Enter 0 to cancel ");
                System.out.print("Enter the amount you want to deposit: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Canceled deposit");
                    return;
                }

                if (amount > 10000000) {
                    System.out.println("Transaction amount too large. Enter a smaller deposit amount.");
                    return;
                }

                // Valid amount exit loop and deposit
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid deposit. Deposit amount must be greater than 0.");
            }
        }
        customerBank.addMoney(amount);
        System.out.println("Successfully deposited " + amount + ".");
        System.out.println("New total credit Limit is: " + customerBank.getCustomerCreditLimit());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case remove money.
     */
    public void useCaseRemoveMoney() {
        if (customerBank == null) {
            System.out.println("Customer does not have a customer bank account registered.");
            return;
        }
        double amount;
        while (true) {
            try {
                System.out.println(customerBank.getCustomer().getCustomerName() + "'s Credit Limit: $" + customerBank.getCustomerCreditLimit());
                System.out.println("Enter 0 to cancel ");
                System.out.print("Enter the amount you want to withdraw: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Canceled withdraw");
                    return;
                }

                if (amount > customerBank.getCustomerCreditLimit()) {
                    System.out.println("Invalid withdrawal. Deposit amount must be less than your credit limit.");
                    continue;
                }

                // Valid amount exit loop and withdraw
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid withdrawal. Deposit amount must be less than your credit limit.");
            }
        }

        customerBank.removeMoney(amount);
        System.out.println("Successfully removed " + amount + ".");
        System.out.println("New total credit Limit is: " + customerBank.getCustomerCreditLimit());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case deposit money admin.
     *
     * @param customerBankAdmin the customer bank admin
     */
    public void useCaseDepositMoneyAdmin(CustomerBank customerBankAdmin) {
        if (customerBankAdmin == null) {
            System.out.println("Customer does not have a customer bank account registered.");
            return;
        }
        double amount;
        while (true) {
            try {
                System.out.println(customerBankAdmin.getCustomer().getCustomerName() + "'s Credit Limit: $" + customerBankAdmin.getCustomerCreditLimit());
                System.out.println("Enter 0 to cancel ");
                System.out.print("Enter the amount you want to deposit: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Canceled withdraw");
                    return;
                }

                if (amount > 10000000) {
                    System.out.println("Transaction amount too large. Enter a smaller deposit amount.");
                    return;
                }

                // Valid amount exit loop and deposit
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid deposit. Deposit amount must be greater than 0.");
            }
        }
        customerBankAdmin.addMoney(amount);
        System.out.println("Successfully deposited " + amount + ".");
        System.out.println("New total credit Limit is: " + customerBankAdmin.getCustomerCreditLimit());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Use case remove money admin.
     *
     * @param customerBankAdmin the customer bank admin
     */
    public void useCaseRemoveMoneyAdmin(CustomerBank customerBankAdmin) {
        if (customerBankAdmin == null) {
            System.out.println("Customer does not have a customer bank account registered.");
            return;
        }
        double amount;
        while (true) {
            try {
                System.out.println(customerBankAdmin.getCustomer().getCustomerName() + "'s Credit Limit: $" + customerBankAdmin.getCustomerCreditLimit());
                System.out.println("Enter 0 to cancel ");
                System.out.print("Enter the amount you want to withdraw: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Canceled withdraw");
                    return;
                }

                if (amount > customerBankAdmin.getCustomerCreditLimit()) {
                    System.out.println("Invalid withdrawal. Deposit amount must be less than your credit limit.");
                    continue;
                }

                // Valid amount exit loop and withdraw
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid withdrawal. Deposit amount must be less than your credit limit.");
            }
        }

        customerBankAdmin.removeMoney(amount);
        System.out.println("Successfully removed " + amount + ".");
        System.out.println("New total credit Limit is: " + customerBankAdmin.getCustomerCreditLimit());

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



// ------------------- Utilities -------------------
    /**
     * Create customer bank.
     */
    /* public void createCustomerBank() {
        this.customerBank = customerOrderSystemUserInterfaceController.createCustomerBankController(customer);
    }
     */
}