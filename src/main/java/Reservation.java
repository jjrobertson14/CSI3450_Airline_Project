/**
 * This class is the java object mapping of Reservation entities from the database
 * Name: Reservation.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class Reservation {

	private int reservationID;
	
	private int flightID;
	
	private boolean cancelled;
	
	public Reservation() {
		
	}
	
	public Reservation(int reservationID, int flightID, boolean cancelled) {
		super();
		this.reservationID = reservationID;
		this.flightID = flightID;
		this.cancelled = cancelled;
	}

	public int getID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public String toString() {
		return "Reservation: " + reservationID + " — Flight No: " + flightID;
	}
	
}
