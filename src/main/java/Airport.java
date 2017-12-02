
public class Airport {

    private int id;

    private String name;

    private double latitude;

    private double longitude;

    public Airport() {

    }

    public Airport(
        int airportID,
        String name,
        double latitude,
        double longitude
    ) {
        this.id = airportID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setID(int airportID) {
        this.id = airportID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    @Override
    public String toString() {
    	return id + ": " + name + " (" + longitude + ", " + latitude + ")";
    }

}