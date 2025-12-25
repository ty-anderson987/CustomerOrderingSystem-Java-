import java.util.Random;

/**
 * Represents an Order in the Customer Order System (COS).
 * Stores cart, delivery method, order date, order total, and authorization code
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class Order {
    private String orderID;
    private Cart orderCart;
    private String orderDeliveryMethod;
    private String orderDate;
    private double orderTotal;
    private final String customerID;
    private String authorizationCode;

// ------------------- Constructors -------------------
    /**
     * Creates an Order object
     * Make sure the input is good
     *
     * @param orderCart           the order cart
     * @param orderDeliveryMethod the order delivery method
     * @param orderDate           the order date
     * @param orderTotal          the order total
     * @param customerID          the customer id
     */
    public Order(Cart orderCart, String orderDeliveryMethod, String orderDate, double orderTotal, String customerID) {
        if (orderCart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (orderDeliveryMethod == null || orderDeliveryMethod.isEmpty()) {
            throw new IllegalArgumentException("Delivery method cannot be null or empty");
        }
        if (orderTotal < 0) {
            throw new IllegalArgumentException("Order total cannot be negative");
        }
        this.orderID = generateRandmOrderID();
        this.orderCart = orderCart;
        this.orderDeliveryMethod = orderDeliveryMethod;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.customerID = customerID;
        this.authorizationCode = null;

        // Add shipping fee if Mail
        setOrderDeliveryMethod(orderDeliveryMethod);
    }

    /**
     * Instantiates a new Order.
     *
     * @param orderID             the order id
     * @param orderCart           the order cart
     * @param orderDeliveryMethod the order delivery method
     * @param orderDate           the order date
     * @param authorizationCode   the authorization code
     * @param orderTotal          the order total
     * @param customerID          the customer id
     */
    public Order(String orderID, Cart orderCart, String orderDeliveryMethod, String orderDate, String authorizationCode, double orderTotal, String customerID) {
        if (orderCart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (orderDeliveryMethod == null || orderDeliveryMethod.isEmpty()) {
            throw new IllegalArgumentException("Delivery method cannot be null or empty");
        }
        if (orderTotal < 0) {
            throw new IllegalArgumentException("Order total cannot be negative");
        }
        this.orderID = orderID;
        this.orderCart = orderCart;
        this.orderDeliveryMethod = orderDeliveryMethod;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.customerID = customerID;
        this.authorizationCode = authorizationCode;

        // Add shipping fee if Mail
        setOrderDeliveryMethod(orderDeliveryMethod);
    }



// ------------------- Getters -------------------
    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Gets order cart.
     *
     * @return the order cart
     */
    public Cart getOrderCart() {
        return orderCart;
    }

    /**
     * Gets order delivery method.
     *
     * @return the order delivery method
     */
    public String getOrderDeliveryMethod() {
        return orderDeliveryMethod;
    }

    /**
     * Gets order date.
     *
     * @return the order date
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Gets order total.
     *
     * @return the order total
     */
    public double getOrderTotal() {
        return orderTotal;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Gets authorization code.
     *
     * @return the authorization code
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }



// ------------------- Setters -------------------
    /**
     * Sets order total.
     *
     * @param orderTotal the order total
     */
    public void setOrderTotal(double orderTotal) {
        if (orderTotal < 0) {
            throw new IllegalArgumentException("Order total cannot be negative");
        }
        this.orderTotal = orderTotal;
    }

    /**
     * Sets order date.
     *
     * @param orderDate the order date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Sets order delivery method.
     *
     * @param orderDeliveryMethod the order delivery method
     */
    public void setOrderDeliveryMethod(String orderDeliveryMethod) {
        if (orderDeliveryMethod == null || orderDeliveryMethod.isEmpty()) {
            throw new IllegalArgumentException("Order delivery method cannot be null or empty");
        }
        if (!orderDeliveryMethod.equals("Mail") && !orderDeliveryMethod.equals("In-Store Pickup")) {
            throw new IllegalArgumentException("Order delivery method must be Mail or In-Store Pickup");
        }
        this.orderDeliveryMethod = orderDeliveryMethod;
    }

    /**
     * Sets order cart.
     *
     * @param orderCart the order cart
     */
    public void setOrderCart(Cart orderCart) {
        this.orderCart = orderCart;
    }



// ------------------- Order Utilities -------------------
    /**
     * Submit order from order object with bank info and customer info.
     * Checks all the parameters match before sending it to the order arrayList
     *
     * @param authorizationCode bank authorization code
     * @param customerOrderSystem the customer order system
     */
    public void submitOrder(String authorizationCode, CustomerOrderSystem customerOrderSystem) {
        if (authorizationCode == null || customerOrderSystem == null) {
            System.out.println("Order submission failed. Invalid data");
            return;
        }

        // Generate a unique orderID
        do {
            this.orderID = generateRandmOrderID();
        } while (doesOrderIDExist(customerOrderSystem));

        // Record order
        this.authorizationCode = authorizationCode;
        customerOrderSystem.addOrder(this);
        System.out.println("\nOrder submitted successfully!");
        System.out.println("Order ID: " + this.orderID);
        System.out.println("Authorization Code: " + this.authorizationCode);
    }



// ------------------- Utilities -------------------
    /**
     * Generate randm order id string.
     *
     * @return the string
     */
    public String generateRandmOrderID() {
        Random random = new Random();
        String randomBankID = "";
        for (int i = 0; i < 10; i++) {
            randomBankID += random.nextInt(10);
        }
        return randomBankID;
    }

    /**
     * Does order id exist boolean.
     *
     * @param customerOrderSystem the customer order system
     * @return the boolean
     */
    public boolean doesOrderIDExist(CustomerOrderSystem customerOrderSystem) {
        for (Order Order : customerOrderSystem.getOrders()) {
            if(Order.getOrderID().equals(this.orderID)) {
                return true;
            }
        }
        return false;
    }



// ------------------- toString() -------------------
    /**
     * To file string.
     *
     * @return the string
     */
    public String toFileString() {
        String fileString = orderID + "," + customerID + "," + orderDate + "," + orderTotal + "," +
                orderDeliveryMethod;
        if (authorizationCode != null) {
            fileString += "," + authorizationCode;
        }
        if (orderCart != null && !orderCart.getItems().isEmpty()) {
            fileString += "," + orderCart.toFileString();
        }
        return fileString;
    }

    /**
     * To ui string string.
     *
     * @param customer the customer
     * @return the string
     */
    public String toUIString(Customer customer) {
        String uiString = "";
        uiString += String.format(
                "Order ID: %-12s | Customer ID: %-6s | Customer Name: %-20s | Date: %-12s | Total: $%-10.2f | Delivery: %-6s | Authorization Code: %-6s",
                orderID,
                customerID,
                customer.getCustomerName(),
                orderDate,
                orderTotal,
                orderDeliveryMethod,
                authorizationCode
        );

        uiString += "\n   Products:\n";
        for (CartItem cartItems : orderCart.getItems()) {
            uiString += "      • " + (cartItems.toUIString()) + "\n";
        }
        return uiString;
    }
}