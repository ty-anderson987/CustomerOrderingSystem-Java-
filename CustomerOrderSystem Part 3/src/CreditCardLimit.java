import java.util.ArrayList;

public class CreditCardLimit {
    private final ArrayList<ChaseBankCustomer> authorizedUsers;
    private final String creditCardNumber;
    private double creditLimit;

// ------------------- Constructors -------------------
    /**
     * Creates a credit card / limit object
     * @param creditCardNumber
     * @param creditLimit
     */
    public CreditCardLimit(String creditCardNumber, double creditLimit) {
        authorizedUsers = new ArrayList<>();
        this.creditCardNumber = creditCardNumber;
        this.creditLimit = creditLimit;
    }



// ------------------- Getters -------------------
    /**
     * Gets authorized users.
     *
     * @return the authorized users
     */
    public ArrayList<ChaseBankCustomer> getAuthorizedUsers() {
        return authorizedUsers;
    }

    /**
     * Gets chase bank customer.
     *
     * @return the chase bank customer
     */
    public ChaseBankCustomer getChaseBankCustomer() {
        if (authorizedUsers.isEmpty()) {
            return null;
        }
        // Returns the first customer with CredtiCardLimit
        return authorizedUsers.get(0);
    }

    public ArrayList<ChaseBankCustomer> getAllAuthorizedUsers() {
        return authorizedUsers;
    }

    /**
     * Gets creditCardNumber.
     *
     * @return the creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Gets creditLimit
     *
     * @return the creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }



// ------------------- Setters -------------------
    /**
     * Set creditLimit
     *
     * @param creditLimit
     */
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }



// ------------------- Utilities -------------------
    /**
     * Add authorized user.
     *
     * @param chaseBankCustomer the chase bank customer
     */
    public void addAuthorizedUser(ChaseBankCustomer chaseBankCustomer) {
        if (!authorizedUsers.contains(chaseBankCustomer)) {
            authorizedUsers.add(chaseBankCustomer);
        }
    }

    /**
     * Is authorized user boolean.
     *
     * @param chaseBankCustomer the chase bank customer
     * @return the boolean
     */
    public boolean isAuthorizedUser(ChaseBankCustomer chaseBankCustomer) {
        return authorizedUsers.contains(chaseBankCustomer);
    }

    /**
     * Add money.
     *
     * @param money the money
     */
    public void addMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.creditLimit += money;
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
        if (money > creditLimit) {
            throw new IllegalArgumentException("Money cannot be greater than credit limit");
        }
        this.creditLimit -= money;
    }



// ------------------- toString() -------------------
    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        return "   Card: " + creditCardNumber + " | Limit: $" + String.format("%.2f", creditLimit);
    }
}