
public class Employee {

	private int id;
	
	private int prevFlightID;
	
	private int positionID;
	
	private String firstName;
	
	private String lastName;
	
	public Employee() {
		
	}

	public Employee(int id, int prevFlightID, int positionID, String firstName, String lastName) {
		super();
		this.id = id;
		this.prevFlightID = prevFlightID;
		this.positionID = positionID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
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
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
