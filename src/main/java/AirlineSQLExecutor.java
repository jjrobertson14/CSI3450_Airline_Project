import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Responsible for running SQL queries against the database.
 */
public class AirlineSQLExecutor {

    private java.sql.Connection connection;

    private final static MysqlDataSource mysqlDataSource;

    static {
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("rootpassword");
        mysqlDataSource.setServerName("localhost");
    }

    public AirlineSQLExecutor() {

    }

    public boolean canConnect() {

        try {
            establishConnection();
            closeConnection();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    private void establishConnection() throws SQLException {
        connection = mysqlDataSource.getConnection();
    }

    private void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public void insertCustomer(Customer ... customer) {

        final String insertSQL = "INSERT INTO flights.Customer" 
        + "(firstName, lastName, birthDate, member, wheelchair, oxygen)"
        + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            establishConnection();
            java.sql.PreparedStatement statement = connection.prepareStatement(insertSQL);
            
            for (int i = 0; i < customer.length; i++) {
                statement.setString(1, customer[i].getFirstName());
                statement.setString(2, customer[i].getLastName());
                statement.setDate(3, customer[i].getBirthDate());
                statement.setBoolean(4, customer[i].getIsMember());
                statement.setBoolean(5, customer[i].getWheelchair());
                statement.setBoolean(6, customer[i].getOxygen());
                
                statement.addBatch();
            }

            statement.executeBatch();
            closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAirport(Airport ... airport) {
        final String insertSQL = "INSERT INTO flights.Airport"
        + "(airportName, latitude, longitude)"
        +"VALUES (?,?,?)";

        try {
            establishConnection();
            java.sql.PreparedStatement statement = connection.prepareStatement(insertSQL);

            for (int i = 0; i < airport.length; i++) {
                statement.setString(1, airport[i].getName());
                statement.setDouble(2, airport[i].getLatitude());
                statement.setDouble(3, airport[i].getLongitude());

                statement.addBatch();
            }

            statement.executeBatch();
            closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAircraft(Aircraft ... aircraft) {
        final String insertSQL = "INSERT INTO flights.Aircraft"
        + "(mileage, routingRange, firstClassSeats, businessSeats, familySeats, "
        + "premiumSeats, econSeats) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            establishConnection();
            PreparedStatement statement = connection.prepareStatement(insertSQL);

            for (int i = 0; i < aircraft.length; i++) {
                statement.setDouble(1, aircraft[i].getMileage());
                statement.setDouble(2, aircraft[i].getRoutingRange());
                statement.setInt(3, aircraft[i].getFirstClassSeats());
                statement.setInt(4, aircraft[i].getBusinessSeats());
                statement.setInt(5, aircraft[i].getFamilySeats());
                statement.setInt(6, aircraft[i].getPremiumSeats());
                statement.setInt(7, aircraft[i].getEconSeats());

                statement.addBatch();
            }

            statement.executeBatch();
            closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Insert the given flight into the database. After flight is inserted, it will have its ID updated
     * to match the auto-allocated ID in the database for future use
     * @param flight the given flight
     */
    public void insertFlight(Flight flight) {
    	
    	// Query for inserting into flight table
    	final String insertSQL = "INSERT INTO flights.Flight " 
    			+ "(aircraftID, sourceAirportID, destAirportID, departureTime, arrivalTime) "
    			+ "VALUES (?,?,?,?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
    		
    		statement.setInt(1,  flight.getAircraftID());
    		statement.setInt(2, flight.getSourceAirportID());
    		statement.setInt(3, flight.getDestAirportID());
    		statement.setTimestamp(4, flight.getDepartureTime());
    		statement.setTimestamp(5, flight.getArrivalTime());
    		
    		statement.execute();
    		
    		ResultSet set = statement.getGeneratedKeys();
    		
    		// record the auto-generated id in the flight object
    		set.next();
    		int flightKey = set.getInt(1);
    		flight.setId(flightKey);
    		
    		System.out.println("fligthID: " + flightKey);
    		
    		closeConnection();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Record the prices for the different tickets for the given flight
     * @param flight the given flight
     * @param classPrice the ticket prices for the given flight
     */
    public void insertClassPrice(int flightID, ClassPrice classPrice) {
    	// Query for inserting into class Price table
    	final String insertSQL = "INSERT INTO flights.ClassPrices "
    			+ "(class, flightID, price) VALUES "
    			+ "(?,?,?)";
    	
    	// Prepare values for insertion into database
    	final String[] classes = {"first", "business", "family", "premium", "economy"};
    	
		Double[] prices = {
				classPrice.getFirstClass(),
				classPrice.getBusinessClass(), 
				classPrice.getFamilyClass(),
				classPrice.getPremiumClass(),
				classPrice.getEconClass()
		};
		
		// execute insertion
		try {
			establishConnection();
			
			PreparedStatement statement = connection.prepareStatement(insertSQL);
			
			for (int i = 0; i < classes.length; i++ ) {    			
    			statement.setString(1, classes[i]);
    			statement.setInt(2, flightID);
    			statement.setDouble(3, prices[i]);
    			statement.addBatch();
    		}
    		
    		statement.executeBatch();
    		statement.close();
    		
			closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Records that the specified flight left at the specified time
     * @param flightID the given flight ID
     * @param timestamp the time of departure
     */
    public void insertFlightDeparted(int flightID, Timestamp timestamp) {
    	final String insertSQL = "INSERT INTO flights.FlightDeparted "
    			+ "(flightID, departTime) VALUES (?, ?)";
    	
    	try {
    		establishConnection();
    		PreparedStatement statement = connection.prepareStatement(insertSQL);
    		
    		statement.setInt(1, flightID);
    		statement.setTimestamp(2, timestamp);
    		
    		statement.execute();
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Records that the specified flight arrived at the specified time
     * @param flight the given flight
     * @param timestamp the time of arrival
     */
    public void insertFlightArrived (int flightID, Timestamp timestamp) {
    	final String insertSQL = "INSERT INTO flights.FlightArrived "
    			+ "(flightID, arriveTime) VALUES (?, ?)";
    	
    	try {
    		establishConnection();
    		PreparedStatement statement = connection.prepareStatement(insertSQL);
    		
    		statement.setInt(1, flightID);
    		statement.setTimestamp(2, timestamp);
    		
    		statement.execute();
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Obtain the list of customers in the database
     * @return ArrayList of customers in the database
     */
    public ArrayList<Customer> getCustomers() {
    	ArrayList<Customer> customers = new ArrayList<Customer>();
    	System.out.println("Hit!");
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.CUSTOMER";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			customers.add(new Customer(
    					result.getInt("customerID"),
    					result.getString("firstName"),
    					result.getString("lastName"),
    					result.getDate("birthDate"),
    					result.getBoolean("member"),
    					result.getBoolean("wheelchair"),
    					result.getBoolean("oxygen")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return customers;
    }
    
    /**
     * Get a collection of all the flights which are not cancelled and have not yet departed
     * @return the set of available flights
     */
    public ArrayList<Flight> getAvailableFlights() {
    	ArrayList<Flight> flights = new ArrayList<Flight>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Flight "
    				+ "WHERE flightID NOT IN ( SELECT flightID FROM flights.FlightDeparted ) "
    				+ "AND cancelled=false";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			flights.add(new Flight(
    					result.getInt("flightID"),
    					result.getInt("aircraftID"),
    					result.getInt("sourceAirportID"),
    					result.getInt("destAirportID"),
    					result.getTimestamp("departureTime"),
    					result.getTimestamp("arrivalTime"),
    					result.getBoolean("cancelled")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return flights;
    }
    
    /**
     * Return a list of all available flights which are not cancelled and not departed which have 
     * the specified number of available seats in the specified seat class
     * @param ticketClass the seat class
     * @param seats the number of desired seats
     * @return the list of qualifying flights
     */
    public ArrayList<Flight> getAvailableFlights(String ticketClass, int seats) {
    	ArrayList<Flight> flights = new ArrayList<Flight>();
    	
    	final String query = "SELECT * FROM flights.flight f WHERE aircraftID IN (SELECT aircraftID FROM "
    			+ "flights.aircraft WHERE firstClassSeats - (SELECT count(*) FROM flights.passenger WHERE reservationID "
    			+ "IN (SELECT reservationID FROM flights.reservation WHERE flightID = f.flightID AND "
    			+ "class='" + ticketClass +"')) >= "+seats+" ) AND cancelled = false "
    			+ "AND f.flightID NOT IN (SELECT flightID from flights.FlightDeparted)";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			flights.add(new Flight(
    					result.getInt("flightID"),
    					result.getInt("aircraftID"),
    					result.getInt("sourceAirportID"),
    					result.getInt("destAirportID"),
    					result.getTimestamp("departureTime"),
    					result.getTimestamp("arrivalTime"),
    					result.getBoolean("cancelled")
				));
    		}
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return flights;
    }
    
    /**
     * Get the set of all flights that have not yet departed on which the given customer is a passenger
     * @param customerID the ID of the given customer
     * @return
     */
    public ArrayList<Flight> getPendingFlights(int customerID) {
    	ArrayList<Flight> flights = new ArrayList<Flight>();
    	
    	final String query = "SELECT * FROM flights.flight WHERE flightID NOT IN "
    			+ "(SELECT flightID FROM flights.FlightDeparted) AND "
    			+ "flightID IN (SELECT flightID FROM flights.Reservation "
    			+ "WHERE reservationID IN (SELECT reservationID FROM flights.Passenger "
    			+ "WHERE customerID=" + customerID+")) "
				+ "AND cancelled=false";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			flights.add(new Flight(
    					result.getInt("flightID"),
    					result.getInt("aircraftID"),
    					result.getInt("sourceAirportID"),
    					result.getInt("destAirportID"),
    					result.getTimestamp("departureTime"),
    					result.getTimestamp("arrivalTime"),
    					result.getBoolean("cancelled")
				));
    		}
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return flights;
    }
    
    /**
     * Get a list of all flights that have departed, but are not yet landed
     * @return the set of flights that are currently in transit
     */
    public ArrayList<Flight> getDepartedFlights() {
    	ArrayList<Flight> flights = new ArrayList<Flight>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Flight "
    				+ "WHERE flightID IN ( SELECT flightID FROM flights.FlightDeparted ) "
    				+ "AND flightID NOT IN ( SELECT flightID FROM flights.FlightArrived) "
    				+ "AND cancelled=false";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			flights.add(new Flight(
    					result.getInt("flightID"),
    					result.getInt("aircraftID"),
    					result.getInt("sourceAirportID"),
    					result.getInt("destAirportID"),
    					result.getTimestamp("departureTime"),
    					result.getTimestamp("arrivalTime"),
    					result.getBoolean("cancelled")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return flights;
    }
    
    
    /**
     * Synchronize the schedule of the given flight
     * @param flight the given flight
     * @param depart the new departure time
     * @param arrive the new arrival time
     */
    public void updateFlightSchedule(int flightID, Timestamp depart, Timestamp arrive) {
    	
    	final String update = "UPDATE flights.Flight SET "
    			+ "departureTime=Timestamp('" + depart + "'), "
    			+ "arrivalTime=Timestamp('" + arrive + "') "
				+ "WHERE flightID=" + flightID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(update);
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Marks the given flight as cancelled
     * @param flight the flight to be cancelled
     */
    public void cancelFlight(int flightID) {
    	try {
    		establishConnection();
    		
    		final String update = "UPDATE flights.Flight SET "
    				+ "cancelled = true WHERE flightID="+ flightID;
    		
    		Statement statement = connection.createStatement();
    		
    		//TODO: Additional customer logic here
    		
    		statement.executeUpdate(update);
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Get the set of prices for each flight class
     * @param flightID the given flight
     * @return a PriceClass containing the prices for each class
     */
    public ClassPrice getClassPrice(int flightID) {
    	ClassPrice price = null;
    	
    	final String query = "SElECT * FROM flights.ClassPrices WHERE "
    			+ "flightID="+flightID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		price = new ClassPrice();
    		price.setFlightID(flightID);
    		
    		while(result.next()) {
    			
    			double seatPrice = result.getDouble("price");
    			
    			switch (result.getString("class")) {
    			case "first":
    				price.setFirstClass(seatPrice);
    				break;
    			case "business":
    				price.setBusinessClass(seatPrice);
    				break;
    			case "family":
    				price.setFamilyClass(seatPrice);
    				break;
    			case "premium":
    				price.setPremiumClass(seatPrice);
    				break;
    			case "economy":
    				price.setEconClass(seatPrice);
    				
    			}
    		}
    		
    		closeConnection();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return price;
    }
    
    /**
     * Get all aircraft
     * @return a list of all aircraft
     */
    public ArrayList<Aircraft> getAircraft() {
    	ArrayList<Aircraft> aircraft = new ArrayList<Aircraft>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Aircraft";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			aircraft.add(new Aircraft(
    					result.getInt("aircraftID"),
    					result.getDouble("mileage"),
    					result.getDouble("routingRange"),
    					result.getInt("firstClassSeats"),
    					result.getInt("businessSeats"),
    					result.getInt("familySeats"),
    					result.getInt("premiumSeats"),
    					result.getInt("econSeats")
    			));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return aircraft;
    }
    
    /**
     * Get all airports
     * @return a list of all airports
     */
    public ArrayList<Airport> getAirports() {
    	ArrayList<Airport> airports = new ArrayList<Airport>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Airport";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			airports.add(new Airport(
    					result.getInt("airportID"),
    					result.getString("airportName"),
    					result.getDouble("longitude"),
    					result.getDouble("latitude")
    			));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return airports;
    }
    
    /**
     * Get a list of all employees
     * @return a list of all employees
     */
    public ArrayList<Employee> getEmployees() {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Employee";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			employees.add(new Employee(
    					result.getInt("empID"),
    					result.getInt("prevFlightID"),
    					result.getInt("positionID"),
    					result.getString("firstName"),
    					result.getString("lastName")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return employees;
    }
    
    /**
     * Get the non-Pilot employees who are currently working at the given airport
     * @param airport the given airport
     * @return a list of assigned employees
     */
    public ArrayList<Employee> getCrewAtAirport(int airportID) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Employee WHERE empID in "
    				+ "(SELECT empID FROM flights.AirportAssignment WHERE airportID="
    				+ airportID + ") "
    				+ "AND positionID <> 3";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			employees.add(new Employee(
    					result.getInt("empID"),
    					result.getInt("prevFlightID"),
    					result.getInt("positionID"),
    					result.getString("firstName"),
    					result.getString("lastName")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return employees;
    }
    
    
    /**
     * Return a list of pilots at the given Airport
     * @param airportID the id of the given airport
     * @return a list of pilots associated with the given airportID
     */
    public ArrayList<Employee> getPilotsAtAirport(int airportID) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Employee WHERE empID in "
    				+ "(SELECT empID FROM flights.AirportAssignment WHERE airportID="
    				+ airportID + ") AND positionID in (SELECT positionID from "
    				+ "flights.EmployeePosition WHERE dressCode='Pilot uniform')";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			employees.add(new Employee(
    					result.getInt("empID"),
    					result.getInt("prevFlightID"),
    					result.getInt("positionID"),
    					result.getString("firstName"),
    					result.getString("lastName")
				));
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return employees;
    }
    
    
    /**
     * Get all employees at the given airport who aren't assigned to a flight
     * @param airport the given airport
     * @return all employees at the airport who aren't assigned to a flight
     */
    public ArrayList<Employee> getAvailableEmployeesAtAirport(int airportID, Timestamp timestamp) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		// Get all employees at the airport not assigned to a pending flight
    		final String query = "SELECT * FROM flights.Employee "
    				+ "WHERE empID NOT IN (SELECT empID FROM flights.FlightAssignment "
    				+ "WHERE flightID NOT IN (SELECT flightID FROM flights.FlightArrived)) "
    				+ "AND empID IN (SELECT empID from flights.AirportAssignment "
    				+ "WHERE airportID=" + airportID +" )";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			
    		}
    		
    		statement.close();
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return employees;
    }
    
    /**
     * Assign all the employees associated with the given flightID to the given airport
     * @param flightID the ID of the flight employees are associated with
     * @param airportID the ID of the airport employees should be reassigned to
     */
    public void updateEmployeesToAirport(int flightID, int airportID) {
    	final String update = "UPDATE flights.airportassignment SET airportID =" + airportID + " "
    			+ "WHERE empID IN (SELECT empID FROM flights.flightassignment WHERE flightID=" + flightID + ")";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(update);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Set the given flightID as the prevFlightID for all employees associated with this flight
     * @param flightID the given flight
     */
    public void updatePrevFlightID(int flightID) {
    	final String update = "UPDATE flights.Employee SET prevFlightID=" + flightID + " "
    			+ "WHERE empID IN (SELECT empID FROM flights.flightassignment WHERE flightID="+ flightID +")";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(update);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Mark the listed employees as assigned to the given flight
     * @param flightID the given flight
     * @param employees the set of employees to assign
     */
    public void assignEmployeesToFlight(int flightID, Employee ...employees) {
    	
    	final String insertSQL = "INSERT INTO flights.FlightAssignment (flightID, empID) "
    			+"VALUES (?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insertSQL);
    		
    		for (int i = 0; i < employees.length; i++) {
    			statement.setInt(1, flightID);
    			statement.setInt(2, employees[i].getID());
    			
    			statement.addBatch();
    		}
    		
    		statement.executeBatch();
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Get the set of all employees who are assigned to the given flight
     * @param flightID the given flight
     * @return all employees who are assigned to the flight
     */
    public ArrayList<Employee> getCrewOnFlight(int flightID) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	final String query = "SELECT * FROM flights.Employee WHERE "
    			+ "empID IN (SELECT empID FROM flights.FlightAssignment "
    			+ "WHERE flightID=" + flightID + ") "
				+ "AND positionID <> 3";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while(result.next()) {
    			employees.add(new Employee(
    					result.getInt("empID"),
    					result.getInt("prevFlightID"),
    					result.getInt("positionID"),
    					result.getString("firstName"),
    					result.getString("lastName")
				));
    		}
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return employees;
    }
    
    /**
     * Get the designated pilot for the given flight
     * @param flightID the given flight
     * @return the designated pilot for the given flight
     */
    public Employee getPilotOnFlight(int flightID) {
    	
    	Employee pilot = null;
    	
    	final String query = "SELECT * FROM flights.Employee WHERE "
    			+ "positionID=3 AND empID IN (SELECT empID from flights.FlightAssignment "
    			+ "WHERE flightID="+flightID +")";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		result.next();
    		
    		pilot = new Employee(
					result.getInt("empID"),
					result.getInt("prevFlightID"),
					result.getInt("positionID"),
					result.getString("firstName"),
					result.getString("lastName")
			);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return pilot;
    	
    }
    
    /**
     * Remove all employees who have been previously assigned to the given flight
     * @param flightID the flight from which all assigned employees will be removed
     */
    public void dropAllEmployeesFromFlight(int flightID) {
    	
    	final String delete = "DELETE FROM flights.FlightAssignment WHERE "
    			+ "flightID=" + flightID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(delete);
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Drop all class prices associated with the given flight
     * @param flightID the flight whose class prices will be deleted
     */
    public void dropAllClassPriceFromFlight(int flightID) {
    	final String delete = "DELETE FROM flights.ClassPrices WHERE "
    			+ "flightID=" + flightID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(delete);
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    

    //• Get all customers who have seats reserved on a given flight.
    public ArrayList<Customer> getCustomersOnFlight(int flightNo) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        System.out.println("Hit!");

        try {
            establishConnection();
            Statement useFlights = connection.createStatement();
            useFlights.executeQuery("USE Flights;");



            final String query = "SELECT DISTINCT customerID,firstName,lastName,birthDate,member,wheelchair,oxygen FROM ( "
                    + "SELECT Customer.customerID,Customer.firstName,Customer.lastName,Customer.birthDate,Customer.member,Customer.wheelchair,Customer.oxygen FROM Flight "
                    + "JOIN Reservation ON Flight.flightID = " + flightNo + " "
                    + "JOIN Passenger USING (ReservationID) "
                    + "JOIN Customer USING (customerID)"
                    + ") AS sub; ";
            System.out.println(query);

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                customers.add(new Customer(
                        result.getInt("customerID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getDate("birthDate"),
                        result.getBoolean("member"),
                        result.getBoolean("wheelchair"),
                        result.getBoolean("oxygen")
                ));
            }

            statement.close();

            closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    // Get all the details about the crew members on that flight.
    public ArrayList<Employee> getEmployeesOnFlight(int flightNo) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        System.out.println("Hit!");

        try {
            establishConnection();
            Statement useFlights = connection.createStatement();
            useFlights.executeQuery("USE Flights;");



            final String query = "SELECT empID,prevFlightID,positionID,firstName,lastName,dressCode FROM (\n"
                    + "	SELECT FlightAssignment.empID,Employee.prevFlightID,Employee.positionID,Employee.firstName,Employee.lastName,EmployeePosition.dressCode FROM Flight "
                    + "JOIN FlightAssignment ON Flight.flightID = " + flightNo + " "
                    + "JOIN Employee USING (empID)"
                    + "JOIN EmployeePosition USING (positionID)"
                    + ") AS sub; ";
            System.out.println(query);

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                employees.add(new Employee(
                        result.getInt("empID"),
                        result.getInt("prevFlightID"),
                        result.getInt("positionID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("dressCode")
                ));
            }

            statement.close();

            closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    //• Get all flights for a given airport.
    // Assuming that means flights departing from an airport
    public ArrayList<Flight> getFlightsForAirport(int airportNo) {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        System.out.println("Hit!");

        try {
            establishConnection();
            Statement useFlights = connection.createStatement();
            useFlights.executeQuery("USE Flights;");



            final String query = "SELECT flightID,aircraftID,destAirportID,sourceAirportID,departureTime,arrivalTime FROM (\n"
                    + "	SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime FROM Flight "
                    + "WHERE Flight.sourceAirportID = " + airportNo + " "
                    + ") AS sub2\n";
            System.out.println(query);

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                flights.add(new Flight(
                        result.getInt("flightID"),
                        result.getInt("aircraftID"),
                        result.getInt("sourceAirportID"),
                        result.getInt("destAirportID"),
                        result.getTimestamp("liftOffTime"),
                        result.getTimestamp("landTime"),
                        false
                ));
            }

            statement.close();

            closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    // • View flight rooster, schedule.
    // Assuming that means schedule of all flights (based on the word roster)
    public ArrayList<Flight> getFlightRooster() {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        System.out.println("Hit!");

        try {
            establishConnection();
            Statement useFlights = connection.createStatement();
            useFlights.executeQuery("USE Flights;");



            final String query = "SELECT flightID,aircraftID,sourceAirportID,destAirportID,liftOffTime,departTime,landTime,arriveTime FROM (\n"
                    + "	SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,liftOffTime,departTime,landTime,arriveTime FROM Flight \n"
                    + " JOIN FlightDeparted USING (flightID) "
                    + " JOIN FlightArrived USING (flightID) "
                    + ") AS sub3";
            System.out.println(query);

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                flights.add(new Flight(
                        result.getInt("flightID"),
                        result.getInt("aircraftID"),
                        result.getInt("sourceAirportID"),
                        result.getInt("destAirportID"),
                        result.getTimestamp("liftOffTime"),
                        result.getTimestamp("departTime"),
                        result.getTimestamp("landTime"),
                        result.getTimestamp("arriveTime"),
                        false
                ));
            }

            statement.close();

            closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }
    
    /**
     * Enter a reservation for the given flight
     * @param flightID the flight to make a reservation for
     * @return the ID number for the new reservation
     */
    public int insertReservation(int flightID) {
    	int reservationID = 0;
    	
    	final String insert = "INSERT INTO flights.reservation (flightID, cancelled) VALUES "
    			+ "(?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
    		statement.setInt(1, flightID);
    		statement.setBoolean(2, false);
    		
    		statement.execute();
    		
    		ResultSet keys = statement.getGeneratedKeys();
    		
    		keys.next();
    		reservationID = keys.getInt(1);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	
    	return reservationID;
    }
    /**
     * Insert records for which customers are passengers for a given reservation.
     * @param reservationID the reservation the customers are associated with
     * @param seatClass the class of seats on the reservation
     * @param weight the weight carried by each passenger
     * @param customers the set of customers who will be passengers for the reservation
     */
    public void insertPassengers(int reservationID, String seatClass, double weight, Customer ... customers) {
    	
    	final String insert = "INSERT INTO flights.Passenger (reservationID, customerID, carryWeight, class) "
    			+ "VALUES (?,?,?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insert);
    		
    		for (int i = 0; i < customers.length; i++) {
    			statement.setInt(1, reservationID);
    			statement.setInt(2, customers[i].getID());
    			statement.setDouble(3, weight);
    			statement.setString(4, seatClass);
    			
    			statement.addBatch();
    		}
    		
    		statement.executeBatch();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    /**
     * Insert an array of purchase items into the Purchase table
     * @param purchases the array of purchases to insert
     */
    public void insertPurchase(Purchase ...purchases) {
    	final String insert = "INSERT INTO flights.Purchase (reservationID, customerID, paymentMethod, paymentDate) "
    			+ "VALUES (?,?,?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insert);
    		
    		for (int i = 0; i < purchases.length; i++) {
    			Purchase p = purchases[i];
    			statement.setInt(1, p.getReservationID());
    			statement.setInt(2,  p.getCustomerID());
    			statement.setString(3, p.getPaymentMethod());
    			statement.setDate(4, p.getDate());
    			
    			statement.addBatch();
    		}
    		
    		statement.executeBatch();
    		statement.close();    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void insertCharge(Charge...charges) {
    	
    	final String insert = "INSERT INTO flights.Charge (reservationID, customerID, memberDiscount, "
    			+ "childDiscount, multiWayDiscount, refund, weightFee, insuranceFee, cancellationFee, ticketPrice) VALUES "
    			+ "(?,?,?,?,?,?,?,?,?,?)";
    	
    	try {
    		establishConnection();
    		
    		PreparedStatement statement = connection.prepareStatement(insert);
    		
    		for (int i = 0; i < charges.length; i++ ) {
    			Charge c = charges[i];
    			statement.setInt(1, c.getReservationID());
    			statement.setInt(2, c.getCustomerID());
    			statement.setDouble(3, c.getMemberDiscount());
    			statement.setDouble(4, c.getChildDiscount());
    			statement.setDouble(5, c.getMultiwayDiscount());
    			statement.setDouble(6, c.getRefund());
    			statement.setDouble(7, c.getWeightFee());
    			statement.setDouble(8, c.getInsuranceFee());
    			statement.setDouble(9, c.getCancellationFee());
    			statement.setDouble(10, c.getTicketPrice());
    			
    			statement.addBatch();
    		}
    		
    		statement.executeBatch();
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    /**
     * Fetch all pending reservations for the given customer. A reservation is 
     * "pending" if the corresponding flight has not yet departed
     * @param customerID the ID of the given customer
     * @return a list of all pending reservations
     */
    public ArrayList<Reservation> getPendingReservations(int customerID) {
    	ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    	
    	final String query = "SELECT * FROM flights.reservation WHERE reservationID IN "
    			+ "(SELECT reservationID from flights.Purchase WHERE customerID=" + customerID + ") "
				+ "AND flightID NOT IN (SELECT flightID from flights.FlightDeparted) "
				+ "AND cancelled=false";
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			reservations.add(
    				new Reservation(
    						result.getInt("reservationID"),
    						result.getInt("flightID"),
    						false
					)
				);
    		}
    		
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return reservations;
    }
    
    /**
     * Cancel the reservation corresponding to the given ID
     * @param reservationID the ID number of the reservation to cancel
     */
    public void cancelReservation(int reservationID) {
    	
    	final String cancel = "UPDATE flights.Reservation SET "
    			+ "cancelled=true WHERE reservationID="+reservationID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(cancel);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    /**
     * Drop all passengers from the Passenger table associated with the given reservation
     * @param reservationID the ID of the given reservation
     */
    public void dropPassengersOnReservation(int reservationID) {
    	
    	final String delete = "DELETE FROM flights.Passenger WHERE reservationID="+reservationID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(delete);
    		
    		statement.close();
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    /**
     * Apply the given cancellation fee (a percentage of ticket price) to 
     * all the Charges associated with the reservationID
     * @param reservationID the reservation ID 
     * @param cancellationFee the scalar by which the cancellation fee will be calculated
     */
    public void updateCancelledReservationCharges(int reservationID, double cancellationFee) {
    	
    	final String update = "UPDATE flights.Charge SET "
    			+ "refund=ticketPrice + insuranceFee + weightFee - memberDiscount - childDiscount "
    			+ "- multiwayDiscount, cancellationFee=" + cancellationFee +" * ticketPrice "
				+ "WHERE reservationID=" + reservationID;
    	
    	try {
    		establishConnection();
    		
    		Statement statement = connection.createStatement();
    		
    		statement.executeUpdate(update);
    		
    		closeConnection();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	
    }
    
}