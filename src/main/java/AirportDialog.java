import javafx.scene.*;
import javafx.stage.*;

/**
 * Defines a dialog for hosting an AirportControl
 * Name: AirportDialog.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class AirportDialog extends Stage {
	
	AirportControl airportControl;
	
	public AirportDialog() {
		super();
		airportControl = new AirportControl();
		
		Scene scene = new Scene(airportControl);
		this.setScene(scene);
		this.setTitle("Add Airport");
	}
}