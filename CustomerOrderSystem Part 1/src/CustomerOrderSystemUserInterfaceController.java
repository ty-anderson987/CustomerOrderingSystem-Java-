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
    private final Bank cosBank;
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
     * @param cosBank             the cos bank
     * @param scanner             the scanner
     */
    public CustomerOrderSystemUserInterfaceController(CustomerOrderSystem customerOrderSystem, Bank cosBank, Scanner scanner) {
        this.customerOrderSystem = customerOrderSystem;
        this.cosBank = cosBank;
        this.scanner = scanner;
        this.customerController = new CustomerController(this);
        this.orderController = new OrderController(this);
        this.adminController = new AdminController(customerOrderSystem, this, cosBank, scanner);
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

    /**
     * Gets cos bank.
     *
     * @return the cos bank
     */
    public Bank getCosBank() {
        return cosBank;
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
        while (attempts < 3) {
            // Find customerID
            System.out.println("Enter -1 to exit");
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
            System.out.println("Enter -1 to exit");
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
        System.out.println("Enter -1 to exit");
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
            do {
                idExists = false;
                System.out.println("Enter -1 to exit");
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
                System.out.println("Enter -1 to exit");
                System.out.print("Enter Customer Password: ");
                customerPassword = scanner.nextLine().trim();
                if (customerPassword.equals("-1")) {
                    return null;
                }
            } while (!Customer.isValidPassword(customerPassword));


            // Step 6 - Get customer information
            do {
                System.out.print("Enter Customer Name: ");
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
            Customer newCustomer = new Customer(customerID, customerName, customerAddress, customerPassword, customerSecurityQuestion, customerSecurityAnswer);
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



// ------------------- Utilities -------------------
    /**
     * Create customer bank controller customer bank.
     *
     * @param customer the customer
     */
    public void createCustomerBankController(Customer customer, String creditCardNumber) {
        if (customerOrderSystem.getCosBankCustomerBank(customer) != null) {
            System.out.println("You already have a  customer bank account.");
        }
        try {
            System.out.print("Enter your deposit: ");
            double depositAmount = Double.parseDouble(scanner.nextLine());
            if (depositAmount <= 0) {
                System.out.println("Invalid deposit amount. Please enter a positive number.");
            }

            customerOrderSystem.addCustomerBank (customer,creditCardNumber, depositAmount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number greater than 0.");
        }
    }

    /**
     * Create a cart universal method for both Admin/Customer UI
     *
     * @return Cart
     */
    public Cart makeCart() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> products = customerOrderSystem.getProducts();
        Cart cart = new Cart();
        Customer customer = null;

        // ------------------- Make Cart-------------------
        int size = products.size();
        for (int i = 1; i < size + 1; i++) {
            System.out.println(i + ":" + products.get(i - 1).toUIString());
        }

        // Make sure user enters a number valid
        // Adds the selected product to the cart
        try {
            while (true) {
                System.out.println();
                int choice;
                do {
                    System.out.println("Enter the number 0 to complete.");
                    System.out.print("Enter the number corresponding to the product you want to buy: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice == 0) {
                        break;
                    }
                } while (!(choice >= 1 && choice <= size));

                // If user chose 0 break
                if (choice == 0) {
                    break;
                }
                choice--;

                // Different questions for weighted/quantity products
                Product product = products.get(choice);
                System.out.println();
                System.out.println("===================================================");
                if (products.get(choice) instanceof WeightedProducts) {
                    System.out.println();
                    System.out.println("Weighted Product Selected: " + product.getProductName());
                    System.out.print("Please enter the weight for the weighted product you want to buy: ");
                    double weight = Double.parseDouble(scanner.nextLine());
                    if (weight <= 0) {
                        System.out.println("Invalid input. Enter a number greater than 0.");
                        continue;
                    }
                    CartItem cartItem = new CartItem(product, weight);
                    cart.addItem(cartItem);
                } else {
                    System.out.println();
                    System.out.println("Quantity Product Selected: " + product.getProductName());
                    System.out.print("Please enter the quantity for the quantity product you want to buy: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity <= 0) {
                        System.out.println("Invalid input. Enter number greater than 0.");
                        continue;
                    }
                    CartItem cartItem = new CartItem(product, quantity);
                    cart.addItem(cartItem);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 0 and " + size + ".");
        }
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return null;
        }

        System.out.println("Here is your order");
        for (CartItem cartItem : cart.getItems()) {
            System.out.println(cartItem.toString());
        }
        System.out.println();
        double total = cart.calculateTotal(0.0825);
        double subtotal = cart.calculateSubtotal();
        double tax = total - subtotal;
        System.out.println("Total price before Tax: $" + subtotal);
        System.out.println("Tax: $" + tax);
        System.out.println("Total price after Tax: $" + total);
        System.out.println();

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
        String[] result = new String[2];
        result[0] = customerSecurityQuestion;
        result[1] = customerSecurityAnswer;
        return result;
    }
}