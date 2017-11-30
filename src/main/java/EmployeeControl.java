import java.util.Calendar;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EmployeeControl extends GridPane {

    AirlineSQLExecutor executor;

    //• Get all customers who have seats reserved on a given flight.
    Button getCustomersOnFlightButton;
    TextField flightCustomersTextField;

    //• Get all the details about the crew members on that flight.
    Button getCrewOfFlightButton;
    TextField flightCrewTextField;

    //• Get all flights for a given airport.
    Button getFlightsForAirportButton;
    TextField airportFlightsTextField;

    //• View flight rooster, schedule.
    Button viewFlightRosterButton;

    //• Get all flights whose arrival and departure times are on time/delayed.
    Button getOnTimeFlightsButton;
    Button getDelayedFlightsButton;

    //• Calculate total sales for a given flight.
    Button calcTotalSalesButton;
    TextField flightTotalSalesTextField;

    public EmployeeControl() {
        super();

        executor = new AirlineSQLExecutor();

        //define buttons
        getCustomersOnFlightButton = new Button("Get customers on flight");
        getCrewOfFlightButton = new Button("Get crew of flight");
        getFlightsForAirportButton = new Button("Get flights for airport");
        viewFlightRosterButton = new Button("View flight roster");
        getOnTimeFlightsButton = new Button("Get flights that are on time");
        getDelayedFlightsButton = new Button("Get flights that are delayed");
        calcTotalSalesButton = new Button("Calculate total sales for flight");

        //define text fields
        flightCustomersTextField = new TextField("Flight No.");
        flightCrewTextField = new TextField("Flight No.");
        airportFlightsTextField = new TextField("Airport No.");
        flightTotalSalesTextField = new TextField("Flight No.");


        // position buttons within the gridPane
        this.add(getCustomersOnFlightButton, 1, 0);
        this.add(flightCustomersTextField, 2, 0);
        this.add(getCrewOfFlightButton, 1, 1);
        this.add(flightCrewTextField, 2, 1);
        this.add(getFlightsForAirportButton, 1, 2);
        this.add(airportFlightsTextField, 2, 2);
        this.add(viewFlightRosterButton, 1, 3);
        this.add(getOnTimeFlightsButton, 1, 4);
        this.add(getDelayedFlightsButton, 1, 5);
        this.add(calcTotalSalesButton, 1, 6);
        this.add(flightTotalSalesTextField, 2, 6);
    }

}