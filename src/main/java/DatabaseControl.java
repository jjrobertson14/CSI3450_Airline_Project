import java.util.Calendar;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DatabaseControl extends GridPane {

    AirlineSQLExecutor executor;

    Button populateCustomerButton;

    Button populateAirportButton;
    
    Button populateAircraftButton;

    Button populateFlightButton;

    Button populateEmployeeButton;

    public DatabaseControl() {
        super();

        executor = new AirlineSQLExecutor();

        populateCustomerButton = new Button("Populate Customers");
        populateAirportButton = new Button("Populate Airports");
        populateAircraftButton = new Button("Populate Aircraft");
        populateFlightButton = new Button("Populate Flights");
        populateEmployeeButton = new Button("Populate Employees");

        // insert a group of random customers into the database
        populateCustomerButton.setOnAction(e ->     {
            Customer[] c = Customer.generateRandomCustomers();
            executor.insertCustomer(c);
        });

        // position buttons within the gridPane
        this.add(populateCustomerButton, 1, 0);
        this.add(populateAirportButton, 1, 1);
        this.add(populateAircraftButton, 1, 2);
        this.add(populateFlightButton, 1, 3);
        this.add(populateEmployeeButton, 1, 4);
    }

    private void populateCustomers () {
        System.out.println("Populate customers!");
    }

}