import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;

//TODO: load passengerListData into passengerListView,  make column headers, control for this dialog popping up

/**
 * This view will display the results of the actions done on the Employee tab
 * @author John
 *
 */
public class EmployeeResultsControl extends GridPane {
//customerID,firstName,lastName,birthDate,member,wheelchair,oxygen

    private ArrayList resultListData;

    private ListView<String> resultListView;

    private int flightNo;

    private int airportNo;

    private Label flightLabel;

    private Label passengerListLabel;

    private Label crewListLabel;

    public EmployeeResultsControl(ArrayList resultList, int flightNum, int airportNum, String resultMode) {
        super();
        resultListData = resultList;
        flightNo = flightNum;
        airportNo = airportNum;
        flightLabel = new Label("Selected Flight: " + flightNo);



        switch (resultMode) {
            case "Passengers of flight" :

                // Load passengers here
                ObservableList<String> observableListView1 = FXCollections.observableArrayList();
                fillListViewPassengersOfFlight(resultListData, observableListView1);
                resultListView = new ListView<String>(observableListView1);

                // Create labels
                passengerListLabel = new Label("Passengers of flight: \n ID, Name, DoB, isMember, needsWheelchair, needsOxygen");

                // arrange UI elements

                this.add(flightLabel, 0, 0);
                this.add(passengerListLabel, 0, 1);
                this.add(resultListView, 0, 2);
                break;

            case "Crew of flight" :
                // Load crew members here
                ObservableList<String> observableListView2 = FXCollections.observableArrayList();
                fillListViewCrewOfFlight(resultListData, observableListView2);
                resultListView = new ListView<String>(observableListView2);

                // Create labels
                crewListLabel = new Label("Crew of flight: \n empID, prevFlightID, positionID, firstName, lastName");

                this.add(flightLabel, 0, 0);
                this.add(crewListLabel, 0, 1);
                this.add(resultListView, 0, 2);
                break;

            case "Flights from airport" :
                // Load crew members here


            default :
                System.out.println("That's not a resultMode value");
        }
    }

    private void fillListViewPassengersOfFlight(ArrayList<Customer> customerListData, ObservableList<String> observableListView) {
        //iterate through each entity of data
        for ( Customer curPassenger : customerListData) {
            String curRow = "";
            curRow += curPassenger.getID() + " ";
            curRow += curPassenger.getFirstName() + " ";
            curRow += curPassenger.getLastName() + " ";
            curRow += curPassenger.getBirthDate() + " ";
            curRow += curPassenger.getIsMember() + " ";
            curRow += curPassenger.getWheelchair() + " ";
            curRow += curPassenger.getOxygen();

            System.out.println("adding passenger to view: \n" + curRow + "\n");

            observableListView.add(curRow);
        }
    }

    private void fillListViewCrewOfFlight(ArrayList<Employee> employeeListData, ObservableList<String> observableListView) {
        //iterate through each entity of data
        for ( Employee curEmployee : employeeListData) {
            String curRow = "";
            curRow += curEmployee.getID() + " ";
            curRow += curEmployee.getPrevFlightID() + " ";
            curRow += curEmployee.getPositionID() + " ";
            curRow += curEmployee.getFirstName() + " ";
            curRow += curEmployee.getLastName() + " ";
            curRow += curEmployee.getDressCode() + " ";

            System.out.println("adding crew member to view: \n" + curRow + "\n");

            observableListView.add(curRow);
        }
    }
}
