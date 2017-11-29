import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AdminControl extends VBox {
	
	private Button addFlight;
	
	private Button editFlight;
	
	private Button addAirport;
	
	public AdminControl() {
		addFlight = new Button("Add Flight");
		editFlight = new Button("Edit Flight");
		addAirport = new Button("Add Airport");
		
		this.getChildren().addAll(
				addFlight,
				editFlight,
				addAirport
		);
		
		addFlight.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.ADD_FLIGHT);
			dialog.show();
		});
		
		editFlight.setOnAction( e -> {
			FlightDialog dialog = new FlightDialog(FlightDialog.EDIT_FLIGHT);
			dialog.show();
		});
		
		addAirport.setOnAction( e -> {
			AirportDialog dialog = new AirportDialog();
			dialog.show();
		});
	}
}