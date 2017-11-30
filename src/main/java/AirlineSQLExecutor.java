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
        + "(name, latitude, longitude)"
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
    				+ "WHERE flightID NOT IN ( SELECT flightID FROM flights.FlightDeparted )";
    		
    		Statement statement = connection.createStatement();
    		
    		ResultSet result = statement.executeQuery(query);
    		
    		while (result.next()) {
    			flights.add(new Flight(
    					result.getInt("flightID"),
    					result.getInt("aircraftID"),
    					result.getInt("sourceAirportID"),
    					result.getInt("destAirportID"),
    					result.getTimestamp("departureTime"),
    					result.getTimestamp("arrivalTime")
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
     * Get the employees who are currently working at the given airport
     * @param airport the given airport
     * @return a list of assigned employees
     */
    public ArrayList<Employee> getEmployeesAtAirport(Airport airport) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Employee WHERE empID in "
    				+ "(SELECT empID FROM flights.AirportAssignment WHERE airportID="
    				+ airport.getID() + ")";
    		
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
     * Return a list of pilots at the given airport
     * @param airport the given airport
     * @return a list of pilots at the given airport
     */
    public ArrayList<Employee> getPilotsAtAirport(Airport airport) {
    	ArrayList<Employee> employees = new ArrayList<Employee>();
    	
    	try {
    		establishConnection();
    		
    		final String query = "SELECT * FROM flights.Employee WHERE empID in "
    				+ "(SELECT empID FROM flights.AirportAssignment WHERE airportID="
    				+ airport.getID() + ") AND positionID in (SELECT positionID from "
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
    

}