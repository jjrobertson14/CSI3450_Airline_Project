import javafx.scene.*;
import javafx.stage.*;
import java.util.ArrayList;

/**
 * This class generates dialogs for the employee tab
 * @author John
 *
 */

public class EmployeeDialog extends Stage {

    public EmployeeDialog(ArrayList resultList, int flightNum, int airportNum, String resultMode) {
        super();

        this.setTitle("Query Results");
        Scene scene = new Scene(new EmployeeResultsControl(resultList, flightNum, airportNum, resultMode));
        this.setScene(scene);
    }
}
