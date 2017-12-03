import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * This view is responsible for creating a new flight
 * @author Noah
 *
 */
public class AddFlightControl extends GridPane {
	
	AirlineSQLExecutor executor;
	
	private ComboBox<Aircraft> aircraft;
		
	private ComboBox<Airport> origin;
	
	private ComboBox<Airport> destination;
	
	private TextField departure;
	
	private TextField arrival;
	
	private ComboBox<Employee> pilot;
	
	private ListView<Employee> crew;
	
	private TextField firstClassPrice;
	
	private TextField businessClassPrice;
	
	private TextField familyClassPrice;
	
	private TextField premiumClassPrice;
	
	private TextField econClassPrice;
	
	private Button submit;
	
	private Label aircraftLabel;
	
	private Label originLabel;
	
	private Label destinationLabel;
	
	private Label departureLabel;
	
	private Label arrivalLabel;
	
	private Label pilotLabel;
	
	private Label crewLabel;
	
	private Label firstPriceLabel;
	
	private Label businessPriceLabel;
	
	private Label familyPriceLabel;
	
	private Label premiumPriceLabel;
	
	private Label econPriceLabel;

	public AddFlightControl() {
		
		// setup SQL executor
		executor = new AirlineSQLExecutor();
		
		// Load aircraft here		
		aircraft = new ComboBox<Aircraft>();
		aircraft.getItems().addAll(executor.getAircraft());
		
		// Load airports here		
		origin = new ComboBox<Airport>();
		destination = new ComboBox<Airport>();
		
		origin.getItems().addAll(executor.getAirports());
		destination.getItems().addAll(executor.getAirports());
		
		// departure and arrival
		departure = new TextField();
		arrival = new TextField();
		
		departure.setText("2017-12-10 8:00");
		arrival.setText("2017-12-10 12:00");
		
		// Setup employee selection
		pilot = new ComboBox<Employee>();
		crew = new ListView<Employee>();
		crew.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// fare
		firstClassPrice=new TextField("200.00");
		businessClassPrice=new TextField("175.00");
		familyClassPrice=new TextField("150.00");
		premiumClassPrice=new TextField("125.00");
		econClassPrice = new TextField("100.00");
		
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
		firstPriceLabel = new Label("$ First Class");
		businessPriceLabel = new Label("$ Business Class");
		familyPriceLabel = new Label("$ Family Class");
		premiumPriceLabel = new Label("$ Premium Class");
		econPriceLabel = new Label("$ Base Price");
		
		// handle changing the flight origin
		origin.setOnAction( e -> {
			
			//clear both the employee selectors
			crew.getItems().clear();
			pilot.getItems().clear();
			
			crew.getItems().addAll(executor.getAvailableCrewAtAirport(origin.getValue().getID()));
			pilot.getItems().addAll(executor.getPilotsAtAirport(origin.getValue().getID()));
		});
		
		// handle submitting the flight
		submit.setOnAction( e -> {
			
			// handle parsing the departure and arrival timestamps
			Timestamp departureTime = null;
			Timestamp arrivalTime = null;
			
			try {
				departureTime = getTimestamp(departure.getText());
				arrivalTime = getTimestamp(arrival.getText());
			}
			catch (ParseException exception) {
				exception.printStackTrace();
				return;
			}
			
			// create flight
			Flight f = new Flight();
			f.setAircraftID(aircraft.getValue().getID());
			f.setSourceAirportID(origin.getValue().getID());
			f.setDestAirportID(destination.getValue().getID());
			f.setDepartureTime(departureTime);
			f.setArrivalTime(arrivalTime);
			f.setCancelled(false);
			
			
			// handle parsing the prices and create a ClassPrice scheme
			ClassPrice price = null;
			
			try {
						price = new ClassPrice(
						0,
						Double.parseDouble(firstClassPrice.getText()),
						Double.parseDouble(businessClassPrice.getText()),
						Double.parseDouble(familyClassPrice.getText()),
						Double.parseDouble(premiumClassPrice.getText()),
						Double.parseDouble(econClassPrice.getText())
				);
			}
			catch (NumberFormatException e1) {
				e1.printStackTrace();
				return;
			}
			
			// get the list of selected employees 
			ObservableList<Employee> e1 = crew.getSelectionModel().getSelectedItems();
			Employee[] emps = e1.toArray(new Employee[0]);
			
			// insert values into database
			executor.insertFlight(f);
			executor.insertClassPrice(f.getID(), price);
			executor.assignEmployeesToFlight(f.getID(), emps);
			executor.assignEmployeesToFlight(f.getID(), pilot.getValue());
			
			Stage stage = (Stage) submit.getParent().getScene().getWindow();
			stage.close();
		});
		
		
		// arrange UI elements
		this.add(aircraft, 1, 0);
		this.add(origin, 1, 1);
		this.add(destination, 1, 2);
		this.add(departure, 1, 3);
		this.add(arrival, 1, 4);
		this.add(pilot, 1, 5);
		this.add(crew, 1, 6);
		this.add(firstClassPrice, 1, 7);
		this.add(businessClassPrice, 1, 8);
		this.add(familyClassPrice, 1, 9);
		this.add(premiumClassPrice, 1, 10);
		this.add(econClassPrice, 1, 11);
		this.add(submit, 1, 12);
		
		this.add(aircraftLabel, 2, 0);
		this.add(originLabel, 2, 1);
		this.add(destinationLabel, 2, 2);
		this.add(departureLabel, 2, 3);
		this.add(arrivalLabel, 2, 4);
		this.add(pilotLabel, 2, 5);
		this.add(crewLabel, 2, 6);
		this.add(firstPriceLabel, 2, 7);
		this.add(businessPriceLabel, 2, 8);
		this.add(familyPriceLabel, 2, 9);
		this.add(premiumPriceLabel, 2, 10);
		this.add(econPriceLabel, 2, 11);
	}
	
	private Timestamp getTimestamp(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return new Timestamp(sdf.parse(time).getTime());
	}
}
