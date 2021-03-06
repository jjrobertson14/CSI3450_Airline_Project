import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


/**
 * This datatype represents an entry in the Aircraft table
 * Name: CancelFlightControl.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class CancelFlightControl extends VBox {
	
	private AirlineSQLExecutor executor;
	
	private ComboBox<Flight> flight;
	
	private Button cancel;
	
	public CancelFlightControl() {
		super();
		
		executor = new AirlineSQLExecutor();
		
		// Load flights		
		flight = new ComboBox<Flight>();
		flight.getItems().addAll(executor.getAvailableFlights());
		
		//cancel button
		cancel = new Button("Cancel");
		cancel.setOnAction( e -> {
			executor.cancelFlight(flight.getValue().getID());
			executor.updateCancelledFlightCharges(flight.getValue().getID());
			executor.cancelFlightReservations(flight.getValue().getID());
			executor.dropPassengersFromFlight(flight.getValue().getID());
			executor.dropAllEmployeesFromFlight(flight.getValue().getID());
			Stage stage = (Stage) this.getScene().getWindow();
			stage.close();
		});
		
		this.getChildren().addAll(flight, cancel);
	}
}
