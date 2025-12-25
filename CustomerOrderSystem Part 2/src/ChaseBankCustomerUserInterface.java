import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents the Credit Card NUmber with CreditLimit
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class ChaseBankCustomerUserInterface {
    private final ChaseBankCustomer chaseBankCustomer;
    private final Scanner scanner;

// ------------------- Constructors -------------------
    public ChaseBankCustomerUserInterface(Scanner scanner, ChaseBankCustomer chaseBankCustomer) {
        this.chaseBankCustomer = chaseBankCustomer;
        this.scanner = scanner;
    }



// ------------------- Chase Bank Customer User Interface -------------------
    /**
     * ChaseBankCustomer menu.
     */
    public void ChaseBankCustomerMenu() {
        int choice;

        while (true) {
            System.out.println("\n===== Chase Bank Customer =====");
            System.out.println("1. View Account Details");
            System.out.println("2. View Credit Card Limits");
            System.out.println("3. Create a new Credit Card Number / Limit");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            // Make sure the user input is a number
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number between 1 and 6.");
                continue;
            }

            // Switch case choose option
            switch (choice) {
                case 1:
                    viewAccountDetails();
                    break;
                case 2:
                    viewCreditCardLimits();
                    break;
                case 3:
                    createCreditCardLimits();
                    break;
                case 4:
                    depositMoney();
                    break;
                case 5:
                    withdrawMoney();
                    break;
                case 6:
                    System.out.println("Exiting Program...");
                    return;
                default:
                    System.out.println("Invalid choice. Enter a number between 1 and 6.");
                    break;
            }
        }
    }



// ------------------- Utilities -------------------
    /**
     * View account details.
     */
    public void viewAccountDetails() {
        System.out.println(chaseBankCustomer.toAccountDetailsString());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * View credit card limits.
     */
    public void viewCreditCardLimits() {
        System.out.println(chaseBankCustomer.toViewCreditCardLimitString());

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Create credit card limits.
     */
    public void createCreditCardLimits() {
        double depositAmount = 0.0;

        // Keep asking until valid positive deposit is entered
        while (true) {
            System.out.println("Enter -1 to exit");
            System.out.print("Enter your initial deposit: ");
            try {
                depositAmount = Double.parseDouble(scanner.nextLine().trim());
                if (depositAmount == -1) {
                    return;
                }
                if (depositAmount <= 0) {
                    System.out.println("Deposit amount must be a positive number. Please try again.\n");
                    continue;
                }
                break; // valid input → exit loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");
            }
        }

        CreditCardLimit creditCard = chaseBankCustomer.createCreditCardLimit(depositAmount);
        if (creditCard != null) {
            System.out.println("\nCredit Card Created Successfully!");
            System.out.println("------------------------------------");
            System.out.println("Card Number : " + creditCard.getCreditCardNumber());
            System.out.printf("Credit Limit: $%,.2f%n", creditCard.getCreditLimit());
            System.out.println("------------------------------------");
        } else {
            System.out.println("Failed to create credit card. Please try again.");
        }

        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Deposit money.
     */
    public void depositMoney() {
        if (chaseBankCustomer == null) {
            System.out.println("Customer does not have a ChaseBankCustomer account registered.");
            return;
        }

        // ------------------- Print all CreditCardLimits -------------------
            ArrayList<CreditCardLimit> creditCards = chaseBankCustomer.getCustomerCreditCardLimit();
            if (creditCards.isEmpty()) {
                System.out.println("There are no credit cards available to deposit.");
                return;
            }

            for (int i = 1; i <  creditCards.size() + 1; i++) {
                System.out.println(i + ":" + creditCards.get(i - 1).toUIString());
            }

        // ------------------- Choose CreditCardLimits -------------------
        // Make sure user enters a number valid
        // Select Credit Card
        CreditCardLimit creditCard;
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the Credit Card you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return;
            }

            if (choice < 1 || choice > creditCards.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + creditCards.size() + ".");
                return;
            }

            creditCard = creditCards.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + creditCards.size() + ".");
            return;
        }


        // ------------------- Deposit -------------------
        double amount;
        while (true) {
            try {
                System.out.println(chaseBankCustomer.getCustomerName() + "'s Credit Limit: $" +  creditCard.getCreditLimit());
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
        creditCard.addMoney(amount);
        System.out.printf("Successfully deposited $%,.2f.%n", amount);
        System.out.printf("New total credit limit: $%,.2f%n", creditCard.getCreditLimit());


        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    /**
     * Withdraw money.
     */
    public void withdrawMoney() {
        if (chaseBankCustomer == null) {
            System.out.println("Customer does not have a ChaseBankCustomer account registered.");
            return;
        }

        // ------------------- Print all CreditCardLimits -------------------
        ArrayList<CreditCardLimit> creditCards = chaseBankCustomer.getCustomerCreditCardLimit();
        if (creditCards.isEmpty()) {
            System.out.println("There are no credit cards available to deposit.");
            return;
        }

        for (int i = 1; i <  creditCards.size() + 1; i++) {
            System.out.println(i + ":" + creditCards.get(i - 1).toUIString());
        }

        // ------------------- Choose CreditCardLimits -------------------
        // Make sure user enters a number valid
        // Select Credit Card
        CreditCardLimit creditCard;
        try {
            System.out.print("\nEnter 0 to cancel \n Enter the number corresponding to the Credit Card you want to choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return;
            }

            if (choice < 1 || choice > creditCards.size()) {
                System.out.println("Invalid input. Enter a number between 1 and " + creditCards.size() + ".");
                return;
            }

            creditCard = creditCards.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + creditCards.size() + ".");
            return;
        }


        // ------------------- Deposit -------------------
        double amount;
        while (true) {
            try {
                System.out.println(chaseBankCustomer.getCustomerName() + "'s Credit Limit: $" +  creditCard.getCreditLimit());
                System.out.println("Enter 0 to cancel ");
                System.out.print("Enter the amount you want to withdraw: ");
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Canceled withdraw");
                    return;
                }

                if (amount > creditCard.getCreditLimit()) {
                    System.out.println("Invalid withdrawal. Withdraw amount must be less than your credit limit.");
                    continue;
                }

                // Valid amount exit loop and deposit
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid withdrawal. Withdraw amount must be less than your credit limit.");
            }
        }

        creditCard.removeMoney(amount);
        System.out.printf("Successfully withdraw $%,.2f.%n", amount);
        System.out.printf("New total credit limit: $%,.2f%n", creditCard.getCreditLimit());


        // Prompt user to continue
        System.out.println();
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }
}
