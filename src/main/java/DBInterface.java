import java.sql.*;

public class DBInterface {
    private DBInterface() throws SQLException {
        connection = createConnection();
        runStatement("CREATE TABLE EMPLOYEES(" +
                "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "NAME VARCHAR(20) NOT NULL " + ")");
        System.out.print("Table \"Employees\" is created!");
        runStatement("CREATE TABLE WEEK(" +
                "ID INTEGER NOT NULL, " +
                "MONDAY BOOLEAN, " +
                "TUESDAY BOOLEAN, " +
                "WEDNESDAY BOOLEAN, " +
                "THURSDAY BOOLEAN, " +
                "FRIDAY BOOLEAN, " +
                "SATRUDAY BOOLEAN, " +
                "SUNDAY BOOLEAN " + ")");
        System.out.print("Table \"Days\" is created!");
    }
    private DBInterface(DBInterface other) {}

    private static DBInterface instance = null;
    public static DBInterface create(){
        if(instance == null){
            synchronized (DBInterface.class){
                if(instance == null){
                    try {
                        instance = new DBInterface();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        instance = null;
                    }
                }
            }
        }
        return instance;
    }

    public void disconnect(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    private Connection createConnection() {
        System.out.println("-------- Derby JDBC Connection Testing ------------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Derby JDBC Driver?");
            e.printStackTrace();
            return null;
        }
        System.out.println("Derby JDBC Driver Registered!");
        Connection makeConnection = null;
        try {
            makeConnection = DriverManager.getConnection("jdbc:derby:database;create=true", "", "");
            //connection = DriverManager.getConnection("jdbc:derby:table1;", "", "");
            if (makeConnection != null) {
                System.out.println("You made it, take control your database now!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return makeConnection;
    }

    public ResultSet runStatement(String toRun) throws SQLException{
        Statement statement = null;
        ResultSet out = null;

        try {
            statement = connection.createStatement();
            statement.execute(toRun);
            out = statement.getResultSet();
            statement.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return out;
    }

    private Connection connection = null;
}
