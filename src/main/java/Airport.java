
public class Airport {

    private int airportID;

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
        this.airportID = airportID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getAirportID() {
        return this.airportID;
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

    public void setAirportID(int airportID) {
        this.airportID = airportID;
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

}