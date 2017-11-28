import javafx.scene.*;
import javafx.stage.*;

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