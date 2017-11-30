import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

//TODO: Implement Flight type with the ComboBox 

/**
 * This view allows the user to select and delete a flight
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
			// TODO: Deletion logic here
			
			Stage stage = (Stage) this.getScene().getWindow();
			stage.close();
		});
		
		this.getChildren().addAll(flight, cancel);
	}
}
