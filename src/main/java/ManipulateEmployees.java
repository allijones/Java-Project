import java.sql.*;
import java.util.*;

/**
 * Uses a DBInterface to manage a table of employees
 *
 * @author  Alli Jones
 */
public class ManipulateEmployees extends DBManipulator{
    public ManipulateEmployees(DBInterface dbInterface) {
        super(dbInterface);
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
        //if the person doesn't have an ID, we insert
        if(p.getId() == 0) {
            String insertTableSQL = "INSERT INTO EMPLOYEES" + "(NAME)" + "VALUES" +
                    "("  + "'"+p.getName()+"'" +")";
            System.out.println(insertTableSQL);
            p.setId(dbInterface.runStatementGetID(insertTableSQL));
            System.out.println("Record is inserted into table!");
        }
        //otherwise, update that ID number
        else {
            String updateTableSQL = "UPDATE EMPLOYEES " +
                    " SET NAME = " +"'"+p.getName() + "'" +
                    " WHERE ID = " + p.getId() ;
            System.out.println(updateTableSQL);
            // execute update SQL stetement
            dbInterface.runStatement(updateTableSQL);
            System.out.println("Record is updated in table!");
        }
    }


    /**
     * delete: deletes an employee from the database
     *
     * @param id
     *
     * */

    public void delete(int id) throws SQLException{
        String deleteTableSQL = "DELETE FROM EMPLOYEES WHERE ID = " + id;
        System.out.println(deleteTableSQL);
        // execute delete SQL stetement
        dbInterface.runStatement(deleteTableSQL);
        System.out.println("Record is deleted from table!");
    }




    /**
     * findAll: prints all entries in database
     *
     * @param
     *
     * */

    public ArrayList<Integer> findAll() throws SQLException {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        String selectTableSQL = "SELECT * from EMPLOYEES";
        System.out.println(selectTableSQL);
        //statement.execute(selectTableSQL);
        ResultSet rs = dbInterface.runStatement(selectTableSQL);
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else {
            do {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                //int age = rs.getInt("AGE");
                //System.out.println("id: " + id);
                System.out.println("name : " + name);
                //System.out.println("age: " + age);
            } while(rs.next());
        }
        return idList;
    }

    /**
     * loadAll: loads all entries in database
     *
     * @param
     *
     * */

    public Map<Integer, String> loadAll() throws SQLException {
        String selectTableSQL = "SELECT * from EMPLOYEES";
        System.out.println(selectTableSQL);
        //statement.execute(selectTableSQL);
        ResultSet rs = dbInterface.runStatement(selectTableSQL);
        Map<Integer, String> out = new HashMap<>();
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else {
            do {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                out.put(id, name);
            } while(rs.next());
        }
        return out;
    }



    /**
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public int count() throws SQLException {
        String countTableSQL = "SELECT COUNT (*) FROM EMPLOYEES";
        //System.out.println(countTableSQL);
        ResultSet rs = dbInterface.runStatement(countTableSQL);
        rs.next();
        int rowcount = rs.getInt(1);
        int rows = rowcount;
        System.out.println("There are " + rowcount + " entries.");
        return rowcount;
    }

    /**
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public String findById(int newId) throws SQLException {
        String out;
        //how to set WHERE to e.id
        String selectTableSQL = "SELECT * from EMPLOYEES WHERE ID = " + newId;
        //System.out.println(selectTableSQL);
        //statement.execute(selectTableSQL);
        //System.out.println("Record is found in table!");
        ResultSet rs = dbInterface.runStatement(selectTableSQL);
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
        }
        do {
            System.out.println("Name: " + rs.getString("NAME"));
            out = rs.getString("NAME");
        } while(rs.next());
        return out;
    }


    public void delete( ) throws SQLException{
        String deleteTableSQL = "DELETE FROM EMPLOYEES WHERE 1=1";
        System.out.println(deleteTableSQL);
        // execute delete SQL stetement
        dbInterface.runStatement(deleteTableSQL);
        System.out.println("All records are deleted from table!");
    }


}
