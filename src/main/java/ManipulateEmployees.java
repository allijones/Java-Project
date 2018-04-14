import java.sql.*;
import java.util.*;

public class ManipulateEmployees {

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
     * save: inserts a new employee name into the database of names and IDs,
     * or changes an employee entry, depending on if the employee parameter has an
     * id or not.
     *
     * @param p
     *
     * */
    public void save(Employee p) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        //if the person doesn't have an ID, we insert
        if(p.getId() == 0) {
            String insertTableSQL = "INSERT INTO EMPLOYEES" + "(NAME)" + "VALUES" +
                    "("  + "'"+p.getName()+"'" +")";


            try {
                dbConnection = getDBConnection();
                statement = dbConnection.createStatement();
                System.out.println(insertTableSQL);
                statement.execute(insertTableSQL);
                System.out.println("Record is inserted into table!");
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
        //otherwise, update that ID number
        else {
            String updateTableSQL = "UPDATE EMPLOYEES " +
                    " SET NAME = " +"'"+p.getName() + "'" +
                    " WHERE ID = " + p.getId() ;
            try {
                dbConnection = getDBConnection();
                statement = dbConnection.createStatement();
                System.out.println(updateTableSQL);
                // execute update SQL stetement
                statement.execute(updateTableSQL);
                System.out.println("Record is updated in table!");

            } catch (SQLException g) {
                System.out.println(g.getMessage());
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            }
        }
    }


    /**
     * delete: deletes an employee from the database
     *
     * @param id
     *
     * */

    public void delete(int id) throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        String deleteTableSQL = "DELETE FROM EMPLOYEES WHERE ID = " + id;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(deleteTableSQL);
            // execute delete SQL stetement
            statement.execute(deleteTableSQL);
            System.out.println("Record is deleted from table!");
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




    /**
     * findAll: prints all entries in database
     *
     * @param
     *
     * */

    public ArrayList<Integer> idList = new ArrayList<Integer>();
    public void findAll() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String selectTableSQL = "SELECT * from EMPLOYEES";
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
                    idList.add(id);
                    String name = rs.getString("NAME");
                    System.out.println("id: " + id);
                    System.out.println("name : " + name);
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
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public void count() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String countTableSQL = "SELECT COUNT (*) FROM EMPLOYEES";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            //System.out.println(countTableSQL);
            ResultSet rs = statement.executeQuery(countTableSQL);
            rs.next();
            int rowcount = rs.getInt(1);
            int rows = rowcount;
            System.out.println("There are " + rowcount + " entries.");
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
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public int getThisId;
    public void findById(int newId) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        //how to set WHERE to e.id
        String selectTableSQL = "SELECT * from EMPLOYEES WHERE ID = " + newId;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            //System.out.println(selectTableSQL);
            //statement.execute(selectTableSQL);
            //System.out.println("Record is found in table!");
            ResultSet rs = statement.executeQuery(selectTableSQL);
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");
            } else {
                do {
                    System.out.println("Name: " + rs.getString("NAME"));
                    getThisId = rs.getInt("ID");
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
        String deleteTableSQL = "DELETE FROM EMPLOYEES WHERE 1=1";
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
