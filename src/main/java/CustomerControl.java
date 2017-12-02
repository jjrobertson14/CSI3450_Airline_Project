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
	
	private ScrollPane chargePane;
	
	private Text charges;
	
	// display information for all flights for this customer that haven't 'arrived' yet
	private ScrollPane itineraryPane;
	
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
		chargePane = new ScrollPane();
		charges = new Text();
		charges.setText("Purchases:\n");
		itineraryPane = new ScrollPane();
		itinerary = new Text();
		itinerary.setText("Scheduled Flights:\n");
		
		chargePane.setContent(charges);
		itineraryPane.setContent(itinerary);
		
		this.getChildren().addAll(
				customer,
				scheduleReservation,
				cancelReservation,
				setMembership,
				chargePane,
				itineraryPane
		);
		
		customer.setOnAction( e -> {
			// Load customer-specific data here
			Customer selectedCustomer = customer.getValue();
			
			// indicate the customer membership
			setMembership.setSelected(selectedCustomer.getIsMember());
			
			// display all purchases
			String purchaseText = "Purchases:\n";
			
			ArrayList<Purchase> customerPurchases = executor.getPurchases(selectedCustomer.getID());
			System.out.println("SIZE: " + customerPurchases.size());
			for (Purchase p : customerPurchases) {
				purchaseText += p.toString() + "\n";
				
				ArrayList<Charge> customerCharges = executor.getCharges(p.getReservationID());
				
				for (Charge c : customerCharges) {
					purchaseText+= "\tCustomer: " + c.getCustomerID() + "\n";
					purchaseText+= "\tMember Discount: " + c.getMemberDiscount() + "\n";
					purchaseText+= "\tChild Discount: " + c.getChildDiscount() + "\n";
					purchaseText+= "\tMultiway Discount: " + c.getMultiwayDiscount() + "\n";
					purchaseText+= "\tRefund: " + c.getRefund() + "\n";
					purchaseText+= "\tWeight Fee: " + c.getWeightFee() + "\n";
					purchaseText+= "\tInsurance Fee: " + c.getInsuranceFee() + "\n";
					purchaseText+= "\tCancellation Fee: " + c.getCancellationFee() + "\n";
					purchaseText+= "\tTicket Price: " + c.getTicketPrice() + "\n\n";
				}
				
			}
			
			charges.setText(purchaseText);
			
			// display all upcoming flights the customer is a passenger on
			String itineraryText = "Scheduled Flights:\n";
			
			ArrayList<Flight> customerFlights = executor.getPendingFlights(selectedCustomer.getID());
			for (Flight f : customerFlights) {
				itineraryText += f.toString() + "\n";
			}
			
			itinerary.setText(itineraryText);
		});
		
		setMembership.setOnAction(e -> {
			Customer selectedCustomer = customer.getValue();
			boolean value = setMembership.isSelected();
			selectedCustomer.setIsMember(value);
			executor.updateCustomerMembership(selectedCustomer.getID(), value);
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
