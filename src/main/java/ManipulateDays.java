import java.sql.*;
import java.util.*;

public class ManipulateDays {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:database;";
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

        Connection dbConnection = null;
        Statement statement = null;

        String insertTableSQL = "INSERT INTO WEEK" + "(ID, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATRUDAY, SUNDAY)" +
                "VALUES" + "("
                + p.getId() + ", "
                + p.getSchedule()[Day.Monday.getVal()] + ", "
                + p.getSchedule()[Day.Tuesday.getVal()] + ", "
                + p.getSchedule()[Day.Wednesday.getVal()] + ", "
                + p.getSchedule()[Day.Thursday.getVal()] + ", "
                + p.getSchedule()[Day.Friday.getVal()] + ", "
                + p.getSchedule()[Day.Saturday.getVal()] + ", "
                + p.getSchedule()[Day.Sunday.getVal()]
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(insertTableSQL);
            statement.execute(insertTableSQL);
            System.out.println("Record is inserted into WEEK table!");
            //call manipulateDays.save() so that employee name and ID is also inserted there
        }
        catch(SQLException f) {
            System.out.println(f.getMessage());
        }
        finally {
            if(statement != null) {
                statement.close();
            }
            if(dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    /**
     * availableMonday: returns a list of the IDs of people
     * who are available monday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> mondayList = new ArrayList<Integer>();
    public void availableMonday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE MONDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    mondayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    /**
     * availableTuesday: returns a list of the IDs of people
     * who are available Tuesday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> tuesdayList = new ArrayList<Integer>();
    public void availableTuesday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE TUESDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    tuesdayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    /**
     * availableWednesday: returns a list of the IDs of people
     * who are available Wednesday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> wednesdayList = new ArrayList<Integer>();
    public void availableWednesday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE WEDNESDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    wednesdayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    /**
     * availableThursday: returns a list of the IDs of people
     * who are available Thursday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> thursdayList = new ArrayList<Integer>();
    public void availableThursday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE THURSDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    thursdayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    /**
     * availableFriday: returns a list of the IDs of people
     * who are available Friday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> fridayList = new ArrayList<Integer>();
    public void availableFriday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE FRIDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    fridayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    /**
     * availableSaturday: returns a list of the IDs of people
     * who are available Saturday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> saturdayList = new ArrayList<Integer>();
    public void availableSaturday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE SATRUDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    saturdayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    /**
     * availableSunday: returns a list of the IDs of people
     * who are available Sunday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> sundayList = new ArrayList<Integer>();
    public void availableSunday() throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from WEEK WHERE SUNDAY = true";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    int id = rs.getInt("ID");
                    sundayList.add(id);
                    System.out.println("id: " + id);
                    //System.out.println("age: " + age);
                } while(rs.next());
            }
        }
        catch(SQLException h) {
            System.out.println(h.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void delete( ) throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String deleteTableSQL = "DELETE FROM WEEK WHERE 1=1";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(deleteTableSQL);
            // execute delete SQL stetement
            statement.execute(deleteTableSQL);
            System.out.println("All records are deleted from table!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}
