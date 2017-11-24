import java.util.Calendar;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DatabaseControl extends GridPane {

    AirlineSQLExecutor executor;

    Button populateCustomerButton;

    Button populateFlightButton;

    Button populateAirportButton;

    public DatabaseControl() {
        super();

        executor = new AirlineSQLExecutor();

        populateCustomerButton = new Button("Populate Customers");
        populateFlightButton = new Button("Populate Flights");
        populateAirportButton = new Button("Populate Airports");

        // insert a group of random customers into the database
        populateCustomerButton.setOnAction(e ->{
            Customer[] c = Customer.generateRandomCustomers();
            executor.insertCustomer(c);
        });

        this.add(populateCustomerButton, 1, 0);
        this.add(populateFlightButton, 1, 1);
        this.add(populateAirportButton, 1, 2);
    }

    private void populateCustomers () {
        System.out.println("Populate customers!");
    }

}