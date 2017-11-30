import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RecordControl extends VBox {
	
	public static final int DEPARTURE_MODE = 0;
	public static final int ARRIVAL_MODE = 1;

	private ComboBox<String> flight;
	
	private TextField time;
	
	private int mode;
	
	private Button submit;
	
	public RecordControl(int mode) {
		this.mode = mode;
		
		flight = new ComboBox<String>();
		time = new TextField();
		time.setText("2017-12-12 8:30:00");
		submit = new Button("Submit");
		
		this.getChildren().addAll(
				flight,
				time,
				submit
		);
	}
	
}
