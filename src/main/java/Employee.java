
/**
 * This class is the java object mapping of Employee entities from the database
 * 
 * @author John
 * @author Noah
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

	public Employee(int id, int prevFlightID, int positionID, String firstName, String lastName) {
		this.id = id;
		this.prevFlightID = prevFlightID;
		this.positionID = positionID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Employee(int id, int prevFlightID, int positionID, String firstName, String lastName, String dressCode) {
		this.id = id;
		this.prevFlightID = prevFlightID;
		this.positionID = positionID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dressCode = dressCode;
	}

	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrevFlightID() {
		return prevFlightID;
	}

	public void setPrevFlightID(int prevFlightID) {
		this.prevFlightID = prevFlightID;
	}

	public int getPositionID() {
		return positionID;
	}

	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDressCode() {
		return this.dressCode;
	}

	public void setDressCode(String dressCode) {
		this.dressCode = dressCode;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (this.getClass() != o.getClass()) {
			return false;
		}

		Employee e = (Employee) o;

		return e.id == this.id;
	}

}
