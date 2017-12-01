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

    private Label flightRoosterLabel;

    private Label airportLabel;

    private Label passengerListLabel;

    private Label crewListLabel;

    private Label flightListLabel;

    public EmployeeResultsControl(ArrayList resultList, int flightNum, int airportNum, String resultMode) {
        super();
        resultListData = resultList;
        flightNo = flightNum;
        airportNo = airportNum;
        flightLabel = new Label("Selected Flight: " + flightNo);
        airportLabel = new Label("Selected airport: " + airportNum);



        switch (resultMode) {
            case "Passengers of flight" :
                // • Get all customers who have seats reserved on a given flight.
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
                // • Get all the details about the crew members on that flight.
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
                // • Get all flights for a given airport.
                // Load flights here
                ObservableList<String> observableListView3 = FXCollections.observableArrayList();
                fillListFlights(resultListData, observableListView3);
                resultListView = new ListView<String>(observableListView3);

                // Create labels
                flightListLabel = new Label("Flights from airport: \n flightID, aircraftID, sourceAirportID, destAirportID, liftOffTime, landTime");

                this.add(airportLabel, 0, 0);
                this.add(flightListLabel, 0, 1);
                this.add(resultListView, 0, 2);
                break;

            case "Flight rooster" :
                // • View flight rooster, schedule.
                // Load flights here
                ObservableList<String> observableListView4 = FXCollections.observableArrayList();
                fillListFlightsSchedule(resultListData, observableListView4);
                resultListView = new ListView<String>(observableListView4);

                //Create labels
                flightRoosterLabel = new Label ("Flight rooster(roster): \n flightID, aircraftID, sourceAirportID, destAirportID, liftOffTime, departTime, landTime, arriveTime");

                this.add(flightRoosterLabel, 0,0);
                this.add(resultListView, 0, 1);

            default :
                System.out.println("That's not a resultMode value");
        }
    }

    // • Get all customers who have seats reserved on a given flight.
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

    // • Get all the details about the crew members on that flight.
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

    // • Get all flights for a given airport.
    private void fillListFlights(ArrayList<Flight> flightListData, ObservableList<String> observableListView) {
        //iterate through each entity of data
        for ( Flight curFlight : flightListData) {
            String curRow = "";
            curRow += curFlight.getID() + " ";
            curRow += curFlight.getAircraftID() + " ";
            curRow += curFlight.getSourceAirportID() + " ";
            curRow += curFlight.getDestAirportID() + " ";
            curRow += curFlight.getLiftOffTime() + " ";
            curRow += curFlight.getLandTime();

            System.out.println("adding flight to view: \n" + curRow + "\n");

            observableListView.add(curRow);
        }
    }

    // • View flight rooster, schedule.
    private void fillListFlightsSchedule(ArrayList<Flight> flightListData, ObservableList<String> observableListView) {
        //iterate through each entity of data
        for ( Flight curFlight : flightListData) {
            String curRow = "";
            curRow += curFlight.getID() + " ";
            curRow += curFlight.getAircraftID() + " ";
            curRow += curFlight.getSourceAirportID() + " ";
            curRow += curFlight.getDestAirportID() + " ";
            curRow += curFlight.getLiftOffTime() + " ";
            curRow += curFlight.getDepartTime() + " ";
            curRow += curFlight.getLandTime();
            curRow += curFlight.getArriveTime();

            System.out.println("adding flight to view: \n" + curRow + "\n");

            observableListView.add(curRow);
        }
    }

    // • View flight rooster, schedule.
//    private void fillFlightSchedule()
}
