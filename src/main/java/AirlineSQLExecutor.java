import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Responsible for running SQL queries against the database.
 */
public class AirlineSQLExecutor {

    java.sql.Connection connection;

    MysqlDataSource mysqlDataSource;

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
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("rootpassword");
        mysqlDataSource.setServerName("localhost");

        connection = mysqlDataSource.getConnection();
    }

    private void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

}