import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents an Order Controller in the Customer Order System (COS).
 * Controls the logic for Order creator in Customer/Admin UI
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class OrderController {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Order controller.
     *
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public OrderController(CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
    }



// ------------------- Utilities -------------------
    /**
     * Create submit order controller order.
     *
     * @param cart           the cart
     * @param customer       the customer
     * @param customerBank   the customer bank
     * @param deliveryMethod the delivery method
     * @return the order
     */
    public Order createSubmitOrderController(Cart cart, Customer customer, CustomerBank customerBank, String deliveryMethod) {
        if (cart == null || customerBank == null || deliveryMethod == null) {
            return null;
        }

        // Get the cos from the cosUIController for the bank and cos for order.submitOrder()
        CustomerOrderSystem customerOrderSystem = customerOrderSystemUserInterfaceController.getCustomerOrderSystem();

        // Creates a date string based on current date
        LocalDate myObj = LocalDate.now();
        String date = myObj.toString();

        // ============ Get total Final Price ============
        double totalPrice = customerOrderSystemUserInterfaceController.getTotal(cart, deliveryMethod);

        // Create order
        Order order = new Order(cart, deliveryMethod, date, totalPrice, customer.getCustomerID());
        // Submit order (controller handles calling the bank/credit methods)
        order.submitOrder(customer, customerBank.getCustomerBankID(), customerBank.getCustomerCreditCardNumber(), customerOrderSystem.getCosBank(), customerOrderSystem);
        return order;
    }

    /**
     * View specific order.
     *
     * @param customer the customer
     */
    public void viewSpecificOrder(Customer customer) {
        CustomerOrderSystem customerOrderSystem = customerOrderSystemUserInterfaceController.getCustomerOrderSystem();

        ArrayList<Order> orderList = customerOrderSystem.getCustomerOrders(customer);
        System.out.println("\n--- All Orders for " + customer.getCustomerID() + " ---");
        for (Order order : orderList) {
            System.out.println(order.toUIString(customer));
        }
    }
}