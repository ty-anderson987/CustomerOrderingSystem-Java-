import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class COSGui extends Application {
    private CustomerOrderSystem customerOrderSystem;
    private ChaseBank chaseBank;


    @Override
    public void start(Stage primaryStage) {
        try {
            // ------------ Load all data  ------------
            customerOrderSystem = new CustomerOrderSystem();
            customerOrderSystem.loadAllData();

            chaseBank = ChaseBank.getInstance();
            chaseBank.loadBankFromFile("chaseBank.txt");


            // ------------ Build GUI  ------------
            CustomerOrderSystemGUI mainGUI = new CustomerOrderSystemGUI(customerOrderSystem);
            mainGUI.start(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}