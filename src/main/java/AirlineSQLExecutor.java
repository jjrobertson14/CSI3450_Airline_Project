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
}