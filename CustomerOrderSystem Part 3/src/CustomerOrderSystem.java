import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * Represents the Customer Order System (COS).
 *
 * @author Ty Anderson R11885063
 * @version 2.1
 */
public class CustomerOrderSystem {
    private final ArrayList<Customer> customers;
    private final ArrayList<Order> orders;
    private final ArrayList<Product> products;
    /**
     * The constant TAX_RATE.
     */
    public static final double TAX_RATE = 0.0825;

// ------------------- Constructors -------------------
    /**
     * Creates a customerOrderSystem Object to be used to call different methods inside Main
     */
    public CustomerOrderSystem() {
        customers = new ArrayList<>();
        orders = new ArrayList<>();
        products = new ArrayList<>();
    }



// ------------------- Getters -------------------
    /**
     * Gets customers.
     *
     * @return the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Gets customer orders.
     *
     * @param customer the customer
     * @return the customer orders
     */
    public ArrayList<Order> getCustomerOrders(Customer customer) {
        ArrayList<Order> newOrder = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomerID().equals(customer.getCustomerID())) {
                newOrder.add(order);
            }
        }
        return newOrder;
    }

    /**
     * Gets products.
     *
     * @return the products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    public Customer getCustomerByNameAddress(String customerName, String customerAddress) {
        for (Customer customer : customers) {
            if (customer.getCustomerName().trim().equalsIgnoreCase(customerName.trim()) &&
                    customer.getCustomerAddress().trim().equalsIgnoreCase(customerAddress.trim())) {
                return customer;
            }

        }
        return null;
    }



// ------------------- Adders -------------------
    /**
     * Add product to the product ArrayList
     *
     * @param product the product
     */
    public void addProduct(Product product) {
        // Finds product in the list
        for (Product p : products) {
            if (p.equalsProduct(product)) {
                System.out.println("product already exists");
                return;
            }
        }
        products.add(product);
    }

    /**
     * Add customer to the Customer ArrayList
     *
     * @param customer the customer
     */
    public void addCustomer(Customer customer) {
        // Check if customer already exists
        for (Customer c : customers) {
            if (c.equalsCustomer(customer)) {
                return;
            }
        }
        customers.add(customer);
    }

    /**
     * Add order.
     *
     * @param order the order
     */
    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
        }
    }



// ------------------- File Save -------------------
    // Format: customerID, customerName, customerAddress, customerPassword, customerQuestion, customerAnswer, default credit card number
    /**
     * Load customer objects from the customer file
     * Keeps track of customers when program closes
     *
     * @param fileName the file name
     */
    public void loadCustomersFromFile(String fileName) {
            try {
                File file = new File(fileName);
                Scanner reader = new Scanner(file);

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    // Splits string into parts seperated by '|' and puts the parts in a String array
                    String[] parts = line.split("\\|");

                    // Found Format: customerID, customerName, customerAddress, customerPassword, customerQuestion, customerAnswer, defaultCreditCardNumber
                    if (parts.length == 7) {
                        String customerID = parts[0].trim();
                        String customerName = parts[1].trim();
                        String customerAddress = parts[2].trim();
                        String customerPassword = parts[3].trim();
                        String customerSecurityQuestion = parts[4].trim();
                        String customerSecurityAnswer =  parts[5].trim();
                        String cardNumber = parts[6].trim();

                        // Creates a customer object from reading the file
                        Customer customerRead = new Customer(customerID, customerName, customerAddress, customerPassword, customerSecurityQuestion, customerSecurityAnswer, cardNumber);
                        customers.add(customerRead);
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }

    /**
     * Save customer objects from customer arraylist to customer file
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void saveCustomersToFile(String fileName) throws IOException {
            try {
                FileWriter fw = new FileWriter(fileName);
                PrintWriter writer = new PrintWriter(fw);

                // Saves all order to order file
                for (Customer customer : customers) {
                    writer.println(customer.toFileString());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }

    // Format: productName, productDescription, regularPrice, salePrice
    /**
     * Load product objects from the product file
     * Keeps track of products after program closes
     *
     * @param fileName the file name
     */
    public void loadProductsFromFile(String fileName) {
            try {
                File file = new File(fileName);
                Scanner reader = new Scanner(file);

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    // Splits the string into parts and saves to string array
                    String[] parts = line.split("\\|");

                    // Product: productName, productDesc, regularPrice, salePrice
                    if (parts.length >= 4) {
                        String productName = parts[0].trim();
                        String productDesc = parts[1].trim();
                        double regularPrice = Double.parseDouble(parts[2].trim());
                        double salePrice = Double.parseDouble(parts[3].trim());

                        // WeightProduct Object Format:productName, productDesc, regularPrice, salePrice, weight
                        // QuantityProduct Object Format: productName, productDesc, regularPrice, salePrice, quantity
                        Product product;
                        if (parts.length == 4) {
                            // Product Object Format: productName, productDesc, regularPrice, salePrice, cartItemQuantity
                            product = new QuantityProducts(productName, productDesc, regularPrice, salePrice, 1);
                            products.add(product);
                        } else if (parts.length == 5) {
                            try {
                                // QuantityProduct Object Format: productName, productDesc, regularPrice, salePrice, quantity
                                int quantity = Integer.parseInt(parts[4].trim());
                                product = new QuantityProducts(productName, productDesc, regularPrice, salePrice, quantity);
                                products.add(product);
                            } catch (NumberFormatException e) {
                                // WeightProduct Object Format:productName, productDesc, regularPrice, salePrice, weight
                                double weight = Double.parseDouble(parts[4].trim());
                                product = new WeightedProducts(productName, productDesc, regularPrice, salePrice, weight);
                                products.add(product);
                            }
                        }
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (Exception e) {
                System.out.println("Error loading file" + e.getMessage());
            }
        }

    /**
     * Saves product objects to product file
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void saveProductsToFile(String fileName) throws IOException {
            try {
                FileWriter fw = new FileWriter(fileName);
                PrintWriter writer = new PrintWriter(fw);

                // Saves all order to order file
                for (Product product : products) {
                    writer.println(product.toFileString());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }

    // Format: orderID, customerID, date, total, authenticationCode, cart() object
    /**
     * Load order objects from the order file
     * Keep track of order information after program closes
     *
     * @param fileName the file name
     */
    public void loadOrdersFromFile(String fileName) {
            try {
                File file = new File(fileName);
                Scanner reader = new Scanner(file);

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    // Split the first part of the order string and the cart string
                    String[] parts = line.split(",", 7);

                    // Format: orderID, customerID, date, total, authenticationCode
                    String orderID = parts[0].trim();
                    String customerID = parts[1].trim();
                    String orderDate = parts[2].trim();
                    double orderTotal = Double.parseDouble(parts[3].trim());
                    String orderDeliveryMethod = parts[4].trim();
                    String authorizationCode = parts[5].trim();
                    // parts[6] = cart() object
                    String cartPart = parts.length > 6 ? parts[6] : "";

                    Cart cart = new Cart();
                    if (!cartPart.isEmpty()) {
                        // Splits the cart with items by || symbol
                        String[] cartItems = cartPart.split("\\|\\|");
                        // Go through the entire cart() object string for all items
                        for (String itemStr : cartItems) {
                            if (itemStr.isEmpty()) continue;

                            // Creates a string for items fields
                            String[] itemFields = itemStr.split("\\|");
                            if (itemFields.length >= 4) {
                                String productName = itemFields[0].trim();
                                String productDesc = itemFields[1].trim();
                                double regularPrice = Double.parseDouble(itemFields[2].trim());
                                double salePrice = Double.parseDouble(itemFields[3].trim());

                                // Product Object Format: productName, productDesc, regularPrice, salePrice, cartItemQuantity
                                // WeightProduct Object Format:productName, productDesc, regularPrice, salePrice, weight, cartItemWeight
                                // QuantityProduct Object Format: productName, productDesc, regularPrice, salePrice, quantity, cartItemQuantity
                                Product product;
                                if (itemFields.length == 5) {
                                    // Product Object Format: productName, productDesc, regularPrice, salePrice, cartItemQuantity
                                    int quantity = Integer.parseInt(itemFields[4].trim());
                                    product = new QuantityProducts(productName, productDesc, regularPrice, salePrice, 1);
                                    cart.addItem(new CartItem(product, quantity));
                                } else if (itemFields.length == 6) {
                                    try {
                                        // QuantityProduct Object Format: productName, productDesc, regularPrice, salePrice, quantity, cartItemQuantity
                                        int quantity = Integer.parseInt(itemFields[4].trim());
                                        int cartQuantity = Integer.parseInt(itemFields[5].trim());
                                        product = new QuantityProducts(productName, productDesc, regularPrice, salePrice, quantity);
                                        cart.addItem(new CartItem(product, cartQuantity));
                                    } catch (NumberFormatException e) {
                                        // WeightProduct Object Format:productName, productDesc, regularPrice, salePrice, weight, cartItemWeight
                                        double weight = Double.parseDouble(itemFields[4].trim());
                                        double weightQuantity = Double.parseDouble(itemFields[5].trim());
                                        product = new WeightedProducts(productName, productDesc, regularPrice, salePrice, weight);
                                        cart.addItem(new CartItem(product, weightQuantity));
                                    }
                                }
                            }
                        }
                    }
                    // Creates a new Order object using the cart object and order fields
                    Order order = new Order(orderID, cart, orderDeliveryMethod, orderDate, authorizationCode, orderTotal, customerID);
                    orders.add(order);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }

    /**
     * Saves order objects to order file
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void saveOrdersToFile(String fileName) throws IOException {
            try {
                FileWriter fw = new FileWriter(fileName);
                PrintWriter writer = new PrintWriter(fw);

                // Saves all order to order file
                for (Order order : orders) {
                    writer.println(order.toFileString());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }



// ------------------- Utilities -------------------
    /**
     * The enum Log code.
     */
    public enum LogCode {
        /**
         * Success log code.
         */
        SUCCESS,
        /**
         * Declined log code.
         */
        DECLINED,
        /**
         * Customer not found log code.
         */
        CUSTOMER_NOT_FOUND,
        /**
         * Order not found log code.
         */
        ORDER_NOT_FOUND,
        /**
         * Product not found log code.
         */
        PRODUCT_NOT_FOUND,
        /**
         * System error log code.
         */
        SYSTEM_ERROR
    }



// ------------------- File Save -------------------
    /**
     * Save all data.
     *
     * @throws IOException the io exception
     */
    public void saveAllData() throws IOException {
        saveCustomersToFile("customers.txt");
        saveProductsToFile("products.txt");
        saveOrdersToFile("orders.txt");
        System.out.println("All data saved successfully");
    }

    /**
     * Load all data.
     *
     * @throws IOException the io exception
     */
    public void loadAllData() throws IOException {
        loadCustomersFromFile("customers.txt");
        loadProductsFromFile("products.txt");
        loadOrdersFromFile("orders.txt");
        System.out.println("All data loaded successfully");
    }
}