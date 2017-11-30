import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;

//TODO: pilot and crew should eventually be of type X<Employee> instead of X<String>

/**
 * This view is responsible for creating a new flight
 * @author Noah
 *
 */
public class AddFlightControl extends GridPane {
	
	private ComboBox<Aircraft> aircraft;
		
	private ComboBox<Airport> origin;
	
	private ComboBox<Airport> destination;
	
	private TextField departure;
	
	private TextField arrival;
	
	private ComboBox<String> pilot;
	
	private ListView<String> crew;
	
	private TextField baseFare;
	
	private Button submit;
	
	private Label aircraftLabel;
	
	private Label originLabel;
	
	private Label destinationLabel;
	
	private Label departureLabel;
	
	private Label arrivalLabel;
	
	private Label pilotLabel;
	
	private Label crewLabel;
	
	private Label baseFareLabel;

	public AddFlightControl() {
		
		// Load aircraft here
		Aircraft a1 = new Aircraft();
		a1.setAircraftID(7);
		a1.setBusinessSeats(50);
		
		ObservableList<Aircraft> aircraftList = FXCollections.observableArrayList(a1);
		
		aircraft = new ComboBox<Aircraft>(aircraftList);
		
		// Load airports here
		ObservableList<Airport> airportList = FXCollections.observableArrayList();
		
		origin = new ComboBox<Airport>(airportList);
		destination = new ComboBox<Airport>(airportList);
		
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
		aircraftLabel = new Label("Select Aircraft");
		originLabel = new Label("Select Origin");
		destinationLabel = new Label("Select Destination");
		departureLabel = new Label("Departure Time");
		arrivalLabel = new Label("Arrival Time");
		pilotLabel = new Label("Select Pilot");
		crewLabel = new Label("Select Crew Members");
		baseFareLabel = new Label("$ Base Price");
		
		
		// arrange UI elements
		this.add(aircraft, 1, 0);
		this.add(origin, 1, 1);
		this.add(destination, 1, 2);
		this.add(departure, 1, 3);
		this.add(arrival, 1, 4);
		this.add(pilot, 1, 5);
		this.add(crew, 1, 6);
		this.add(baseFare, 1, 7);
		this.add(submit, 1, 8);
		
		this.add(aircraftLabel, 2, 0);
		this.add(originLabel, 2, 1);
		this.add(destinationLabel, 2, 2);
		this.add(departureLabel, 2, 3);
		this.add(arrivalLabel, 2, 4);
		this.add(pilotLabel, 2, 5);
		this.add(crewLabel, 2, 6);
		this.add(baseFareLabel, 2, 7);
	}
}