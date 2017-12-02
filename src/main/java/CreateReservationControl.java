import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CreateReservationControl extends GridPane {
	
	private AirlineSQLExecutor executor;
	
	private ListView<Customer> passengers;
	
	private ComboBox<String> passengerClass;
	
	// click this button to pull all flights that have enough seats
	private Button populateFlights;
	
	private ListView<Flight> flights;
	
	private Button submit;
	
	private Label passengerLabel;
	
	private Label flightLabel;
	
	public CreateReservationControl() {
		super();
		
		// create sql executor
		executor = new AirlineSQLExecutor();
		
		// instantiate UI elements
		passengers = new ListView<Customer>();
		passengerClass = new ComboBox<String>();
		populateFlights = new Button("Populate Applicable Flights");
		flights = new ListView<Flight>();
		submit = new Button("Submit Reservation");
		
		passengerLabel = new Label("Select Passengers");
		flightLabel = new Label("Select From Applicable Flights");
		
		
		// populate passengers
		passengers.getItems().addAll(executor.getCustomers());
		
		// populate class dropdown
		passengerClass.getItems().addAll(
				"First Class",
				"Business Class",
				"Family Class",
				"Premium Class",
				"Economy Class"
		);
		
		// handle populate flights press
		populateFlights.setOnAction( e -> {
			flights.getItems().clear();
			flights.getItems().addAll(executor.getAvailableFlights());
		});
		
		
		// arrange UI elements
		this.add(passengers, 1, 0);
		this.add(passengerClass, 1, 1);
		this.add(populateFlights, 1, 2);
		this.add(flights, 1, 3);
		this.add(submit, 1, 4);
	
		this.add(passengerLabel, 2, 0);
		this.add(flightLabel, 2, 3);
	}
	
}