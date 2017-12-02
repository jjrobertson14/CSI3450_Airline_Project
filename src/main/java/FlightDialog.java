import javafx.scene.*;
import javafx.stage.Stage;

public class FlightDialog extends Stage {
	
	public static final int ADD_FLIGHT = 0;
	public static final int EDIT_FLIGHT = 1;
	public static final int CANCEL_FLIGHT = 2;
	public static final int DEPARTURE = 3;
	public static final int ARRIVAL = 4;
	
	public FlightDialog(int mode) {
		super();
		
		Scene scene = null;
		
		switch (mode) {
		case ADD_FLIGHT:
			this.setTitle("Add Flight");
			scene = new Scene(new AddFlightControl());
			break;
		case EDIT_FLIGHT:
			this.setTitle("Edit Flight");
			scene = new Scene(new EditFlightControl());
			 break;
		case CANCEL_FLIGHT:
			this.setTitle("Cancel Flight");
			scene = new Scene(new CancelFlightControl());
			break;
		case DEPARTURE:
			this.setTitle("Record Flight Departure");
			scene = new Scene(new RecordControl(RecordControl.DEPARTURE_MODE));
			break;
		case ARRIVAL:
			this.setTitle("Record Flight Arrival");
			scene = new Scene(new RecordControl(RecordControl.ARRIVAL_MODE));
			break;
		default:
			throw new IllegalArgumentException("Invalide mode flag in FlightDialog");
			 
		}
		
		this.setScene(scene);
	}
}
