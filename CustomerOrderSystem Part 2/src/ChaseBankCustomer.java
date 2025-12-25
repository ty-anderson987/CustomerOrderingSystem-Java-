import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the ChaseBank Customer
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class ChaseBankCustomer {
    private final String customerBankID;
    private final String customerName;
    private final String customerPassword;
    private final String customerAddress;
    private final ArrayList<CreditCardLimit> customerCreditCardLimit;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Chase bank customer.
     *
     * @param customerBankID  the customer bank id
     * @param customerName    the customer name
     * @param customerAddress the customer address
     */
    public ChaseBankCustomer(String customerBankID, String customerName, String customerPassword, String customerAddress) {
        this.customerBankID = customerBankID;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customerAddress = customerAddress;
        this.customerCreditCardLimit = new ArrayList<>();
    }



// ------------------- Getters -------------------
    /**
     * Gets customer bank id.
     *
     * @return the customer bank id
     */
    public String getCustomerBankID() {
        return customerBankID;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets customer password.
     *
     * @return the customer password
     */
    public String getCustomerPassword() {
        return customerPassword;
    }

    /**
     * Gets customer address.
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Gets customer credit card limit.
     *
     * @return the customer credit card limit
     */
    public ArrayList<CreditCardLimit> getCustomerCreditCardLimit() {
        return customerCreditCardLimit;
    }

    /**
     * Get CreditCardLimit object for the given card number
     */
    public CreditCardLimit getCustomerCreditCardLimitByCreditCardNumber(String creditCardNumber) {
        for (CreditCardLimit card : customerCreditCardLimit) {
            if (creditCardNumber.equals(card.getCreditCardNumber())) {
                return card; // Found
            }
        }
        return null; // Not found
    }



// ------------------- Adders/Removers -------------------
    /**
     * Create a CreditCardLimit card and add it to the customerCreditCardLimit arraylist
     *
     */
    public CreditCardLimit createCreditCardLimit (double creditLimit) {
        // Search existing CreditCardLimit to find a matching card number
        // If no matches found --> create a new CreditCardLimit
        String creditCardNumber;
        do {
            creditCardNumber = generateRandomCardNumber();
        } while (ChaseBank.getInstance().getCreditCardLimitByNumber(creditCardNumber) != null);

        // Create a new CreditCardLimit object and adds it to the customerCreditCardLimit arrayList
        CreditCardLimit creditCardLimit = new CreditCardLimit(creditCardNumber, creditLimit);
        addCreditCardLimit(creditCardLimit);
        ChaseBank.getInstance().addGlobalCreditCardLimit(creditCardLimit);
        return creditCardLimit;
    }

    /**
     * Create credit card limit credit card limit.
     *
     * @param creditLimit      the credit limit
     * @param creditCardNumber the credit card number
     * @return the credit card limit
     */
    public CreditCardLimit createCreditCardLimit (double creditLimit, String creditCardNumber) {
        ChaseBank chaseBank = ChaseBank.getInstance();
        if (!creditCardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid credit card number. Must be exactly 16 digits.");
        }

        // Check if card exists in the bank
        CreditCardLimit existingCreditCardLimit = chaseBank.getCreditCardLimitByNumber(creditCardNumber);
        if (existingCreditCardLimit != null) {
            // Card already exists --> Link
            addCreditCardLimit(existingCreditCardLimit);
            return existingCreditCardLimit;
        }

        // Card doesn’t exist → create a new one
        CreditCardLimit newCard = new CreditCardLimit(creditCardNumber, creditLimit);
        addCreditCardLimit(newCard);
        chaseBank.addGlobalCreditCardLimit(newCard);
        return newCard;
    }

    /**
     * Add CreditCardLimit to customerCreditCardLimit arrayList.
     *
     * @param card the customer bank
     */
    public void addCreditCardLimit(CreditCardLimit card) {
        if (card == null) {
            throw new IllegalArgumentException("card is null");
        }
        if (!customerCreditCardLimit.contains(card)) {
            customerCreditCardLimit.add(card);
            card.addAuthorizedUser(this);
        }
    }

    /**
     * Remove CreditCardLimit from customerCreditCardLimit arraylist.
     *
     * @param card the customer bank
     */
    public void removeCreditCardLimit(CreditCardLimit card) {
        customerCreditCardLimit.remove(card);
    }





// ------------------- ChaseBankCustomer Utilities -------------------
    /**
     * Generate random card number string.
     *
     * @return the string
     */
    public String generateRandomCardNumber() {
        Random random = new Random();
        String cardNumber = "";
        for (int i = 0; i < 16; i++) {
            cardNumber += random.nextInt(10);
        }
        return cardNumber;
    }

    /**
     * Does card number exist boolean.
     *
     * @param cardNumber the card number
     * @return the boolean
     */
    public boolean doesCardNumberExist(String cardNumber) {
        for (CreditCardLimit card : customerCreditCardLimit) {
            if (card.getCreditCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }



// ------------------- toString() -------------------
    /**
     * To file string string.
     *
     * @return the string
     */
    public String toFileString() {
        String result = "";
        for (CreditCardLimit card : customerCreditCardLimit) {
            result += customerBankID + "," +
                    customerName + "," +
                    customerPassword + "," +
                    customerAddress + "," +
                    card.getCreditCardNumber() + "," +
                    card.getCreditLimit() + "\n";
        }
        return result;
    }

    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        String result = "------------------------------------------------\n";
        result += "Bank ID: " + customerBankID + "\n";
        result += "Customer Name: " + customerName + "\n";
        result += "Customer Password: " + customerPassword + "\n";
        result += "Address: " + customerAddress + "\n";
        for (CreditCardLimit card : customerCreditCardLimit) {
            result += "   Card: " + card.getCreditCardNumber() + " | Limit: $" + String.format("%.2f", card.getCreditLimit()) + "\n";
        }
        return result;
    }

    /**
     * To Account Detail string
     */
    public String toAccountDetailsString() {
        String result = "------------------------------------------------\n";
        result += "Bank ID: " + customerBankID + "\n";
        result += "Customer Name: " + customerName + "\n";
        result += "Customer Password: " + customerPassword + "\n";
        result += "Address: " + customerAddress + "\n";
        return result;
    }

    /**
     * To Credit Limit String
     */
    public String toViewCreditCardLimitString() {
        String result = "------------------------------------------------\n";
        for (CreditCardLimit card : customerCreditCardLimit) {
            result += "   Card: " + card.getCreditCardNumber() + " | Limit: $" + String.format("%.2f", card.getCreditLimit()) + "\n";
        }
        return result;
    }
}
