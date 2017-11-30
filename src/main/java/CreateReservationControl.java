import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CreateReservationControl extends GridPane {
	
	private ListView<Customer> passengers;
	
	private ComboBox<String> passengerClass;
	
	// click this button to pull all flights that have enough seats
	private Button populate;
	
	private ListView<String> flight;
	
	private Button submit;
	
	private Label passengerLabel;
	
	private Label flightLabel;
	
	public CreateReservationControl() {
		super();
		
		passengers = new ListView<Customer>();
		passengerClass = new ComboBox<String>();
		populate = new Button("Populate Applicable Flights");
		flight = new ListView<String>();
		submit = new Button("Submit Reservation");
		
		passengerLabel = new Label("Select Passengers");
		flightLabel = new Label("Select From Applicable Flights");
		
		this.add(passengers, 1, 0);
		this.add(passengerClass, 1, 1);
		this.add(populate, 1, 2);
		this.add(flight, 1, 3);
		this.add(submit, 1, 4);
	
		this.add(passengerLabel, 2, 0);
		this.add(flightLabel, 2, 3);
	}
	
}