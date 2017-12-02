import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

//TODO: Implement Flight type with the ComboBox 

/**
 * This view allows the user to select and delete a flight
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
		cancellationDate = new TextField("2017-12-04");
		cancel = new Button("Cancel");
		
		// load reservations
		reservation.getItems().addAll(executor.getPendingReservations(currentCustomer.getID()));
		
		cancel.setOnAction( e -> {
			Reservation res = reservation.getValue();
			
			executor.cancelReservation(res.getID());
			executor.dropPassengersOnReservation(res.getID());
			executor.updateCancelledReservationCharges(res.getID(), .300);
			
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
