import javafx.stage.Stage;

public class FlightDialog extends Stage {
	
	public static int ADD_FLIGHT = 0;
	public static int EDIT_FLIGHT = 1;
	
	public FlightDialog(int mode) {
		super();
		
		if (mode == ADD_FLIGHT) {
			this.setTitle("Add Flight");
		}
		else if (mode == EDIT_FLIGHT) {
			this.setTitle("Edit Flight");
		}
		else {
			throw new IllegalArgumentException("Invalide mode flag in FlightDialog");
		}
	}
}
