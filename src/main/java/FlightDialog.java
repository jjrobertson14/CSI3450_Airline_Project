import javafx.scene.*;
import javafx.stage.Stage;

public class FlightDialog extends Stage {
	
	public static int ADD_FLIGHT = 0;
	public static int EDIT_FLIGHT = 1;
	public static int CANCEL_FLIGHT = 2;
	
	public FlightDialog(int mode) {
		super();
		
		Scene scene;
		
		if (mode == ADD_FLIGHT) {
			this.setTitle("Add Flight");
			scene = new Scene(new AddFlightControl());
			this.setScene(scene);
		}
		else if (mode == EDIT_FLIGHT) {
			this.setTitle("Edit Flight");
			scene = new Scene(new EditFlightControl());
			this.setScene(scene);
		}
		else if (mode == CANCEL_FLIGHT) {
			this.setTitle("Cancel Flight");
			scene = new Scene(new CancelFlightControl());
			this.setScene(scene);
		}
		else {
			throw new IllegalArgumentException("Invalide mode flag in FlightDialog");
		}
	}
}
