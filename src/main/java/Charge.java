
/**
 * This class is the java object mapping of Charge entities from the database
 * Name: Charge.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class Charge {

	private int reservationID;
	
	private int customerID;
	
	private double memberDiscount;
	
	private double childDiscount;
	
	private double multiwayDiscount;
	
	private double refund;
	
	private double weightFee;
	
	private double insuranceFee;
	
	private double cancellationFee;
	
	private double ticketPrice;
	
	public Charge() {
		
	}

	public Charge(int reservationID, int customerID, double memberDiscount, double childDiscount,
			double multiwayDiscount, double refund, double weightFee, double insuranceFee, double cancellationFee,
			double ticketPrice) {
		super();
		this.reservationID = reservationID;
		this.customerID = customerID;
		this.memberDiscount = memberDiscount;
		this.childDiscount = childDiscount;
		this.multiwayDiscount = multiwayDiscount;
		this.refund = refund;
		this.weightFee = weightFee;
		this.insuranceFee = insuranceFee;
		this.cancellationFee = cancellationFee;
		this.ticketPrice = ticketPrice;
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

	public double getMemberDiscount() {
		return memberDiscount;
	}

	public void setMemberDiscount(double memberDiscount) {
		this.memberDiscount = memberDiscount;
	}

	public double getChildDiscount() {
		return childDiscount;
	}

	public void setChildDiscount(double childDiscount) {
		this.childDiscount = childDiscount;
	}

	public double getMultiwayDiscount() {
		return multiwayDiscount;
	}

	public void setMultiwayDiscount(double multiwayDiscount) {
		this.multiwayDiscount = multiwayDiscount;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getWeightFee() {
		return weightFee;
	}

	public void setWeightFee(double weightFee) {
		this.weightFee = weightFee;
	}

	public double getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public double getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(double cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

}
