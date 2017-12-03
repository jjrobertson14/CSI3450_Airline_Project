import java.util.Calendar;

import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * This view presents some 'quick-n-dirty' database testing options
 * Name: DatabaseControl.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
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
        populateCustomerButton.setOnAction( e -> {
            Customer[] c = Customer.generateRandomCustomers();
            executor.insertCustomer(c);
        });

        populateAirportButton.setOnAction( e -> {
            Airport detroit = new Airport();
            detroit.setName("Detroit Metroplitan Airport");
            detroit.setLatitude(42.22);
            detroit.setLongitude(83.36);

            Airport chicago = new Airport();
            chicago.setName("O'Hare International Airport");
            chicago.setLatitude(41.97);
            chicago.setLongitude(87.91);

            Airport dallas = new Airport();
            dallas.setName("Fort Worth International Airport");
            dallas.setLatitude(32.90);
            dallas.setLongitude(97.04);

            Airport london = new Airport();
            london.setName("Heathrow Airport");
            london.setLatitude(51.47);
            london.setLongitude(0.45);

            executor.insertAirport(detroit, chicago, dallas, london);
        });

        populateAircraftButton.setOnAction( e -> {
            Aircraft[] a = Aircraft.generateRandomAircraft();
            executor.insertAircraft(a);
        });

        // position buttons within the gridPane
        this.add(populateCustomerButton, 1, 0);
        this.add(populateAirportButton, 1, 1);
        this.add(populateAircraftButton, 1, 2);
        this.add(populateFlightButton, 1, 3);
        this.add(populateEmployeeButton, 1, 4);
    }

}