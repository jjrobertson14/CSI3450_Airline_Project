import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RecordControl extends VBox {
	
	public static final int DEPARTURE_MODE = 0;
	public static final int ARRIVAL_MODE = 1;
	
	AirlineSQLExecutor executor;

	private ComboBox<Flight> flight;
	
	private TextField time;
	
	private int mode;
	
	private Button submit;
	
	private Timestamp timestamp;
	
	public RecordControl(int mode) {
		this.mode = mode;
		
		// instantiate SQL executor
		executor = new AirlineSQLExecutor();
		
		// instantiate UI elements
		flight = new ComboBox<Flight>();
		time = new TextField();
		time.setText("2017-12-12 8:30");
		submit = new Button("Submit");
		
		// make sure the timestamp is initialized
		try {
			updateTimestamp(time.getText());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		// handle the user updating the time field
		// if they mess up, just reset it
		time.setOnAction( e -> {
			try {
				updateTimestamp(time.getText());
			} catch (ParseException e1) {
				e1.printStackTrace();
				time.setText("2017-12-12 8:30");
			}
		});
		
		// load values into ComboBox depending on the mode
		// also determine how to handle clicking 'submit'
		if (mode == DEPARTURE_MODE) {
			flight.getItems().addAll(executor.getAvailableFlights());
			submit.setOnAction( e -> {
				executor.insertFlightDeparted(flight.getValue().getID(), timestamp);
				closeDialog();
			});
		}
		else if (mode == ARRIVAL_MODE){
			flight.getItems().addAll(executor.getDepartedFlights());
			submit.setOnAction( e -> {
				executor.insertFlightArrived(flight.getValue().getID(), timestamp);
				closeDialog();
			});
		}
		
		// display UI elements
		this.getChildren().addAll(
				flight,
				time,
				submit
		);
	}
	
	private void updateTimestamp(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = sdf.parse(time);
		timestamp = new Timestamp(date.getTime());
	}
	
	private void closeDialog() {
		Stage stage = (Stage) submit.getScene().getWindow();
		stage.close();
	}
	
}
