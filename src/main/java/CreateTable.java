import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:table1;create=true";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void createTable() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String createString = "CREATE TABLE EMPLOYEES(" +
                "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "NAME VARCHAR(20) NOT NULL " + ")";
        Statement stmt = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(createString);
            System.out.print("Table \"Employees\" is created!");
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
