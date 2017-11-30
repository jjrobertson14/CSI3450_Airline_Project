import java.sql.*;

public class Flight {
	
	private int id;
	
	private int aircraftID;
	
	private int sourceAirportID;
	
	private int destAirportID;
	
	private Timestamp departureTime;
	
	private Timestamp arrivalTime;

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

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Flight(int id, int aircraftID, int sourceAirportID, int destAirportID, Timestamp departureTime,
			Timestamp arrivalTime) {
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
	
	@Override
	public String toString() {
		String res = id + " : " + departureTime + " -> " + arrivalTime;
		return res;
	}
	
	
}
