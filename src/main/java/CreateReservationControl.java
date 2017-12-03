import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CreateReservationControl extends GridPane {
	
	private AirlineSQLExecutor executor;
	
	private ListView<Customer> passengers;
	
	private ComboBox<String> passengerClass;
	
	private CheckBox lifeInsurance;
	
	private TextField cargo;
	
	// click this button to pull all flights that have enough seats
	private Button populateFlights;
	
	private ListView<Flight> flights;
	
	private ComboBox<String> paymentMethod;
	
	private TextField paymentDate;
	
	private Button submit;
	
	private Label passengerLabel;
	
	private Label cargoLabel;
	
	private Label paymentDateLabel;
	
	private Label flightLabel;
	
	public CreateReservationControl(Customer currentCustomer) {
		super();
		
		// create sql executor
		executor = new AirlineSQLExecutor();
		
		// instantiate UI elements
		passengers = new ListView<Customer>();
		passengers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		passengerClass = new ComboBox<String>();
		lifeInsurance = new CheckBox("Life Insurance");
		cargo = new TextField("2.5");
		populateFlights = new Button("Populate Applicable Flights");
		flights = new ListView<Flight>();
		flights.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		paymentMethod = new ComboBox<String>();
		paymentDate = new TextField("2017-11-25");
		submit = new Button("Submit Reservation");
		
		passengerLabel = new Label("Select Passengers");
		cargoLabel = new Label("KG Cargo per Passenger");
		flightLabel = new Label("Select From Applicable Flights");
		paymentDateLabel = new Label("Date of Payment");
		
		
		// populate passengers
		passengers.getItems().addAll(executor.getCustomers());
		
		// populate class dropdown
		passengerClass.getItems().addAll(
				"first",
				"business",
				"family",
				"premium",
				"economy"
		);
		
		// populate payment method dropdown
		paymentMethod.getItems().addAll(
				"credit",
				"debit"
		);
		
		// handle populate flights press
		populateFlights.setOnAction( e -> {
			flights.getItems().clear();		
			
			String ticketClass = passengerClass.getValue();
			int seats = passengers.getSelectionModel().getSelectedItems().size();
			
			flights.getItems().addAll(executor.getAvailableFlights(ticketClass, seats));
		});
		
		// handle clicking submit button
		submit.setOnAction( e -> {
			double weight = Double.parseDouble(cargo.getText());
			
			// for each flight ... 
			for (Flight f : flights.getSelectionModel().getSelectedItems()) {
				// create a reservation
				int reservationID = executor.insertReservation(f.getID());
				
				// insert passengers
				Customer[] customers = passengers.getSelectionModel().getSelectedItems().toArray(new Customer[0]);
				executor.insertPassengers(reservationID, passengerClass.getValue(), weight, customers);
				
				//record purchase
				Purchase purchase = null;
				Date date = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
				
				try {
					date = new Date(sdf.parse(paymentDate.getText()).getTime());
				} catch (ParseException e2) {
					e2.printStackTrace();
					return;
				}
				
				purchase = new Purchase(
						reservationID,
						currentCustomer.getID(),
						paymentMethod.getValue(),
						date
				);
				
				executor.insertPurchase(purchase);
				
				// get the ticket prices for the selected flight
				ClassPrice prices = executor.getClassPrice(f.getID());
				
				double price = 0.0;
				
				switch (passengerClass.getValue()) {
				case "first":
					price = prices.getFirstClass();
					break;
				case "business":
					price = prices.getBusinessClass();
					break;
				case "family":
					price = prices.getFamilyClass();
					break;
				case "premium":
					price = prices.getPremiumClass();
					break;
				case "economy":
					price = prices.getEconClass();
					break;
				}
				
				// calculate fees and discounts				
				double memberDiscount = 0;	
				double insuranceFee = 0;
				double weightFee = 0;
				double multiwayDiscount = 0.0;
				double childDiscount = 0;
				
				if (currentCustomer.getIsMember()) {
					memberDiscount = price * .15;
				}
				
				if (lifeInsurance.isSelected()) {
					insuranceFee = 100.00;
				}
				
				if (weight > 5) {
					weightFee = 30.00;
				}
				
				if (flights.getSelectionModel().getSelectedItems().size() > 1) {
					multiwayDiscount = 30.00;
				}
				
				Date cutOff = null;
				
				try {
					cutOff = new Date(sdf.parse("2001-01-01").getTime());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				Charge[] charges = new Charge[customers.length];
				
				for (int i = 0; i < customers.length; i++) {		
					
					Customer cust = customers[i];
					
					Date birthDate = cust.getBirthDate();
					
					if (birthDate.compareTo(cutOff) > 0) {
						childDiscount = 15;
						System.out.println(birthDate);
					}
					else {
						childDiscount = 0;
					}
					
					Charge charge = new Charge(
							reservationID,
							cust.getID(),
							memberDiscount,
							childDiscount,
							multiwayDiscount,
							0, // refund
							weightFee,
							insuranceFee,
							0, // cancellationFee
							price
					);
					
					charges[i] = charge;
				}
				
				executor.insertCharge(charges);
				
				Stage stage = (Stage) submit.getParent().getScene().getWindow();
				stage.close();
				
			}
		});
		
		
		// arrange UI elements
		this.add(passengers, 1, 0);
		this.add(passengerClass, 1, 1);
		this.add(lifeInsurance, 1, 2);
		this.add(cargo, 1, 3);
		this.add(populateFlights, 1, 4);
		this.add(flights, 1, 5);
		this.add(paymentMethod, 1, 6);
		this.add(paymentDate, 1, 7);
		this.add(submit, 1, 8);
	
		this.add(passengerLabel, 2, 0);
		this.add(cargoLabel, 2, 3);
		this.add(flightLabel, 2, 4);
		this.add(paymentDateLabel, 2, 7);
	}
	
}