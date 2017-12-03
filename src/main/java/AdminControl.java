import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * This view presents the various controls in the 'Admin' tab
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class AdminControl extends VBox {
	
	private Button addFlight;
	
	private Button editFlight;
	
	private Button cancelFlight;
	
	private Button addAirport;
	
	private Button flightDeparture;
	
	private Button flightArrival;
	
	public AdminControl() {
		addFlight = new Button("Add Flight");
		editFlight = new Button("Edit Flight");
		cancelFlight = new Button("Cancel Flight");
		addAirport = new Button("Add Airport");
		flightDeparture = new Button("Record Flight Departure");
		flightArrival = new Button("Record Flight Arrival");
		
		this.getChildren().addAll(
				addFlight,
				editFlight,
				cancelFlight,
				addAirport,
				flightDeparture,
				flightArrival
		);
		
		addFlight.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.ADD_FLIGHT);
			dialog.show();
		});
		
		editFlight.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.EDIT_FLIGHT);
			dialog.show();
		});
		
		cancelFlight.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.CANCEL_FLIGHT);
			dialog.show();
		});
		
		addAirport.setOnAction( e -> {
			AirportDialog dialog = new AirportDialog();
			dialog.show();
		});
		
		flightDeparture.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.DEPARTURE);
			dialog.show();
		});
		
		flightArrival.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.ARRIVAL);
			dialog.show();
		});
	}
}