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
	
	private ComboBox<String> flight;
	
	private Button cancel;
	
	public CancelFlightControl() {
		super();
		
		// Load flights
		ObservableList<String> flights = FXCollections.observableArrayList(
			"Flight 1: Los Angeles -> Detroit",
			"Flight 2: New York -> London",
			"Flight 3: San Jose -> Mexico City"
		);
		
		flight = new ComboBox<String>(flights);
		
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
