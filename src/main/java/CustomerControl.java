import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class CustomerControl extends VBox {
	
	private ComboBox<Customer> customer;
	
	private Button scheduleReservation;
	
	private Button cancelReservation;
	
	private CheckBox setMembership;
	
	private Text charges;
	
	public CustomerControl() {
		super();
		customer = new ComboBox<Customer>();
		scheduleReservation = new Button("Create a Reservation");
		cancelReservation = new Button("Cancel a Reservation");
		setMembership = new CheckBox("Member");
		charges = new Text();
		charges.setText("Place Charges Here!\n1. Item 1\n2. Item 2");
		
		this.getChildren().addAll(
				customer,
				scheduleReservation,
				cancelReservation,
				setMembership,
				charges
		);
		
		scheduleReservation.setOnAction( e -> {
			ReservationDialog dialog = new ReservationDialog(ReservationDialog.CREATE_RESERVATION);
			dialog.show();
		});
		
		cancelReservation.setOnAction( e -> {
			ReservationDialog dialog = new ReservationDialog(ReservationDialog.CANCEL_RESERVATION);
			dialog.show();
		});
	}

}
