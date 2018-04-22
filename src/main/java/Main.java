import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] argv) throws SQLException {
        //DBInterface database = DBInterface.create();
        //EmployeeGateway manip = new EmployeeGateway();
        ArrayList<Employee> testList = new ArrayList<Employee>();

        testList.add(EmployeeFactory.create("Alli",   new boolean[]{false, true,  true,  true,  false, true,  false}));
        testList.add(EmployeeFactory.create("Aidan",  new boolean[]{false, true,  true,  true,  false, false, true }));
        testList.add(EmployeeFactory.create("Bob",    new boolean[]{true,  false, true,  false, true,  true,  true }));
        testList.add(EmployeeFactory.create("Sarah",  new boolean[]{false, false, true,  false, false, false, true }));
        testList.add(EmployeeFactory.create("Kelly",  new boolean[]{true,  true,  true,  false, false, false, true }));
        testList.add(EmployeeFactory.create("Joe",    new boolean[]{true,  true,  true,  true,  false, false, false}));
        testList.add(EmployeeFactory.create("Cerny",  new boolean[]{true,  true,  true,  true,  true,  true,  true }));
        testList.add(EmployeeFactory.create("Rachel", new boolean[]{true,  true,  true,  false, true,  true,  true }));
        testList.add(EmployeeFactory.create("Billy",  new boolean[]{false, true,  false, true,  true,  true,  true }));
        testList.add(EmployeeFactory.create("Sam",    new boolean[]{false, false, false, true,  false, false, false}));


        ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
        ManipulateDays available = new ManipulateDays(DBInterface.create());

        //clear the database before running this test
        filter.delete();
        available.delete();

        //save the test employees to the database
        for(int i = 0; i < testList.size(); i++){
            filter.save(testList.get(i));
        }
        filter.findAll();
        for(int i = 0; i < filter.idList.size(); i++){
            System.out.println(filter.idList.get(i));
            testList.get(i).setId(filter.idList.get(i));
            System.out.println(testList.get(i).getId());
        }

        for(int i = 0; i < testList.size(); i++){
            available.save(testList.get(i));
        }

        Functionality.interact();

        //print them all
        //filter.findAll();

        //filter.disconnect();
    }
}