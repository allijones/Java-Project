import java.sql.*;
import java.util.*;

public class ManipulateDays extends DBManipulator {
    public ManipulateDays(DBInterface dbInterface) {
        super(dbInterface);
    }

    /**
     * save: Finds an employee by their ID number (or name?) and
     * updates their avaiable days.
     *
     * @param p
     *
     * */
    public void save(Employee p) throws SQLException {
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
        System.out.println(insertTableSQL);
        dbInterface.runStatement(insertTableSQL);
        System.out.println("Record is inserted into WEEK table!");
    }

    /**
     * availableMonday: returns a list of the IDs of people
     * who are available monday to work
     *
     * @param
     *
     * */
    public ArrayList<Integer> availableDay(Day day) throws SQLException{
        ArrayList<Integer> out = new ArrayList<Integer>();
        String selectTableSQL = "SELECT * from WEEK WHERE " + day.getClass() + " = true";
        System.out.println(selectTableSQL);
        //statement.execute(selectTableSQL);
        ResultSet rs = dbInterface.runStatement(selectTableSQL);
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else {
            do {
                int id = rs.getInt("ID");
                out.add(id);
                System.out.println("id: " + id);
                //System.out.println("age: " + age);
            } while(rs.next());
        }
        return out;
    }

    public void delete( ) throws SQLException{
        String deleteTableSQL = "DELETE FROM WEEK WHERE 1=1";
        System.out.println(deleteTableSQL);
        // execute delete SQL stetement
        dbInterface.runStatement(deleteTableSQL);
        System.out.println("All records are deleted from table!");
    }


    /**
     * loadAll: loads all entries in database
     *
     * @param
     *
     * */

    public Map<Integer, boolean[]> loadAll() throws SQLException {
        String selectTableSQL = "SELECT * from WEEK";
        System.out.println(selectTableSQL);
        //statement.execute(selectTableSQL);
        ResultSet rs = dbInterface.runStatement(selectTableSQL);
        Map<Integer, boolean[]> out = new HashMap<>();
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else {
            do {
                int id = rs.getInt("ID");
                boolean[] schedule = new boolean[7];
                for(int i = 0; i < 7; i++){
                    schedule[i] = rs.getBoolean(Day.fromIndex(i).name());
                }
                out.put(id, schedule);
            } while(rs.next());
        }
        return out;
    }
}
