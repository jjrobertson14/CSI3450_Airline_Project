import java.util.ArrayList;

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
		
		// create SQL executor
		executor = new AirlineSQLExecutor();
		
		// load the list of available customers
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
		
		customer.setOnAction( e -> {
			// Load customer-specific data here
			Customer selectedCustomer = customer.getValue();
			
			setMembership.setSelected(selectedCustomer.getIsMember());
			
			String itineraryText = "Scheduled Flights:\n";
			
			ArrayList<Flight> customerFlights = executor.getPendingFlights(selectedCustomer.getID());
			for (Flight f : customerFlights) {
				itineraryText += f.toString() + "\n";
			}
			
			itinerary.setText(itineraryText);
		});
		
		scheduleReservation.setOnAction( e -> {
			ReservationDialog dialog = new ReservationDialog(ReservationDialog.CREATE_RESERVATION, customer.getValue());
			dialog.show();
		});
		
		cancelReservation.setOnAction( e -> {
			ReservationDialog dialog = new ReservationDialog(ReservationDialog.CANCEL_RESERVATION, customer.getValue());
			dialog.show();
		});
	}

}
