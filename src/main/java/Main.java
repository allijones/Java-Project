import java.sql.SQLException;

import javafx.util.Pair;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] argv) throws SQLException {
        //DBInterface database = DBInterface.create();
        //EmployeeGateway manip = new EmployeeGateway();
        ArrayList<Employee> employees = new ArrayList<Employee>();

        ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
        ManipulateDays available = new ManipulateDays(DBInterface.create());

        //clear the database before running this test
        //filter.delete();
        //available.delete();

        //save the test employees to the database
        //for(int i = 0; i < employees.size(); i++){
        //    filter.save(employees.get(i));
        //}
        /*filter.findAll();
        for(int i = 0; i < filter.idList.size(); i++){
            System.out.println(filter.idList.get(i));
            employees.get(i).setId(filter.idList.get(i));
            System.out.println(employees.get(i).getId());
        }

        for(int i = 0; i < employees.size(); i++){
            available.save(employees.get(i));
        }*/

        Scanner reader = new Scanner(System.in);

        ActionMenu saveMenu = new ActionMenu();
        saveMenu.add("Append to SQL", (ActionMenu parent) -> {
            try {
                for (int i = 0; i < employees.size(); i++) {
                    filter.save(employees.get(i));
                }
                for (int i = 0; i < employees.size(); i++) {
                    available.save(employees.get(i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            parent.stop();
        });
        saveMenu.add("Replace SQL", (ActionMenu parent) -> {
            try {
                filter.delete();
                available.delete();
                for (int i = 0; i < employees.size(); i++) {
                    filter.save(employees.get(i));
                }
                for (int i = 0; i < employees.size(); i++) {
                    available.save(employees.get(i));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            parent.stop();
        });
        saveMenu.add("Save to CSV", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
            parent.stop();
        });
        saveMenu.add("Save to XML", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
            parent.stop();
        });
        saveMenu.add("Cancel", ActionMenu::stop);


        ActionMenu loadMenu = new ActionMenu();
        loadMenu.add("Load Test Data", (ActionMenu parent) -> {
            employees.add(EmployeeFactory.create("Alli", new boolean[]{false, true, true, true, false, true, false}));
            employees.add(EmployeeFactory.create("Aidan", new boolean[]{false, true, true, true, false, false, true}));
            employees.add(EmployeeFactory.create("Bob", new boolean[]{true, false, true, false, true, true, true}));
            employees.add(EmployeeFactory.create("Sarah", new boolean[]{false, false, true, false, false, false, true}));
            employees.add(EmployeeFactory.create("Kelly", new boolean[]{true, true, true, false, false, false, true}));
            employees.add(EmployeeFactory.create("Joe", new boolean[]{true, true, true, true, false, false, false}));
            employees.add(EmployeeFactory.create("Cerny", new boolean[]{true, true, true, true, true, true, true}));
            employees.add(EmployeeFactory.create("Rachel", new boolean[]{true, true, true, false, true, true, true}));
            employees.add(EmployeeFactory.create("Billy", new boolean[]{false, true, false, true, true, true, true}));
            employees.add(EmployeeFactory.create("Sam", new boolean[]{false, false, false, true, false, false, false}));
            parent.stop();
        });
        loadMenu.add("Load from SQL", (ActionMenu parent) -> {
            try {
                Map<Integer, String> names = filter.loadAll();
                Map<Integer, boolean[]> schedules = available.loadAll();
                for (Integer id : names.keySet()) {
                    employees.add(EmployeeFactory.create(names.get(id), schedules.get(id), id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            parent.stop();
        });
        loadMenu.add("Load from CSV", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
            parent.stop();
        });
        loadMenu.add("Load from XML", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
            parent.stop();
        });
        loadMenu.add("Cancel", ActionMenu::stop);


        ActionMenu mainMenu = new ActionMenu();
        mainMenu.add("Read Availability", (ActionMenu) -> {
            int idLength = 3;
            int nameLength = 4;
            for(Employee employee : employees){
                idLength = Math.max(idLength, (int)Math.log10(employee.getId()));
                nameLength = Math.max(nameLength, employee.getName().length());
            }
            idLength += 1;
            nameLength += 1;
            System.out.print(String.format("%1$-" + idLength + "s", "ID"));
            System.out.print(String.format("%1$-" + nameLength + "s", "Name"));
            System.out.println("S M T W R F S");
            for(Employee employee : employees){
                System.out.print(String.format("%1$-" + idLength + "s", employee.getId()));
                System.out.print(String.format("%1$-" + nameLength + "s", employee.getName()));
                for(int i = 0; i < 7; i++){
                    System.out.print(employee.getSchedule()[i]?"X ":"  ");
                }
                System.out.println();
            }
        });
        mainMenu.add("Modify Availability", (ActionMenu) -> {
            System.out.println("Error: function not implemented");
        });
        mainMenu.add("Save Availability", (ActionMenu) -> {
            saveMenu.run(reader);
        });
        mainMenu.add("Load Availability", (ActionMenu) -> {
            loadMenu.run(reader);
        });
        mainMenu.add("Clear Availability", (ActionMenu) -> {
            /*try {
                filter.delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            employees.clear();
        });
        mainMenu.add("Build Schedule", (ActionMenu) -> {
            System.out.println("Error: function not implemented");
        });
        mainMenu.add("Modify Schedule", (ActionMenu) -> {
            System.out.println("Error: function not implemented");
        });
        mainMenu.add("Exit", (ActionMenu parent) -> {
            parent.stop();
            System.out.println("\nThank you for using our software.");
        });

        mainMenu.run(reader);


        /*
        System.out.println("Enter '1' to create a new schedule, enter '2' to update an existing schedule.");
        int n = reader.nextInt();

        if(n == 1){
            ManipulateDays available = new ManipulateDays(DBInterface.create());
            ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
            ArrayList<Integer> avalibleList = available.availableDay(Day.MONDAY);
            for(int i = 0; i < avalibleList.size(); i++){
                filter.findById(avalibleList.get(i));
            }
            System.out.println("Please enter 3 ID numbers to work on Monday: ");
            int a = reader.nextInt();
            int b = reader.nextInt();
            int c = reader.nextInt();

            //send names out to the CSV file
        }
        else{

        }
        */
    }
}