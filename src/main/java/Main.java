import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] argv) throws SQLException {
        //DBInterface database = DBInterface.create();
        //EmployeeGateway manip = new EmployeeGateway();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        Employee employee4 = new Employee();
        Employee employee5 = new Employee();
        Employee employee6 = new Employee();
        Employee employee7 = new Employee();
        Employee employee8 = new Employee();
        Employee employee9 = new Employee();
        Employee employee10 = new Employee();

        ArrayList<Employee> testList = new ArrayList<Employee>();

        employee1.setName("Alli");
        employee1.setSchedule(false, true, true, true, false, true, false);
        testList.add(employee1);

        employee2.setName("Aidan");
        employee2.setSchedule(false, true, true, true, false, false, true);
        testList.add(employee2);

        employee3.setName("Bob");
        employee3.setSchedule(true, false, true, false, true, true, true);
        testList.add(employee3);

        employee4.setName("Sarah");
        employee4.setSchedule(false, false, true, false, false, false, true);
        testList.add(employee4);

        employee5.setName("Kelly");
        employee5.setSchedule(true, true, true, false, false, false, true);
        testList.add(employee5);

        employee6.setName("Joe");
        employee6.setSchedule(true, true, true, true, false, false, false);
        testList.add(employee6);

        employee7.setName("Cerny");
        employee2.setSchedule(true, true, false, false, false, false, true);
        testList.add(employee7);

        employee8.setName("Rachel");
        employee8.setSchedule(true, true, true, false, true, true, true);
        testList.add(employee8);

        employee9.setName("Billy");
        employee9.setSchedule(false, true, false, true, true, true, true);
        testList.add(employee9);

        employee10.setName("Sam");
        employee10.setSchedule(false, false, false, true, false, false, false);
        testList.add(employee10);


        ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
        ManipulateDays available = new ManipulateDays();

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

        Functionality functionality = new Functionality();
        functionality.interact();

        //print them all
        //filter.findAll();

        filter.disconnect();
    }
}