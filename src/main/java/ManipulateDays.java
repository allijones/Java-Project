import java.sql.*;
import java.util.*;

public class ManipulateDays {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:table1;";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

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


    /**
     * save: Finds an employee by their ID number (or name?) and
     * updates their avaiable days.
     *
     * @param p
     *
     * */
    public void save(Employee p) throws SQLException {

    }

    /**
     * findByDay: you can search a particular day and the function
     * prints a list of employees that can work that day
     *
     * @param day
     *
     * */
    public void findByDay(String day) throws SQLException{

    }
}
