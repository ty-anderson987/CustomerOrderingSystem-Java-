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

            // ========== Initialize ChaseBank ==========
            ChaseBank chaseBank = ChaseBank.getInstance();
            try {
                chaseBank.loadBankFromFile("chaseBank.txt");
            } catch (IOException e) {
                System.out.println("Could not load ChaseBank data: " + e.getMessage());
            }


            // Creates a new customer order system user interface
            CustomerOrderSystemUserInterface customerOrderSystemUserInterface = new CustomerOrderSystemUserInterface(customerOrderSystem);
            // Load the customer order system user interface
            customerOrderSystemUserInterface.cosMainUI();



            // ========== Save All Data on Exit ==========
            try {
                customerOrderSystem.saveAllData();
                chaseBank.saveBankToFile("chaseBank.txt");
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
