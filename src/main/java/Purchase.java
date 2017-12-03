import java.sql.*;

/**
 * This class is the java object mapping of Purchase entities from the database
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class Purchase {
	
	private int reservationID;
	
	private int customerID;
	
	private String paymentMethod;
	
	private Date date;
	
	public Purchase() {
		
	}

	public Purchase(int reservationID, int customerID, String paymentMethod, Date date) {
		super();
		this.reservationID = reservationID;
		this.customerID = customerID;
		this.paymentMethod = paymentMethod;
		this.date = date;
	}

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Purchase for Reservation " + reservationID + " On " + date + " : " + paymentMethod;
	}

}
