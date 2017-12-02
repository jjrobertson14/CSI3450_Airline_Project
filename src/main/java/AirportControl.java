import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * The UI Dialog for creating a new Airport in the "Admin" tab
 * @author Noah
 *
 */
public class AirportControl extends GridPane {
	
	private TextField airportName;
	
	private TextField airportLongitude;
	
	private TextField airportLatitude;
	
	private Button submit;
	
	private Label nameLabel;
	
	private Label longitudeLabel;
	
	private Label latitudeLabel;
	
	public AirportControl() {
		
		// create text fields
		airportName = new TextField();
		airportLongitude = new TextField();
		airportLatitude = new TextField();
		
		// create submission button
		submit = new Button("Submit");
		
		// create labels
		nameLabel = new Label("Airport Name");
		longitudeLabel = new Label("Longitude");
		latitudeLabel = new Label("Latitude");
		
		this.add(airportName, 1, 0);
		this.add(airportLongitude, 1, 1);
		this.add(airportLatitude, 1, 2);
		this.add(submit, 1, 3);
		
		this.add(nameLabel, 2, 0);
		this.add(longitudeLabel, 2, 1);
		this.add(latitudeLabel, 2, 2);
	}
}
