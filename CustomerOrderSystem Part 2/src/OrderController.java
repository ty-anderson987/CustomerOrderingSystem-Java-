import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

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
     * @param chaseBankCustomer the chaseBankCustomer account
     * @param deliveryMethod the delivery method
     * @return the order
     */
    public Order createSubmitOrderController(Cart cart, Customer customer, ChaseBankCustomer chaseBankCustomer, String deliveryMethod) {
        if (cart == null || chaseBankCustomer == null || deliveryMethod == null) {
            return null;
        }

        ChaseBank chaseBank = ChaseBank.getInstance();
        CustomerOrderSystem customerOrderSystem = customerOrderSystemUserInterfaceController.getCustomerOrderSystem();

        // ============ Get total Final Price ============
        double totalPrice = customerOrderSystemUserInterfaceController.getTotal(cart, deliveryMethod);

        // Creates a date string based on current date
        LocalDate myObj = LocalDate.now();
        String date = myObj.toString();

        // Create order
        Order order = new Order(cart, deliveryMethod, date, totalPrice, customer.getCustomerID());

        // ============ Try Default Credit Card ============
        String creditCardNumber = customer.getDefaultCreditCardNumber();
        String authCode = chaseBank.processTransaction(customer, creditCardNumber, totalPrice);
        if (authCode == null) {
            System.out.println("Default card declined");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.print("Enter a new credit card number or '0' to cancel: ");
                String newCard = scanner.nextLine().trim();

                if (newCard.equals("0")) {
                    System.out.println("Order cancelled.");
                    return null;
                }

                authCode = chaseBank.processTransaction(customer, newCard, totalPrice);
                if (authCode != null) {
                    creditCardNumber = newCard;
                    break;
                } else {
                    System.out.println("❌ Transaction declined again. Try a different card.");
                }
            }
        }

        // ============ Finalize Order ============
        order.submitOrder(authCode, customerOrderSystem);
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