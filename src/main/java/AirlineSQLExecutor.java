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



            final String query = "SELECT flightID,aircraftID,destAirportID,sourceAirportID,departureTime,arrivalTime,departTime,arriveTime FROM (\n"
                    + "\tSELECT flightID,aircraftID,destAirportID,sourceAirportID,Flight.departureTime,Flight.arrivalTime,FlightDeparted.departTime,FlightArrived.arriveTime FROM Flight\n "
                    + "\tJOIN FlightDeparted USING (flightID)\n"
					+ "\tJOIN FlightArrived USING (flightID)\n"
                    + "\tWHERE Flight.sourceAirportID = 9 #<airport number to check for>\n"
                    + ") AS sub2";
            System.out.println(query);

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



            final String query = "SELECT flightID,aircraftID,sourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM (\n"
                    + "	SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,Flight.departureTime,FlightDeparted.departTime,Flight.arrivalTime,FlightArrived.arriveTime FROM Flight \n"
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
                        result.getTimestamp("departureTime"),
                        result.getTimestamp("departTime"),
                        result.getTimestamp("arrivalTime"),
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
}