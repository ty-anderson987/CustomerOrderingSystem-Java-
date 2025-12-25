/**
 * Represents a customerBank in the Customer Order System (COS).
 * Stores account details and bank information.
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CustomerBank {
    private Customer customer;
    private final String customerBankID;
    private final String customerCreditCardNumber;
    private double customerCreditLimit;

// ------------------- Constructors -------------------
    /**
     * Creates a customerBank object
     *
     * @param customer                 the customer
     * @param customerBankID           the customer bank id
     * @param customerCreditCardNumber the customer credit card number
     * @param customerCreditLimit      the customer credit limit
     */
    public CustomerBank(Customer customer, String customerBankID, String customerCreditCardNumber, double customerCreditLimit) {
        this.customer = customer;
        this.customerBankID = customerBankID;
        this.customerCreditCardNumber = customerCreditCardNumber;
        this.customerCreditLimit = customerCreditLimit;
    }

    /**
     * Instantiates a new Customer bank.
     *
     * @param customer the customer
     */
    public CustomerBank(Customer customer) {
        this.customer = customer;
        this.customerBankID = "";
        this.customerCreditLimit = 0;
        this.customerCreditCardNumber = "";
    }

    /**
     * Instantiates a new Customer bank.
     */
    public CustomerBank() {
        this.customer = null;
        this.customerBankID = null;
        this.customerCreditLimit = 0;
        this.customerCreditCardNumber = "";
    }



// ------------------- Getters -------------------
    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets customer bank id.
     *
     * @return the customer bank id
     */
    public String getCustomerBankID() {
        return customerBankID;
    }

    /**
     * Gets customer credit card number.
     *
     * @return the customer credit card number
     */
    public String getCustomerCreditCardNumber() {
        return customerCreditCardNumber;
    }

    /**
     * Gets customer credit limit.
     *
     * @return the customer credit limit
     */
    public double getCustomerCreditLimit() {
        return customerCreditLimit;
    }



// ------------------- Setters -------------------
    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets customer credit limit.
     *
     * @param customerCreditLimit the customer credit limit
     */
    public void setCustomerCreditLimit(double customerCreditLimit) {
        this.customerCreditLimit = customerCreditLimit;
    }



// ------------------- Utilities -------------------
    /**
     * Add money.
     *
     * @param money the money
     */
    public void addMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.customerCreditLimit += money;
    }

    /**
     * Remove money.
     *
     * @param money the money
     */
    public void removeMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        if (money > customerCreditLimit) {
            throw new IllegalArgumentException("Money cannot be greater than credit limit");
        }
        this.customerCreditLimit -= money;
    }

    /**
     * Equals customer bank boolean.
     *
     * @param customerBank the customer bank
     * @return the boolean
     */
    public boolean equalsCustomerBank(CustomerBank customerBank) {
        return this.customerBankID != null && this.customerBankID.equals(customerBank.customerBankID);
    }



// ------------------- toString() -------------------
    /**
     * To file string string.
     *
     * @return the string
     */
    public String toFileString() {
        return getCustomer().getCustomerName() + "," +
                getCustomer().getCustomerID() + "," +
                customerBankID + "," +
                customerCreditCardNumber + "," +
                customerCreditLimit;
    }

    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        return String.format(
                "Customer Name: %-15s | Bank ID: %-8s | Credit Card: %-19s | Credit Limit: $%-10.2f",
                customer.getCustomerName(),
                customerBankID,
                customerCreditCardNumber,
                customerCreditLimit
        );
    }
}