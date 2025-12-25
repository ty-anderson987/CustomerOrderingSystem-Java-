/**
 * Represents a customer in the Customer Order System (COS).
 * Stores account details, login credentials, and security information.
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class Customer {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerPassword;
    private String customerSecurityQuestion;
    private String customerSecurityAnswer;

// ------------------- Constructors -------------------

    /**
     * Constructs a new Customer object with validation checks for all fields.
     *
     * @param id               the id
     * @param name             the name
     * @param address          the address
     * @param password         the password
     * @param securityQuestion the security question
     * @param securityAnswer   the security answer
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public Customer(String id, String name, String address, String password, String securityQuestion, String securityAnswer) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Customer ID must not be null or empty");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password. Must be at least 6 characters and include a digit, a special character (@,#,$,%,&,*) and an uppercase letter.");
        }
        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Customer name or address must not be null or empty");
        }
        if (securityQuestion == null || securityQuestion.isEmpty() || securityAnswer == null || securityAnswer.isEmpty()) {
            throw new IllegalArgumentException("Security question or security answer must not be null or empty");
        }
        this.customerID = id;
        this.customerName = name;
        this.customerAddress = address;
        this.customerPassword = password;
        this.customerSecurityQuestion = securityQuestion;
        this.customerSecurityAnswer = securityAnswer;
    }

    /**
     * Constructs a new Customer default object
     */
    public Customer() {
        this.customerID = "";
        this.customerName = "";
        this.customerAddress = "";
        this.customerPassword = "";
        this.customerSecurityQuestion = "";
        this.customerSecurityAnswer = "";
    }

    /**
     * Constructs a copy of inputted object
     *
     * @param object2 for customer
     */
    public Customer(Customer object2) {
        this.customerID = object2.customerID;
        this.customerName = object2.customerName;
        this.customerAddress = object2.customerAddress;
        this.customerPassword = object2.customerPassword;
        this.customerSecurityQuestion = object2.customerSecurityQuestion;
        this.customerSecurityAnswer = object2.customerSecurityAnswer;
    }

    /**
     * Checks if a password is valid.
     * A valid password must be at least 6 characters long,
     * contain at least one digit, one uppercase letter,
     * and one special character from @, #, $, %, &, *.
     *
     * @param password the password string to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;

        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasSpecial = password.matches(".*[@#$%&*].*");

        return hasDigit && hasUpper && hasSpecial;
    }



// ------------------- Getters -------------------
    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public String getCustomerID() {
        return customerID;
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
     * Gets customer address.
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
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
     * Gets customer security question.
     *
     * @return the customer security question
     */
    public String getCustomerSecurityQuestion() {
        return customerSecurityQuestion;
    }

    /**
     * Gets customer security answer.
     *
     * @return the customer security answer
     */
    public String getCustomerSecurityAnswer() {
        return customerSecurityAnswer;
    }



// ------------------- Setters -------------------
    /**
     * Sets customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets customer password.
     *
     * @param customerPassword the customer password
     */
    public void setCustomerPassword(String customerPassword) {
        if (!isValidPassword(customerPassword)) {
            throw new IllegalArgumentException("Invalid password.");
        }
        this.customerPassword = customerPassword;
    }

    /**
     * Sets customer address.
     *
     * @param customerAddress the customer address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets customer security question.
     *
     * @param customerSecurityQuestion the customer security question
     */
    public void setCustomerSecurityQuestion(String customerSecurityQuestion) {
        this.customerSecurityQuestion = customerSecurityQuestion;
    }

    /**
     * Sets customer security answer.
     *
     * @param customerSecurityAnswer the customer security answer
     */
    public void setCustomerSecurityAnswer(String customerSecurityAnswer) {
        this.customerSecurityAnswer = customerSecurityAnswer;
    }



// ------------------- Utilities -------------------
    /**
     * Register customer to Bank and creates a new customerBank account
     *
     * @param system      the system
     * @param creditLimit the credit limit
     */
    /*public void registerCustomerBank(CustomerOrderSystem system, double creditLimit) {
        if (system == null) {
            throw new IllegalArgumentException("System must not be null");
        }
        // System can have a customer but no bank account
        // Bank must have a customer and customerBank account
        system.addCustomer(this);
        system.addCustomerBank(this, creditLimit);
    }

     */

    /**
     * Equals customer boolean.
     *
     * @param customer the customer
     * @return the boolean
     */
    public boolean equalsCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }
        if (this.customerID == null || customer.customerID == null) {
            return false;
        }
        return this.customerID.equals(customer.customerID);
    }

    /**
     * Copy customer.
     *
     * @return the customer
     */
    public Customer copy() {
        return new Customer(this.getCustomerID(), this.getCustomerName(), this.customerAddress, this.customerPassword, this.customerSecurityQuestion, this.customerSecurityAnswer);
    }



// ------------------- toString(). -------------------
    @Override
    public String toString() {
        return "Customer{" +
                "ID='" + customerID + '\'' +
                ", Name='" + customerName + '\'' +
                ", Address='" + customerAddress + '\'' +
                ", SecurityQuestion='" + customerSecurityQuestion + '\'' +
                '}';
    }

    /**
     * To file string string.
     *
     * @return the string
     */
    public String toFileString() {
        return customerID + "|" +
                customerName + "|" +
                customerAddress + "|" +
                customerPassword + "|" +
                customerSecurityQuestion + "|" +
                customerSecurityAnswer;
    }

    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        return String.format(
                "| ID: %-10s | Name: %-20s | Address: %-25s | Password: %-15s | Question: %-40s | Answer: %-20s |",
                customerID,
                customerName,
                customerAddress,
                customerPassword,
                customerSecurityQuestion,
                customerSecurityAnswer
        );
    }
}