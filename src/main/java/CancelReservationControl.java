import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * This view allows the user to select and cancel a particular reservation
 * Name: CancelReservationControl.java
 * Location src/main/java
 * Date: November 30th 2017
 * @author Noah
 *
 */
public class CancelReservationControl extends VBox {
	
	AirlineSQLExecutor executor;
	
	private ComboBox<Reservation> reservation;
	
	private TextField cancellationDate;
	
	private Button cancel;
	
	public CancelReservationControl(Customer currentCustomer) {
		super();
		
		executor = new AirlineSQLExecutor();
		
		reservation = new ComboBox<Reservation>();
		cancellationDate = new TextField("2017-12-04 12:00");
		cancel = new Button("Cancel");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		// load reservations
		reservation.getItems().addAll(executor.getPendingReservations(currentCustomer.getID()));
		
		cancel.setOnAction( e -> {
			// get the reservation
			Reservation res = reservation.getValue();
			
			// cancel reservation and drop customers
			executor.cancelReservation(res.getID());
			executor.dropPassengersOnReservation(res.getID());
			
			// determine fees
			double feePercentage = .10;
			
			Flight resFlight = executor.getFlightByID(res.getFlightID());
			Timestamp depart = resFlight.getDepartureTime();
			Timestamp cancelTime = null;
			
			try {
				cancelTime = new Timestamp(sdf.parse(cancellationDate.getText()).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
				return;
			}
			
			if (depart.getTime() - cancelTime.getTime() < 10 * 60 * 60 * 1000 ) {
				feePercentage = .33;
				System.out.println("Cancelled within 10 hours -> 33% fee");
			}
			
						
			executor.updateCancelledReservationCharges(res.getID(), feePercentage);
			
			Stage stage = (Stage) this.getScene().getWindow();
			stage.close();
		});
		
		this.getChildren().addAll(
				reservation,
				cancellationDate,
				cancel
		);
	}
}
