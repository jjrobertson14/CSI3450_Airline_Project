import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class CustomerControl extends VBox {
	
	private AirlineSQLExecutor executor;
	
	private ComboBox<Customer> customer;
	
	private Button scheduleReservation;
	
	private Button cancelReservation;
	
	private CheckBox setMembership;
	
	private Text charges;
	
	// display information for all flights for this customer that haven't 'arrived' yet
	private Text itinerary; 
	
	public CustomerControl() {
		super();
		
		executor = new AirlineSQLExecutor();
		
		customer = new ComboBox<Customer>();
		customer.getItems().addAll(executor.getCustomers());
		
		scheduleReservation = new Button("Create a Reservation");
		cancelReservation = new Button("Cancel a Reservation");
		setMembership = new CheckBox("Member");
		charges = new Text();
		charges.setText("Place Charges Here!\n1. Item 1\n2. Item 2");
		itinerary = new Text();
		itinerary.setText("This is the itenerary!\nFlight a) ...\nFlight b) ...");
		
		this.getChildren().addAll(
				customer,
				scheduleReservation,
				cancelReservation,
				setMembership,
				charges,
				itinerary
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
