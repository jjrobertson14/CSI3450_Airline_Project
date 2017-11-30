import java.sql.*;

public class Flight {
	
	private int id;
	
	private int aircraftID;
	
	private int sourceAirportID;
	
	private int destAirportID;
	
	private Date departureTime;
	
	private Date arrivalTime;

	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAircraftID() {
		return aircraftID;
	}

	public void setAircraftID(int aircraftID) {
		this.aircraftID = aircraftID;
	}

	public int getSourceAirportID() {
		return sourceAirportID;
	}

	public void setSourceAirportID(int sourceAirportID) {
		this.sourceAirportID = sourceAirportID;
	}

	public int getDestAirportID() {
		return destAirportID;
	}

	public void setDestAirportID(int destAirportID) {
		this.destAirportID = destAirportID;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Flight(int id, int aircraftID, int sourceAirportID, int destAirportID, Date departureTime,
			Date arrivalTime) {
		super();
		this.id = id;
		this.aircraftID = aircraftID;
		this.sourceAirportID = sourceAirportID;
		this.destAirportID = destAirportID;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	public Flight() {
		
	}
	
	
}
