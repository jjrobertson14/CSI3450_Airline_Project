import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * This view allows the user to edit a particular flight
 * @author Noah
 *
 */
public class EditFlightControl extends GridPane {
	
	private final String dateFormat = "yyyy-MM-dd HH:mm";
	
	private AirlineSQLExecutor executor;
	
	private ComboBox<Flight> flight;
	
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
	
	private Label flightLabel;
	
	private Label departureLabel;
	
	private Label arrivalLabel;
	
	private Label pilotLabel;
	
	private Label crewLabel;
	
	private Label firstPriceLabel;
	
	private Label businessPriceLabel;
	
	private Label familyPriceLabel;
	
	private Label premiumPriceLabel;
	
	private Label econPriceLabel;

	public EditFlightControl() {
		super();
		
		executor = new AirlineSQLExecutor();
		
		executor.getAvailableFlights();
		
		// Load flights here
		flight = new ComboBox<Flight>();
		flight.getItems().addAll(executor.getAvailableFlights());
		
		// departure and arrival
		departure = new TextField();
		arrival = new TextField();
		
		departure.setText("2017-12-10 8:00");
		arrival.setText("2017-12-10 12:00");
		
		pilot = new ComboBox<Employee>();
		crew = new ListView<Employee>();
		crew.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// setup pricing
		firstClassPrice=new TextField();
		businessClassPrice=new TextField();
		familyClassPrice=new TextField();
		premiumClassPrice=new TextField();
		econClassPrice = new TextField();
		
		// Submit the flight
		submit = new Button("Submit");
		
		// Create labels
		flightLabel = new Label("Select Flight");
		departureLabel = new Label("Departure Time");
		arrivalLabel = new Label("Arrival Time");
		pilotLabel = new Label("Select Pilot");
		crewLabel = new Label("Select Crew Members");
		firstPriceLabel = new Label("$ First Class");
		businessPriceLabel = new Label("$ Business Class");
		familyPriceLabel = new Label("$ Family Class");
		premiumPriceLabel = new Label("$ Premium Class");
		econPriceLabel = new Label("$ Base Price");
		
		// handle selecting a flight
		flight.setOnAction( e -> {		
			// obtain flight
			Flight f = flight.getValue();
			
			// get timestamps
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			departure.setText(formatter.format(f.getDepartureTime()));
			arrival.setText(formatter.format(f.getArrivalTime()));
			
			// get crew
			ArrayList<Employee> assignedEmployees = executor.getCrewOnFlight(f.getID());
			crew.getItems().clear();
			crew.getItems().addAll(assignedEmployees);
			
			// get available employees not on the crew
			ArrayList<Employee> unassignedEmployees = executor.getCrewAtAirport(f.getSourceAirportID());
			
			for (Employee emp : unassignedEmployees) {
				if (!crew.getItems().contains(emp)) {
					crew.getItems().add(emp);
				}
			}
			
			// make sure that the previously assigned employees are marked as such
			for (Employee a : assignedEmployees) {
				crew.getSelectionModel().select(a);
			}
			
			// get pilot
			pilot.getItems().clear();
			pilot.getItems().addAll(executor.getPilotsAtAirport(f.getSourceAirportID()));
			pilot.setValue(executor.getPilotOnFlight(f.getID()));
			
			// get prices
			ClassPrice prices = executor.getClassPrice(f.getID());
			firstClassPrice.setText(""+prices.getFirstClass());
			businessClassPrice.setText(""+prices.getBusinessClass());
			familyClassPrice.setText(""+prices.getFamilyClass());
			premiumClassPrice.setText(""+prices.getPremiumClass());
			econClassPrice.setText(""+prices.getEconClass());
		});
		
		// handle submit button
		submit.setOnAction( e -> {
			
			// Get a reference to the selected flight
			Flight f = flight.getValue();
			
			// Overwrite departure and arrival times
			Timestamp departureTime = null;
			Timestamp arrivalTime = null;
			
			try {
				departureTime = getTimestamp(departure.getText());
				arrivalTime = getTimestamp(arrival.getText());
			}
			catch (ParseException p) {
				p.printStackTrace();
				return;
			}
			
			f.setDepartureTime(departureTime);
			f.setArrivalTime(arrivalTime);
			
			executor.updateFlightSchedule(f.getAircraftID(), departureTime, arrivalTime);
			
			// drop all employees from flight
			executor.dropAllEmployeesFromFlight(f.getID());
			
			// assign all selected employees to flight
			Employee[] updatedCrew = crew.getItems().toArray(new Employee[0]);
			executor.assignEmployeesToFlight(f.getID(), updatedCrew);
			executor.assignEmployeesToFlight(f.getID(), pilot.getValue());
			
			// drop all class prices from flight
			executor.dropAllClassPriceFromFlight(f.getID());
			
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
			
			executor.insertClassPrice(f.getID(), price);
			
			// close dialog
			Stage stage = (Stage) submit.getScene().getWindow();
			stage.close();
			
		});
		
		// arrange UI elements
		this.add(flight, 1, 0);
		this.add(departure, 1, 1);
		this.add(arrival, 1, 2);
		this.add(pilot, 1, 3);
		this.add(crew, 1, 4);
		this.add(firstClassPrice, 1, 5);
		this.add(businessClassPrice, 1, 6);
		this.add(familyClassPrice, 1, 7);
		this.add(premiumClassPrice, 1, 8);
		this.add(econClassPrice, 1, 9);
		this.add(submit, 1, 10);
		
		this.add(flightLabel, 2, 0);
		this.add(departureLabel, 2, 1);
		this.add(arrivalLabel, 2, 2);
		this.add(pilotLabel, 2, 3);
		this.add(crewLabel, 2, 4);
		this.add(firstPriceLabel, 2, 5);
		this.add(businessPriceLabel, 2, 6);
		this.add(familyPriceLabel, 2, 7);
		this.add(premiumPriceLabel, 2, 8);
		this.add(econPriceLabel, 2, 9);
	}
	
	private Timestamp getTimestamp(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return new Timestamp(sdf.parse(time).getTime());
	}
}
