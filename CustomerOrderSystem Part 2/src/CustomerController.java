/**
 * Represents a Customer Controller in the Customer Order System (COS).
 * Controls the logic for customer UI
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CustomerController {
    private final CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController;

// ------------------- Constructors -------------------
    /**
     * Instantiates a new Customer controller.
     *
     * @param customerOrderSystemUserInterfaceController the customer order system user interface controller
     */
    public CustomerController(CustomerOrderSystemUserInterfaceController customerOrderSystemUserInterfaceController) {
        this.customerOrderSystemUserInterfaceController = customerOrderSystemUserInterfaceController;
    }



// ------------------- Utilities -------------------
    /**
     * Create order controller order.
     *
     * @param cart         the cart
     * @param customer     the customer
     * @param chaseBankCustomer the customer bank
     * @return the order
     */
    public Order createOrderController(Cart cart, Customer customer, ChaseBankCustomer chaseBankCustomer) {
        if (cart == null) {
            return null;
        }

        // ============ Get delivery Option ============
        String deliveryMethod = customerOrderSystemUserInterfaceController.chooseDeliveryMethod(cart);
        if (deliveryMethod == null) {
            return null;
        }

        return customerOrderSystemUserInterfaceController.getOrderController().createSubmitOrderController(cart, customer, chaseBankCustomer, deliveryMethod);
    }

    /**
     * View customer order controller.
     *
     * @param customer the customer
     */
    public void viewCustomerOrderController(Customer customer) {
        customerOrderSystemUserInterfaceController.getOrderController().viewSpecificOrder(customer);
    }
}