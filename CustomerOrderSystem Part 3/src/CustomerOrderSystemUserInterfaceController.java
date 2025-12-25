import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents an cosUI Controller in the Customer Order System (COS).
 * Controls the logic for cos UI
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CustomerOrderSystemUserInterfaceController {
    private final CustomerOrderSystem customerOrderSystem;
    private final Scanner scanner;

    // Controllers
    private final CustomerController customerController;
    private final OrderController orderController;
    private final AdminController adminController;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer order system user interface controller.
     *
     * @param customerOrderSystem the customer order system
     * @param scanner             the scanner
     */
    public CustomerOrderSystemUserInterfaceController(CustomerOrderSystem customerOrderSystem, Scanner scanner) {
        this.customerOrderSystem = customerOrderSystem;
        this.scanner = scanner;
        this.customerController = new CustomerController(this);
        this.orderController = new OrderController(this);
        this.adminController = new AdminController(customerOrderSystem, this, scanner);
    }



// ------------------- Getters -------------------
    /**
     * Gets admin controller.
     *
     * @return the admin controller
     */
    public AdminController getAdminController() {
        return adminController;
    }

    /**
     * Gets order controller.
     *
     * @return the order controller
     */
    public OrderController getOrderController() {
        return orderController;
    }

    /**
     * Gets customer controller.
     *
     * @return the customer controller
     */
    public CustomerController getCustomerController() {
        return customerController;
    }

    /**
     * Gets customer order system.
     *
     * @return the customer order system
     */
    public CustomerOrderSystem getCustomerOrderSystem() {
        return customerOrderSystem;
    }



// ------------------- Use Cases -------------------
    /**
     * Use case log on customer.
     *
     * @return the customer
     */
    public Customer useCaseLogOn() {
        // Get all the customers
        ArrayList<Customer> customers = customerOrderSystem.getCustomers();
        Customer selectedCustomer = null;
        String customerID;
        String customerPassword;
        String customerSecurityAnswer;

        // Attempt login
        // ID/Password
        int attempts = 0;
        System.out.println("Enter -1 to exit");
        while (attempts < 3) {
            // Find customerID
            System.out.print("Enter Customer ID: ");
            customerID = scanner.nextLine();
            if (customerID.equals("-1")) {
                return null;
            }
            for (Customer customer : customers) {
                if (customerID.equalsIgnoreCase(customer.getCustomerID().trim())) {
                    selectedCustomer = customer;
                    break;
                }
            }
            if (selectedCustomer == null) {
                System.out.println("No account with that customer ID");
                return null;
            }

            // Check password match
            System.out.print("Enter Password: ");
            customerPassword = scanner.nextLine();
            if (customerPassword.equals("-1")) {
                return null;
            }
            if (customerPassword.equals(selectedCustomer.getCustomerPassword())) {
                break;
            } else {
                System.out.println("Password is incorrect");
                attempts++;
            }
        }
        if (attempts >= 3) {
            System.out.println("Too many failed login attempts. Try again later.");
            return null;
        }

        // Check security answer
        System.out.print("Here is your security question: ");
        System.out.print(selectedCustomer.getCustomerSecurityQuestion());
        System.out.print("\n");
        System.out.print("What is your security question answer: ");
        customerSecurityAnswer = scanner.nextLine();
        if (customerSecurityAnswer.equals("-1")) {
            return null;
        }
        if (!customerSecurityAnswer.equalsIgnoreCase(selectedCustomer.getCustomerSecurityAnswer())) {
            System.out.println("Your security answer is incorrect");
            return null;
        }

        return selectedCustomer;
    }

    /**
     * Use case create account controller customer.
     *
     * @return the customer
     */
    public Customer useCaseCreateAccountController() {
        try {
            String customerID;
            String customerName;
            String customerAddress;
            String customerPassword;
            String customerSecurityQuestion;
            String customerSecurityAnswer;

            // Do while checks the ID to see if it exists already
            boolean idExists;
            System.out.println("Enter -1 to exit");
            do {
                idExists = false;
                System.out.print("Enter Customer ID: ");
                customerID = scanner.nextLine().trim();
                if (customerID.equals("-1")) {
                    return null;
                }

                // Check if ID already exists
                for (Customer existingCustomer : customerOrderSystem.getCustomers()) {
                    if (existingCustomer.getCustomerID().equals(customerID)) {
                        System.out.println("Invalid ID. Enter another ID.");
                        idExists = true;
                        break;
                    }
                }
            } while (idExists);
            System.out.println("Password has to be least 6 characters and include a digit, a special character (@,#,$,%,&,*) and an uppercase letter.");
            // Do while checks the password to see if it's valid or not
            do {
                System.out.print("Enter Customer Password: ");
                customerPassword = scanner.nextLine().trim();
                if (customerPassword.equals("-1")) {
                    return null;
                }
            } while (!Customer.isValidPassword(customerPassword));


            // Step 6 - Get customer information
            do {
                System.out.print("Enter Customer Name (First, Last Name): ");
                customerName = scanner.nextLine().trim();
                System.out.print("Enter Customer Address: ");
                customerAddress = scanner.nextLine().trim();

                if (customerName.isEmpty() || customerAddress.isEmpty()) {
                    System.out.println("Customer Name or Address is empty");
                } else {
                    break;
                }
            } while (true);


            // Step 6 - Get Customer Credit Card Information
            String cardNumber;
            boolean validCard = false;
            do {
                System.out.print("Enter your 16-digit credit card number: ");
                cardNumber = scanner.nextLine().trim();

                if (!cardNumber.matches("\\d{16}")) {
                    System.out.println("Invalid card number. Must be exactly 16 digits.");
                } else {
                    validCard = true;
                }
            } while (!validCard);


            System.out.println("All data entered is valid and not Null");


            // Step 8/9 - Get question / answer
            // Format: string array [question, answer]
            String[] result;
            result = getSecurityQuestionAnswer();
            customerSecurityQuestion = result[0];
            customerSecurityAnswer = result[1];


            // Create new customer object and adds to the ArrayList customers
            Customer newCustomer = new Customer(customerID, customerName, customerAddress, customerPassword, customerSecurityQuestion, customerSecurityAnswer, cardNumber);
            customerOrderSystem.addCustomer(newCustomer);
            System.out.println("Your credit card has been linked to your account successfully.");
            System.out.println("Customer added successfully!");
            return newCustomer;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the customer.");
            return null;
        }
    }

    /**
     * ChaseBankCustomer log on
     */
    public ChaseBankCustomer chaseBankCustomerLogOn() {
        ChaseBank chaseBank = ChaseBank.getInstance();

        System.out.print("Enter your Bank ID: ");
        String bankID = scanner.nextLine().trim();

        System.out.print("Enter your Password: ");
        String password = scanner.nextLine().trim();

        for (ChaseBankCustomer customer : chaseBank.getCustomersBank()) {
            if (customer.getCustomerBankID().equals(bankID) && customer.getCustomerPassword().equals(password)) {
                return customer;
            }
        }
        System.out.println("Invalid Bank ID or Password.");
        return null;
    }

    /**
     * ChaseBankCustomer account controller
     */
    public ChaseBankCustomer chaseBankCustomerCreateAccountController() {
        System.out.println("Enter -1 to cancel account creation.");

        // -------------------- Customer Name --------------------
        System.out.print("Enter Customer Name (First, Last Name): ");
        String customerName = scanner.nextLine().trim();
        if (customerName.isEmpty() || customerName.equals("-1")) {
            System.out.println("Customer creation canceled or invalid.");
            return null;
        }

        // -------------------- Customer Address --------------------
        System.out.print("Enter Customer Address: ");
        String customerAddress = scanner.nextLine().trim();
        if (customerAddress.isEmpty() || customerAddress.equals("-1")) {
            System.out.println("Customer creation canceled or invalid.");
            return null;
        }

        // -------------------- Password --------------------
        System.out.println("Password must be at least 6 characters and include a digit, a special character (@,#,$,%,&,*) and an uppercase letter.");
        String customerPassword;
        do {
            System.out.print("Enter Customer Password: ");
            customerPassword = scanner.nextLine().trim();
            if (customerPassword.equals("-1")) return null;
        } while (!Customer.isValidPassword(customerPassword));

        // -------------------- Get or Create Customer --------------------
        Customer existingCustomer = customerOrderSystem.getCustomerByNameAddress(customerName, customerAddress);
        if (existingCustomer == null) {
            existingCustomer = new Customer(customerName, customerAddress);
        }

        ChaseBank chaseBank = ChaseBank.getInstance();
        ChaseBankCustomer bankCustomer = chaseBank.createChaseBankCustomer(existingCustomer, customerPassword);

        // -------------------- Link or Create Credit Card --------------------
        String defaultCardNumber = existingCustomer.getDefaultCreditCardNumber();
        CreditCardLimit cardToUse;

        // Only ask for deposit if the card does not exist globally
        double depositAmount = 0.0;
        if (chaseBank.getCreditCardLimitByNumber(defaultCardNumber) == null) {
            while (true) {
                System.out.print("Enter your initial deposit: ");
                try {
                    depositAmount = Double.parseDouble(scanner.nextLine().trim());
                    if (depositAmount == -1) return null;
                    if (depositAmount <= 0) {
                        System.out.println("Deposit amount must be positive. Try again.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                }
            }
        }

        // Use global-aware create method
        cardToUse = bankCustomer.createCreditCardLimit(depositAmount, defaultCardNumber);

        // -------------------- Display Info --------------------
        System.out.println("\n===== Bank Details =====");
        System.out.println(bankCustomer.toUIString());
        System.out.println("------------------------------------");
        System.out.println("Card Number : " + cardToUse.getCreditCardNumber());
        System.out.printf("Credit Limit: $%,.2f%n", cardToUse.getCreditLimit());
        System.out.println("------------------------------------");

        return bankCustomer;
    }



// ------------------- Utilities -------------------
    /**
     * Create a cart universal method for both Admin/Customer UI
     *
     * @return Cart
     */
    public Cart makeCart() {
        ArrayList<Product> products = customerOrderSystem.getProducts();
        if (products.isEmpty()) {
            System.out.println("There are no products in the system.");
            return null;
        }
        Cart cart = new Cart();

        // ------------------- Display Products -------------------
        System.out.println("\n================ AVAILABLE PRODUCTS ================");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, products.get(i).toUIString());
        }
        System.out.println("====================================================");

        // ------------------- Add Items -------------------
        while (true) {
            System.out.println("\nEnter 0 when you are done adding items.");
            System.out.print("Select a product by number: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
                continue;
            }

            if (choice == 0) break;
            if (choice < 1 || choice > products.size()) {
                System.out.println("⚠️ Invalid choice. Try again.");
                continue;
            }

            Product selected = products.get(choice - 1);
            System.out.println("\n----------------------------------------------------");
            System.out.println("🛒 Selected: " + selected.getProductName());
            double amount;

            if (selected instanceof WeightedProducts) {
                System.out.print("Enter weight (lbs/kg): ");
                double weight;
                try {
                    weight = Double.parseDouble(scanner.nextLine().trim());
                    if (weight <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Invalid input. Enter a positive number.");
                    continue;
                }
                cart.addItem(new CartItem(selected, weight));
            } else { // QuantityProducts
                System.out.print("Enter quantity: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine().trim());
                    if (quantity <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Invalid input. Enter a positive whole number.");
                    continue;
                }
                cart.addItem(new CartItem(selected, quantity));
            }
            System.out.println("✅ Added to cart!");
        }

        // ------------------- Display Summary -------------------
        if (cart.getItems().isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return null;
        }

        System.out.println("\n================ YOUR ORDER SUMMARY ================");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.toUIString());
        }

        double subtotal = cart.calculateSubtotal();
        double total = cart.calculateTotal(0.0825);
        double tax = total - subtotal;

        System.out.printf("%nSubtotal: $%.2f%nTax: $%.2f%nTotal: $%.2f%n", subtotal, tax, total);
        System.out.println("====================================================\n");

        return cart;
    }

    /**
     * Prompt user for the delivery method
     *
     * @param cart the cart
     * @return the string
     */
    public String chooseDeliveryMethod(Cart cart) {
        while (true) {
            System.out.print("1-Mail | 2-In-Store Pickup: ");
            String method = scanner.nextLine().trim();
            if (method.equals("1")) {
                return "Mail";
            }
            if (method.equals("2")) {
                return "In-Store Pickup";
            }
            System.out.println("Invalid input. Please enter a number 1 or 2.");
        }
    }

    /**
     * Get the total price including tax and delivery fees
     *
     * @param cart   the cart
     * @param method the method
     * @return the total
     */
    public double getTotal(Cart cart, String method) {
        // Determine the delivery fee
        // 3 if delivery | 0 if pick up
        int methodFee = switch (method) {
            case "Mail" -> 3;
            case "In-Store Pickup" -> 0;
            default -> 0;
        };

        System.out.println("Delivery Method: " + method);
        double cartTotal = cart.calculateTotal(CustomerOrderSystem.TAX_RATE);
        double totalPrice = cartTotal + methodFee;

        System.out.println("Cart Price: $" + cartTotal);
        System.out.println("Delivery Fee: $" + methodFee);
        System.out.println("Total Price: $" + totalPrice);

        return totalPrice;
    }

    /**
     * Get the security answer and question
     *
     * @return string array [question, answer]
     */
    public String[] getSecurityQuestionAnswer() {
        String customerSecurityQuestion = "";
        String customerSecurityAnswer = "";

        System.out.println("\nSelect a security question:");
        System.out.println("1. What is your favorite color?");
        System.out.println("2. What is your mother’s maiden name?");
        System.out.println("3. What city were you born in?");
        System.out.println("4. What is your favorite food?");
        System.out.print("Enter your choice (1–4): ");

        int questionChoice;
        while (true) {
            try {
                questionChoice = Integer.parseInt(scanner.nextLine().trim());
                switch (questionChoice) {
                    case 1 -> customerSecurityQuestion = "What is your favorite color?";
                    case 2 -> customerSecurityQuestion = "What is your mother’s maiden name?";
                    case 3 -> customerSecurityQuestion = "What city were you born in?";
                    case 4 -> customerSecurityQuestion = "What is your favorite food?";
                    default -> {
                        System.out.print("Invalid choice. Please enter 1–4: ");
                        continue;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number 1–4: ");
            }
        }
        System.out.print("Enter your answer: ");
        customerSecurityAnswer = scanner.nextLine().trim();

        String[] result = new String[2];
        result[0] = customerSecurityQuestion;
        result[1] = customerSecurityAnswer;
        return result;
    }
}