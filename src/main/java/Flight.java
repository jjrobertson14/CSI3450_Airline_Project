/**
 * This class is the java object mapping of Flight entities from the database
 * @author John
 *
 */


public class Flight {
    private int id;
    private int aircraftID;
    private int sourceAirportID;
    private int destAirportID;
    private String liftOffTime;
    private String landTime;
    //when the flight actually departs and arrives
    private String departTime;
    private String arriveTime;

    public Flight() {

    }

    public Flight(
            int id,
            int aircraftID,
            int sourceAirportID,
            int destAirportID,
            String liftOffTime,
            String landTime,
            String departTime,
            String arriveTime
    ) {
        this.id = id;
        this.aircraftID = aircraftID;
        this.sourceAirportID = sourceAirportID;
        this.destAirportID = destAirportID;
        this.liftOffTime = liftOffTime;
        this.landTime = landTime;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
    }

    //without depart and arrive times, for when we don't need the actual times
    public Flight(
            int id,
            int aircraftID,
            int sourceAirportID,
            int destAirportID,
            String liftOffTime,
            String landTime
    ) {
        this.id = id;
        this.aircraftID = aircraftID;
        this.sourceAirportID = sourceAirportID;
        this.destAirportID = destAirportID;
        this.liftOffTime = liftOffTime;
        this.landTime = landTime;
    }

    public int getID() {return id;}
    public int getAircraftID() {return this.aircraftID;}
    public int getSourceAirportID() {return this.sourceAirportID;}
    public int getDestAirportID() {return this.destAirportID;}
    public String getLiftOffTime() {return this.liftOffTime;}
    public String getLandTime() {return this.landTime;}
    public String getDepartTime() {return this.departTime;}
    public String getArriveTime() {return this.arriveTime;}
}