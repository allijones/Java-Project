import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:database;create=true";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void createTable() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        Statement statement1 = null;
        String createString = "CREATE TABLE EMPLOYEES(" +
                "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "NAME VARCHAR(20) NOT NULL " + ")";
        String createString2 = "CREATE TABLE DAYS(" +
                "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "MONDAY BOOLEAN, " +
                "TUESDAY BOOLEAN, " +
                "WEDNESDAY BOOLEAN, " +
                "THURSDAY BOOLEAN, " +
                "FRIDAY BOOLEAN, " +
                "SATRUDAY BOOLEAN, " +
                "SUNDAY BOOLEAN " + ")";
        //Statement stmt = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement1 = dbConnection.createStatement();
            statement.execute(createString);
            statement1.execute(createString2);
            System.out.print("Table \"Employees\" is created!");
            System.out.print("Table \"Days\" is created!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            if(statement!=null) {
                statement.close();
            }
            if(dbConnection != null) {
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
