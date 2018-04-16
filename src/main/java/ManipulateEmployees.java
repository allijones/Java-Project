import java.sql.*;
import java.util.*;

public class ManipulateEmployees {
    private DBInterface dbInterface;

    public ManipulateEmployees(DBInterface dbInterface) {
        this.dbInterface = dbInterface;
    }

    public void setDBInterface(DBInterface dbInterface) {
        this.dbInterface = dbInterface;
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
            dbInterface.runStatement(insertTableSQL);
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

    public ArrayList<Integer> idList = new ArrayList<Integer>();
    public void findAll() throws SQLException {
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
                int age = rs.getInt("AGE");
                //System.out.println("id: " + id);
                System.out.println("name : " + name);
                //System.out.println("age: " + age);
            } while(rs.next());
        }
    }



    /**
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public void count() throws SQLException {
        String countTableSQL = "SELECT COUNT (*) FROM EMPLOYEES";
        //System.out.println(countTableSQL);
        ResultSet rs = dbInterface.runStatement(countTableSQL);
        rs.next();
        int rowcount = rs.getInt(1);
        int rows = rowcount;
        System.out.println("There are " + rowcount + " entries.");
    }

    /**
     * count: counter total number of employees in database
     *
     * @param
     *
     * */
    public int getThisId;
    public void findById(int newId) throws SQLException {
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
            getThisId = rs.getInt("ID");
        } while(rs.next());
    }


    public void delete( ) throws SQLException{
        String deleteTableSQL = "DELETE FROM EMPLOYEES WHERE 1=1";
        System.out.println(deleteTableSQL);
        // execute delete SQL stetement
        dbInterface.runStatement(deleteTableSQL);
        System.out.println("All records are deleted from table!");
    }


}
