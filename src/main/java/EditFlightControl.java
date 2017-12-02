import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

//TODO: Implement Flight, Employee types with applicable UI elements

/**
 * This view allows the user to edit a particular flight
 * @author Noah
 *
 */
public class EditFlightControl extends GridPane {
	
	private ComboBox<String> flight;
	
	private TextField departure;
	
	private TextField arrival;
	
	private ComboBox<String> pilot;
	
	private ListView<String> crew;
	
	private TextField baseFare;
	
	private Button submit;
	
	private Label flightLabel;
	
	private Label departureLabel;
	
	private Label arrivalLabel;
	
	private Label pilotLabel;
	
	private Label crewLabel;
	
	private Label baseFareLabel;

	public EditFlightControl() {
		super();
		
		// Load flights here
		ObservableList<String> flights = FXCollections.observableArrayList(
				"Flight 1: Los Angeles -> Detroit",
				"Flight 2: New York -> London",
				"Flight 3: San Jose -> Mexico City"
		);
		flight = new ComboBox<String>(flights);
		
		// departure and arrival
		departure = new TextField();
		arrival = new TextField();
		
		departure.setText("2017-12-10 8:00");
		arrival.setText("2017-12-10 12:00");
		
		// Load employees here
		ObservableList<String> employees = FXCollections.observableArrayList(
				"Pilot Bob",
				"Attendant John",
				"Attendant Jane",
				"Pilot Francine",
				"Attendant Jose"
		);
		
		pilot = new ComboBox<String>(employees);
		crew = new ListView<String>(employees);
		crew.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// Base fare
		baseFare = new TextField();
		baseFare.setText("100.00");
		
		// Submit the flight
		submit = new Button("Submit");
		
		// Create labels
		flightLabel = new Label("Select Flight");
		departureLabel = new Label("Departure Time");
		arrivalLabel = new Label("Arrival Time");
		pilotLabel = new Label("Select Pilot");
		crewLabel = new Label("Select Crew Members");
		baseFareLabel = new Label("$ Base Price");
		
		// arrange UI elements
		this.add(flight, 1, 0);
		this.add(departure, 1, 1);
		this.add(arrival, 1, 2);
		this.add(pilot, 1, 3);
		this.add(crew, 1, 4);
		this.add(baseFare, 1, 5);
		this.add(submit, 1, 6);
		
		this.add(flightLabel, 2, 0);
		this.add(departureLabel, 2, 1);
		this.add(arrivalLabel, 2, 2);
		this.add(pilotLabel, 2, 3);
		this.add(crewLabel, 2, 4);
		this.add(baseFareLabel, 2, 5);
	}
}
