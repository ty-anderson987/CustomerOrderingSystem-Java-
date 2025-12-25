import java.io.IOException;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            // Create a Customer order system object
            CustomerOrderSystem customerOrderSystem = new CustomerOrderSystem();
            // --- Load ALL DATA from file if exists ---
            try {
                customerOrderSystem.loadAllData();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            // Create a new bank object
            Bank bank = customerOrderSystem.getCosBank();
            // Send customer Order System to the bank object
            bank.setCustomerOrderSystem(customerOrderSystem);
            // Creates a new customer order system user interface
            CustomerOrderSystemUserInterface customerOrderSystemUserInterface = new CustomerOrderSystemUserInterface(customerOrderSystem, bank);
            // Load the customer order system user interface
            customerOrderSystemUserInterface.cosMainUI();



            // --- Save all DATA back to file ---
            customerOrderSystem.saveAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
