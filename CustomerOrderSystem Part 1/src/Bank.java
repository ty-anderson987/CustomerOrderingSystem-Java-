import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Represents a bank in the Customer Order System (COS).
 * Stores customers, card numbers, credit limits, and authorization codes
 *
 * @author Ty Anderson R11885063
 * @version 1.4
 */
public class Bank {
    private static final Bank instanceBank = new Bank();
    private final ArrayList<CustomerBank> customersBank;
    private Bank() {
        customersBank = new ArrayList<>();
    }
    private CustomerOrderSystem customerOrderSystem;

// ------------------- Getters -------------------
    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Bank getInstance() {
        return instanceBank;
    }

    /**
     * Gets customers bank.
     *
     * @return the customers bank
     */
    public ArrayList<CustomerBank> getCustomersBank() {
        return customersBank;
    }

    /**
     * Gets customers bank.
     *
     * @param customer the customer
     * @return the customers bank
     */
    public CustomerBank getCustomersBank(Customer customer) {
        for (CustomerBank customerBank : customersBank) {
            if (customerBank.getCustomer().equalsCustomer(customer)) {
                return customerBank;
            }
        }
        for (Customer customerFound: customerOrderSystem.getCustomers()) {
            if (customerFound.equalsCustomer(customer)) {
                System.out.println("Customer found, but doesn't have a Customer Bank account.");
                return null;
            }
        }
        System.out.println("Customer not found");
        return null;
    }

    /**
     * Gets bank id for customer.
     *
     * @param customer the customer
     * @return the bank id for customer
     */
    public String getBankIDForCustomer(Customer customer) {
        for (CustomerBank customerBank : customersBank) {
            if (customerBank.getCustomer().equalsCustomer(customer)) {
                return customerBank.getCustomerBankID();
            }
        }
        return null;
    }



// ------------------- File Save -------------------
    // Format: customerName, customerID, creditCardNumber, creditLimit
    /**
     * Load customerBank accounts for the bank from bank file.
     * Keeps track of customerBanks when program closes
     *
     * @param filename  the filename
     * @param customers the customers
     * @throws IOException the io exception
     */
    public void loadBankFromFile(String filename, ArrayList<Customer> customers) throws IOException {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                // Splits string into parts seperated by ',' and puts the parts in a String array
                String[] parts = line.split(",");

                // Found Format: customerName, customerID, creditCardNumber, creditLimit
                if (parts.length == 5) {
                    String customerName = parts[0].trim();
                    String customerID = parts[1].trim();
                    String customerBankID = parts[2].trim();
                    String customerCreditCardNumber = parts[3].trim();
                    double customerCreditLimit = Double.parseDouble(parts[4].trim());

                    // Finds customer using customerID
                    Customer foundCustomer = null;
                    for (Customer customer : customers) {
                        if (customer.getCustomerID().equals(customerID)) {
                            foundCustomer = customer;
                            break;
                        }
                    }
                    if (foundCustomer == null) {
                        System.out.println("Customer not found");
                    }
                    // Creates a customerBank object from reading the file.
                    CustomerBank customerBank = new CustomerBank(foundCustomer, customerBankID, customerCreditCardNumber, customerCreditLimit);
                    customersBank.add(customerBank); // Add customerBank object to a ArrayList
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Save customerBank accounts from customerBank arrayList to bank file
     * Keeps track of customerBanks when program closes
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void saveBankToFile(String filename) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(filename);

            // Get all the customerBank objects and String format them to be saved to bank file
            for (CustomerBank customerBank : customersBank) {
                writer.println(customerBank.toFileString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }



// ------------------- Adders/Removers -------------------
    /**
     * Creates a customer to the banks' customerBank arraylist
     *
     * @param customer    the customer
     * @param creditLimit the credit limit
     */
    public void createCustomerToBank(Customer customer, String creditCardNumber, double creditLimit) {
        // Searches the customerBank arraylist to find a matching customerBank
        // If no match found -> create a new customer
        for (CustomerBank customerBank : customersBank) {
            if (customerBank.getCustomer().equalsCustomer(customer)) {
                System.out.println("Customer already exists in the bank.");
                return;
            }
        }
        // Creates a unique bankID
        String customerBankID;
        do {
            customerBankID = generateRandomBankID();
        } while (doesBankIDNumberExist(customerBankID));

        // Creates a new customerBank object and adds it to the customerBank arrayList
        CustomerBank customerBankToAdd = new CustomerBank(customer, customerBankID, creditCardNumber, creditLimit);
        addCustomerToBank(customerBankToAdd);
        System.out.println("New customerBank added to the bank.");
    }

    /**
     * Add customer to bank.
     *
     * @param customerBank the customer bank
     */
    public void addCustomerToBank(CustomerBank customerBank) {
        if (customerBank == null) {
            throw new IllegalArgumentException("customerBank is null");
        }
        customersBank.add(customerBank);
    }

    /**
     * Remove customer from bank.
     *
     * @param customerBank the customer bank
     */
    public void removeCustomerFromBank(CustomerBank customerBank) {
        customersBank.remove(customerBank);
    }

    /**
     * Customer bank exists boolean.
     *
     * @param customer the customer
     * @return the boolean
     */
    public boolean customerBankExists(Customer customer) {
            return getCustomersBank(customer) != null;
        }



// ------------------- Bank Utilities -------------------
    /**
     * Process transaction checking if the inputted customer, cardNumber and bankID are matching with the customer in bank
     * Then runs validateTransaction
     * Returns the authentication code for order
     *
     * @param customer       the customer
     * @param customerBankID the customer bank id
     * @param cardNumber     the card number
     * @param amountCharged  the amount charged
     * @return the string
     */
    public String processTransaction(Customer customer, String customerBankID, String cardNumber, double amountCharged) {
        CustomerBank foundCustomerBank = null;

        // Find customer in bank arraylist
        for (CustomerBank customerBank : customersBank) {
            if (customerBank.getCustomerBankID().equals(customerBankID) &&
                    customerBank.getCustomerCreditCardNumber().equals(cardNumber) &&
                    customerBank.getCustomer().getCustomerName().equals(customer.getCustomerName())) {
                foundCustomerBank = customerBank;
                break;
            }
        }
        // If not found, decline
        if (foundCustomerBank == null) {
            System.out.println("Customer not found");
            return null;
        }
        // If found, check if transaction is valid
        if (validateTransaction(foundCustomerBank, amountCharged)) {
            return generateRandomCode();
        } else {
            System.out.println("Insufficient funds");
            return null;
        }
    }

    /**
     * Checks if the customer has the money to make purchase
     *
     * @param customerBank  the customer bank
     * @param amountCharged the amount charged
     * @return the boolean
     */
    public boolean validateTransaction(CustomerBank customerBank, double amountCharged) {
        if (!(customerBank.getCustomerCreditLimit() >= amountCharged)) {
            return false;
        }
        customerBank.removeMoney(amountCharged);
        return true;
    }



// ------------------- Misc. -------------------
    /**
     * Sets customer order system.
     *
     * @param cos the cos
     */
    public void setCustomerOrderSystem(CustomerOrderSystem cos) {
        this.customerOrderSystem = cos; // you’d need a field for it
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
        for (CustomerBank customerBank : customersBank) {
            if(customerBank.getCustomerBankID().equals(customerBankID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Does card number exist boolean.
     *
     * @param cardNumber the card number
     * @return the boolean
     */
    public boolean doesCardNumberExist(String cardNumber) {
        for (CustomerBank customerBank : customersBank) {
            if (customerBank.getCustomerCreditCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }
}