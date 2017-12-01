/**
 * This class is the java object mapping of Employee entities from the database
 * @author John
 *
 */


public class Employee {
    private int id;
    private int prevFlightID;
    private int positionID;
    private String firstName;
    private String lastName;
    private String dressCode;

    public Employee() {

    }

    public Employee(
            int id,
            int prevFlightID,
            int positionID,
            String firstName,
            String lastName,
            String dressCode
    ) {
        this.id = id;
        this.prevFlightID = prevFlightID;
        this.positionID = positionID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dressCode = dressCode;
    }

    //constructor without dressCode
    public Employee(
            int id,
            int prevFlightID,
            int positionID,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.prevFlightID = prevFlightID;
        this.positionID = positionID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getID() {return id;}
    public int getPrevFlightID() {return this.prevFlightID;}
    public int getPositionID() {return this.positionID;}
    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getDressCode() {return this.dressCode;}
}