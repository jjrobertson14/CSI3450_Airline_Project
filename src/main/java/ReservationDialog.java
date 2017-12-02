import javafx.scene.*;
import javafx.stage.*;

public class ReservationDialog extends Stage {

	public static final int CREATE_RESERVATION = 0;
	public static final int CANCEL_RESERVATION = 1;
	
	public ReservationDialog(int mode, Customer currentCustomer) {
		super();
		
		Scene scene = null;
		
		switch (mode) {
		case CREATE_RESERVATION:
			this.setTitle("Schedule a Reservation");
			scene = new Scene(new CreateReservationControl(currentCustomer));
			break;
		case CANCEL_RESERVATION:
			this.setTitle("Cancel a Reservation");
			scene = new Scene(new CancelReservationControl(currentCustomer));
			break;
		}
		
		this.setScene(scene);
	}
}
