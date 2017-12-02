import java.sql.*;

/**
 * This class is the java object mapping of Flight entities from the database
 * @author Noah
 * @author John
 *
 */
public class Flight {
	
	private int id;
	
	private int aircraftID;
	
	private int sourceAirportID;
	
	private int destAirportID;
	
	private Timestamp departureTime;
	
	private Timestamp arrivalTime;
  
  private Timestamp recordedDepartureTime;
  
  private Timestamp recordedArrivalTime;
	
	private boolean cancelled;
  
  public Flight() {
		cancelled = false;
	}
  
	public Flight(int id, int aircraftID, int sourceAirportID, int destAirportID, Timestamp departureTime,
			Timestamp arrivalTime, boolean cancelled) {
		super();
		this.id = id;
		this.aircraftID = aircraftID;
		this.sourceAirportID = sourceAirportID;
		this.destAirportID = destAirportID;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.cancelled = cancelled;
	}
  
  public Flight(
            int id,
            int aircraftID,
            int sourceAirportID,
            int destAirportID,
            Timestamp departureTime,
            Timestamp arrivalTime,
            Timestamp recordedDepartureTime,
            Timestamp recordedArrivalTime,
            boolean cancelled
    ) {
        this.id = id;
        this.aircraftID = aircraftID;
        this.sourceAirportID = sourceAirportID;
        this.destAirportID = destAirportID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.recordedDepartureTime = recordedDepartureTime;
        this.recordedArrivalTime = recordedArrivalTime;
        this.cancelled = cancelled;
  }

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
  
  public Timestamp getRecordedDepartureTime() {
    return recordedDepartureTime; 
  }
  
  public Timestamp setRecordedDepartureTime(Timestamp time) {
    this.recordedDepartureTime = time; 
  }
  
  public Timestamp getRecordedArrivalTime() {
    return recordedArrivalTime;
  }
  
  public Timestamp setRecordedArrivalTime(Timestamp time) {
    this.recordedArrivalTime = time;
  }

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}	
	
	@Override
	public String toString() {
		String res = id + " : " + departureTime + " -> " + arrivalTime;
		return res;
	}	
}