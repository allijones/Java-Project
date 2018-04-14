import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] argv) throws SQLException {
        DBInterface database = DBInterface.create();
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
        employee1.setId(0);
        employee1.setMon(true);
        employee1.setTues(true);
        employee1.setWed(true);
        employee1.setThurs(false);
        employee1.setFri(true);
        employee1.setSat(false);
        employee1.setSun(false);
        testList.add(employee1);

        employee2.setName("Aidan");
        employee2.setMon(false);
        employee2.setTues(true);
        employee2.setWed(true);
        employee2.setThurs(true);
        employee2.setFri(false);
        employee2.setSat(false);
        employee2.setSun(true);
        employee2.setId(0);
        testList.add(employee2);

        employee3.setName("Bob");
        employee3.setId(0);
        employee3.setMon(true);
        employee3.setTues(true);
        employee3.setWed(true);
        employee3.setThurs(true);
        employee3.setFri(true);
        employee3.setSat(true);
        employee3.setSun(true);
        testList.add(employee3);

        employee4.setName("Sarah");
        employee4.setId(0);
        employee4.setMon(false);
        employee4.setTues(false);
        employee4.setWed(true);
        employee4.setThurs(false);
        employee4.setFri(true);
        employee4.setSat(true);
        employee4.setSun(true);
        testList.add(employee4);

        employee5.setName("Kelly");
        employee5.setId(0);
        employee5.setMon(true);
        employee5.setTues(false);
        employee5.setWed(false);
        employee5.setThurs(false);
        employee5.setFri(true);
        employee5.setSat(false);
        employee5.setSun(true);
        testList.add(employee5);

        employee6.setName("Joe");
        employee6.setId(0);
        employee6.setMon(false);
        employee6.setTues(true);
        employee6.setWed(false);
        employee6.setThurs(true);
        employee6.setFri(true);
        employee6.setSat(true);
        employee6.setSun(false);
        testList.add(employee6);

        employee7.setName("Cerny");
        employee7.setId(0);
        employee7.setMon(true);
        employee7.setTues(true);
        employee7.setWed(true);
        employee7.setThurs(true);
        employee7.setFri(false);
        employee7.setSat(false);
        employee7.setSun(false);
        testList.add(employee7);

        employee8.setName("Rachel");
        employee8.setId(0);
        employee8.setMon(true);
        employee8.setTues(true);
        employee8.setWed(true);
        employee8.setThurs(false);
        employee8.setFri(true);
        employee8.setSat(false);
        employee8.setSun(false);
        testList.add(employee8);

        employee9.setName("Billy");
        employee9.setId(0);
        employee9.setMon(false);
        employee9.setTues(true);
        employee9.setWed(true);
        employee9.setThurs(true);
        employee9.setFri(true);
        employee9.setSat(false);
        employee9.setSun(true);
        testList.add(employee9);

        employee10.setName("Sam");
        employee10.setId(0);
        employee10.setMon(true);
        employee10.setTues(true);
        employee10.setWed(true);
        employee10.setThurs(false);
        employee10.setFri(false);
        employee10.setSat(false);
        employee10.setSun(false);
        testList.add(employee10);


        ManipulateEmployees filter = new ManipulateEmployees();
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

        database.disconnect();
    }
}