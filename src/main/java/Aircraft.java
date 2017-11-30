import java.util.Random;

public class Aircraft {
    
	private int id;

	private double mileage;

	private double routingRange;

	private int firstClassSeats;

	private int businessSeats;

	private int familySeats;

	private int premiumSeats;

    private int econSeats;
    
    public Aircraft() {

    }

    public Aircraft(
        int aircraftID, 
        double mileage, 
        double routingRange, 
        int firstClassSeats, 
        int businessSeats,
        int familySeats, 
        int premiumSeats, 
        int econSeats
    ) {
        super();
        this.id = aircraftID;
        this.mileage = mileage;
        this.routingRange = routingRange;
        this.firstClassSeats = firstClassSeats;
        this.businessSeats = businessSeats;
        this.familySeats = familySeats;
        this.premiumSeats = premiumSeats;
        this.econSeats = econSeats;
    }

	public int getID() {
		return id;
	}

	public void setID(int aircraftID) {
		this.id = aircraftID;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public double getRoutingRange() {
		return routingRange;
	}

	public void setRoutingRange(double routingRange) {
		this.routingRange = routingRange;
	}

	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}

	public int getBusinessSeats() {
		return businessSeats;
	}

	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}

	public int getFamilySeats() {
		return familySeats;
	}

	public void setFamilySeats(int familySeats) {
		this.familySeats = familySeats;
	}

	public int getPremiumSeats() {
		return premiumSeats;
	}

	public void setPremiumSeats(int premiumSeats) {
		this.premiumSeats = premiumSeats;
	}

	public int getEconSeats() {
		return econSeats;
	}

	public void setEconSeats(int econSeats) {
		this.econSeats = econSeats;
    }
	
	// TODO: implement this function for use in flight dialogs
	@Override
	public String toString() {
		return id + " : " + "fc." + firstClassSeats + " bs." + businessSeats + " fm." + familySeats
				+ " pm." + premiumSeats + " ec." + econSeats + " : " + mileage + " miles";
	}
    
    public static Aircraft[] generateRandomAircraft() {
        Aircraft[] aircraft = new Aircraft[10];
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            Aircraft a = new Aircraft();

            a.firstClassSeats = rand.nextInt(10) + 10;
            a.businessSeats = rand.nextInt(20) + 15;
            a.premiumSeats = rand.nextInt(20) + 15;
            a.familySeats = rand.nextInt(20) + 20;
            a.econSeats = rand.nextInt(20) + 25;

            a.mileage = 20000 + 40000 * rand.nextDouble();
            a.routingRange = 3500 + 2000 * rand.nextDouble();
        
            aircraft[i] = a;
        }

        return aircraft;
    }
}