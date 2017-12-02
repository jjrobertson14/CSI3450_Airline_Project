/**
 * Represents a pricing scheme for a particular flight
 * @author Noah
 *
 */
public class ClassPrice {
	
	private int flightID;

	private double firstClass;
	
	private double businessClass;
	
	private double familyClass;
	
	private double premiumClass;
	
	private double econClass;

	public ClassPrice(int flightID, double firstClass, double businessClass, double familyClass, double premiumClass,
			double econClass) {
		super();
		this.flightID = flightID;
		this.firstClass = firstClass;
		this.businessClass = businessClass;
		this.familyClass = familyClass;
		this.premiumClass = premiumClass;
		this.econClass = econClass;
	}
	
	public ClassPrice() {
		
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public double getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(double firstClass) {
		this.firstClass = firstClass;
	}

	public double getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(double businessClass) {
		this.businessClass = businessClass;
	}

	public double getFamilyClass() {
		return familyClass;
	}

	public void setFamilyClass(double familyClass) {
		this.familyClass = familyClass;
	}

	public double getPremiumClass() {
		return premiumClass;
	}

	public void setPremiumClass(double premiumClass) {
		this.premiumClass = premiumClass;
	}

	public double getEconClass() {
		return econClass;
	}

	public void setEconClass(double econClass) {
		this.econClass = econClass;
	}
	
}
