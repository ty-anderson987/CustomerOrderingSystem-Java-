import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the Chase bank
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class ChaseBank {
    private static final ChaseBank instanceBank = new ChaseBank();
    public final ArrayList<ChaseBankCustomer> chaseBankCustomerArrayList;
    public final ArrayList<CreditCardLimit> allCreditCardLimits;

// ------------------- Constructors -------------------
    /**
     * Creates a ChaseBank ArrayList of all customerBanks
     */
    private ChaseBank() {
        chaseBankCustomerArrayList = new ArrayList<>();
        allCreditCardLimits = new ArrayList<>();
    }



// ------------------- Getters -------------------
    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ChaseBank getInstance() {
        return instanceBank;
    }

    /**
     * Gets customers bank.
     *
     * @return the customers bank
     */
    public ArrayList<ChaseBankCustomer> getCustomersBank() {
        return chaseBankCustomerArrayList;
    }

    /**
     * Gets ChaseBankCustomer account.
     *
     * @param customer the customer
     * @return the customers bank
     */
    public ChaseBankCustomer getChaseBankCustomerByCustomer(Customer customer) {
        for (ChaseBankCustomer chaseBankCustomer : chaseBankCustomerArrayList) {
            if (chaseBankCustomer.getCustomerName().equals(customer.getCustomerName())
                    && chaseBankCustomer.getCustomerAddress().equals(customer.getCustomerAddress())) {
                return chaseBankCustomer;
            }
        }
        return null;
    }

    /**
     * Gets chase bank customer by credit card number.
     *
     * @param creditCardNumber the credit card number
     * @return the chase bank customer by credit card number
     */
    public ChaseBankCustomer getCustomerByCardNumber(String creditCardNumber) {
        for (ChaseBankCustomer customer : chaseBankCustomerArrayList) {
            for (CreditCardLimit card : customer.getCustomerCreditCardLimit()) {
                if (card.getCreditCardNumber().equals(creditCardNumber)) {
                    return customer;
                }
            }
        }
        return null;
    }



// ------------------- Adders/Removers -------------------
    /**
     * Create a ChaseBankCustomer account and add to customerBankArrayList
     *
     * @param customer customer object
     */
    public ChaseBankCustomer createChaseBankCustomer(Customer customer, String customerPassword) {
        // Search existing ChaseBankCustomers to find a matching ChaseBankCustomer account
        // If no matches found --> create a new ChaseBankCustomer
        for (ChaseBankCustomer chaseBankCustomer : chaseBankCustomerArrayList) {
            if (chaseBankCustomer.getCustomerName().equals(customer.getCustomerName())
                    && chaseBankCustomer.getCustomerAddress().equals(customer.getCustomerAddress())) {
                System.out.println("ChaseBankCustomer account already exists in ChaseBank.");
                return null;
            }
        }

        // Create a unique ChaseBankCustomer ID
        String chaseBankCustomerID;
        do {
            chaseBankCustomerID = generateRandomBankID();
        } while (doesBankIDNumberExist(chaseBankCustomerID));

        // Creates a new ChaseBankCustomer account and adds it to the chaseCustomerBankArrayList
        ChaseBankCustomer chaseBankCustomerToAdd = new ChaseBankCustomer(chaseBankCustomerID,
                                                                        customer.getCustomerName(),
                                                                        customerPassword,
                                                                        customer.getCustomerAddress());
        addCustomerToBank(chaseBankCustomerToAdd);
        return chaseBankCustomerToAdd;
    }

    /**
     * Add ChaseBankCustomer to ChaseBank.
     *
     * @param chaseBankCustomer the customer bank
     */
    public void addCustomerToBank(ChaseBankCustomer chaseBankCustomer) {
        if (chaseBankCustomer == null) {
            throw new IllegalArgumentException("ChaseBankCustomer is null");
        }
        chaseBankCustomerArrayList.add(chaseBankCustomer);
    }

    /**
     * Remove ChaseBankCustomer from ChaseBank.
     *
     * @param chaseBankCustomer the customer bank
     */
    public void removeCustomerFromBank(ChaseBankCustomer chaseBankCustomer) {
        chaseBankCustomerArrayList.remove(chaseBankCustomer);
    }



// ------------------- Chase Bank Use Cases -------------------
    /**
     * Process transaction checking if the inputted customer, cardNumber and bankID are matching with the customer in bank
     * Then runs validateTransaction
     * Returns the authentication code for order
     *
     * @param customer       the customer
     * @param creditCardNumber     the card number
     * @param amountCharged  the amount charged
     * @return the string
     */
    public String processTransaction(Customer customer, String creditCardNumber, double amountCharged) {
        // Find the Chase bank account associated with customer object
        ChaseBankCustomer chaseBankCustomer = getChaseBankCustomerByCustomer(customer);
        if (chaseBankCustomer == null) {
            System.out.println("Chase Bank Customer not found");
            return null;
        }

        // Find the specific card number with credit limit
        CreditCardLimit card = getCreditCardLimitByNumber(creditCardNumber);
        if (card == null) {
            System.out.println("Credit Card associated with " + chaseBankCustomer.getCustomerBankID() + " was not found");
            return null;
        }

        // Ensure that the user is authorized to use the card
        if (!card.isAuthorizedUser(chaseBankCustomer)) {
            System.out.println("Customer " + chaseBankCustomer.getCustomerBankID() + " is not authorized to use this card.");
            return null;
        }

        if (validateTransaction(card, amountCharged)) {
            return generateRandomCode();
        }
        return null;
    }

    /**
     * Checks if the customer has the money to make purchase
     *
     * @param card  the card with credit limit
     * @param amountCharged the amount charged
     * @return the boolean
     */
    public boolean validateTransaction(CreditCardLimit card, double amountCharged) {
        if (!((card.getCreditLimit()) >= amountCharged)) {
            System.out.printf("Insufficient funds on card %s. Requested: $%,.2f, Available: $%,.2f%n",
                    card.getCreditCardNumber(), amountCharged, card.getCreditLimit());

            return false;
        }
        card.removeMoney(amountCharged);
        return true;
    }



// ------------------- Chase Bank Utilities -------------------
    /**
     * Gets credit card limit by number.
     *
     * @param creditCardNumber the credit card number
     * @return the credit card limit by number
     */
    public CreditCardLimit getCreditCardLimitByNumber(String creditCardNumber) {
        for (ChaseBankCustomer customer : chaseBankCustomerArrayList) {
            for (CreditCardLimit limit : customer.getCustomerCreditCardLimit()) {
                if (limit.getCreditCardNumber().equals(creditCardNumber)) {
                    return limit;
                }
            }
        }
        return null;
    }

    /**
     * Add global credit card limit.
     *
     * @param creditCardLimit the credit card limit
     */
    public void addGlobalCreditCardLimit(CreditCardLimit creditCardLimit) {
        allCreditCardLimits.add(creditCardLimit);
    }

    /**
     * Generate random bank id string.
     *
     * @return the string
     */
    public String generateRandomBankID() {
        Random random = new Random();
        String randomBankID = "";
        for (int i = 0; i < 10; i++) {
            randomBankID += random.nextInt(10);
        }
        return randomBankID;
    }

    /**
     * Generate random code string.
     *
     * @return the string
     */
    public String generateRandomCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    /**
     * Does bank id number exist boolean.
     *
     * @param customerBankID the customer bank id
     * @return the boolean
     */
    public boolean doesBankIDNumberExist(String customerBankID) {
        for (ChaseBankCustomer inputCustomer : chaseBankCustomerArrayList) {
            if (inputCustomer.getCustomerBankID().equals(customerBankID)) {
                return true;
            }
        }
        return false;
    }



// ------------------- File Save -------------------
    // Format: customerBankID, customerName, customerAddress, creditCardNumber, creditLimit
    /**
     * Load ChaseBankCustomer accounts for the bank from bank file.
     * Keeps track of ChaseBankCustomers when program closes
     *
     * @param filename  the filename
     * @throws IOException the io exception
     */
    public void saveBankToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {

            // Get all the ChaseBankCustomer objects and String format them to be saved to ChaseBankCustomers file
            for (ChaseBankCustomer customer : chaseBankCustomerArrayList) {
                writer.print(customer.toFileString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }

    /**
     * Save ChaseBankCustomer accounts from customerBank arrayList to bank file
     * Keeps track of ChaseBankCustomers when program closes
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void loadBankFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Bank file not found — starting with empty bank.");
            return;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 6) continue; // Skip invalid lines

                String customerBankID = parts[0].trim();
                String customerName = parts[1].trim();
                String customerPassword = parts[2].trim();
                String customerAddress = parts[3].trim();
                String cardNumber = parts[4].trim();
                double creditLimit = Double.parseDouble(parts[5].trim());

                // Check if customer already exists
                ChaseBankCustomer existingCustomer = null;
                for (ChaseBankCustomer c : chaseBankCustomerArrayList) {
                    if (c.getCustomerBankID().equals(customerBankID)) {
                        existingCustomer = c;
                        break;
                    }
                }

                // If not, create a new customer
                if (existingCustomer == null) {
                    existingCustomer = new ChaseBankCustomer(customerBankID, customerName, customerPassword, customerAddress);
                    chaseBankCustomerArrayList.add(existingCustomer);
                }

                // Add the credit card to the customer's list
                CreditCardLimit card = new CreditCardLimit(cardNumber, creditLimit);
                card.addAuthorizedUser(existingCustomer);
                existingCustomer.getCustomerCreditCardLimit().add(card);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }
}
