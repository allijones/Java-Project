import java.sql.*;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:employeedata;";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            createDbUserTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE EMPLOYEE (" +
                "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "NAME VARCHAR(255) NOT NULL, " +
                ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate("DROP TABLE EMPLOYEE");
            System.out.println(createTableSQL);
            // execute the SQL statement
            statement.execute(createTableSQL);
            System.out.println("Table \"employee\" is created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
