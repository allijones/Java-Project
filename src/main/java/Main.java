import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] argv) throws SQLException {
        //DBInterface database = DBInterface.create();
        //EmployeeGateway manip = new EmployeeGateway();
        ArrayList<Employee> employees = new ArrayList<Employee>();

        DBInterface dbInterface = DBInterface.create("database");
        ManipulateEmployees filter = new ManipulateEmployees(dbInterface);
        ManipulateDays available = new ManipulateDays(dbInterface);

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
            PrintWriter writer = null;
            try {
                System.out.print("Enter a name for the CSV file: ");
                String filename = reader.next();
                writer = new PrintWriter(filename, "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            for (Employee employee : employees) {
                writer.print(employee.getId() + "," + employee.getName() + ",");
                for (Boolean day : employee.getSchedule()) {
                    writer.print(day ? "X" : "O");
                }
                writer.println();
            }
            writer.close();
            parent.stop();
        });
        /*saveMenu.add("Save to XML", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
        });*/
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
            try {
                System.out.print("Enter the CSV file's name: ");
                String filename = reader.next();
                Scanner csvIn = new Scanner(new File(filename));

                while (csvIn.hasNext()) {
                    String[] data = csvIn.nextLine().split(",");
                    boolean[] schedule = new boolean[7];
                    for (int i = 0; i < 7; i++) {
                        schedule[i] = data[2].charAt(i) == 'X';
                    }
                    employees.add(EmployeeFactory.create(data[1], schedule, Integer.parseInt(data[0])));
                }

                parent.stop();
            } catch (FileNotFoundException e) {
                System.out.println("Invalid File");
            }
        });
        /*loadMenu.add("Load from XML", (ActionMenu parent) -> {
            System.out.println("Error: function not implemented");
        });*/


        ActionMenu mainMenu = new ActionMenu();
        mainMenu.add("Display Data", (ActionMenu) -> {
            int idLength = 3;
            int nameLength = 4;
            for (Employee employee : employees) {
                idLength = Math.max(idLength, (int) Math.log10(employee.getId()));
                nameLength = Math.max(nameLength, employee.getName().length());
            }
            idLength += 1;
            nameLength += 1;
            System.out.print(String.format("%1$-" + idLength + "s", "ID"));
            System.out.print(String.format("%1$-" + nameLength + "s", "Name"));
            System.out.println("S M T W R F S");
            for (Employee employee : employees) {
                System.out.print(String.format("%1$-" + idLength + "s", employee.getId()));
                System.out.print(String.format("%1$-" + nameLength + "s", employee.getName()));
                for (int i = 0; i < 7; i++) {
                    System.out.print(employee.getSchedule()[i] ? "X " : "  ");
                }
                System.out.println();
            }
        });
        /*mainMenu.add("Modify Availability", (ActionMenu) -> {
            System.out.println("Error: function not implemented");
        });*/
        mainMenu.add("Save Data", (ActionMenu) -> {
            saveMenu.run(reader, System.out);
        });
        mainMenu.add("Load Data", (ActionMenu) -> {
            loadMenu.run(reader, System.out);
        });
        mainMenu.add("Clear Loaded Data", (ActionMenu) -> {
            /*try {
                filter.delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            employees.clear();
        });
        mainMenu.add("Build Schedule", (ActionMenu) -> {
            List<Set<Employee>> scheduled = Main.buildSchedule(reader, employees, 3);
            System.out.print("\nEnter a name for the CSV file: ");
            String filename = reader.next();
            PrintWriter writer = null;
            while (writer == null) {
                try {
                    writer = new PrintWriter(filename, "UTF-8");
                } catch (FileNotFoundException e) {
                    System.out.println("Invalid path, try again: ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 7; i++) {
                writer.print(Day.fromIndex(i).name());
                for (Employee employee : scheduled.get(i)) {
                    writer.print("," + employee.getId() + "," + employee.getName());
                }
                writer.println();
            }
            writer.close();
        });
        mainMenu.add("Add to Schedule", (ActionMenu) -> {
            System.out.print("Path to source CSV file: ");
            String infilename = reader.next();
            try {
                Scanner csvIn = new Scanner(new File(infilename));
                String[] lines = new String[7];
                for(int i = 0; i < 6; i++){
                    lines[i] = csvIn.nextLine();
                }
                List<Set<Employee>> scheduled = Main.buildSchedule(reader, employees, 0);
                System.out.print("\nEnter a name for the CSV file: ");
                String filename = reader.next();
                PrintWriter writer = null;
                while (writer == null) {
                    try {
                        writer = new PrintWriter(filename, "UTF-8");
                    } catch (FileNotFoundException e) {
                        System.out.println("Invalid path, try again: ");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                for(int i = 0; i < 6; i++){
                    writer.print(lines[i]);
                    for (Employee employee : scheduled.get(i)) {
                        writer.print("," + employee.getId() + "," + employee.getName());
                    }
                    writer.println();
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Invalid path.");
            }
        });
        mainMenu.add("Exit", (ActionMenu parent) -> {
            parent.stop();
            System.out.println("\nThank you for using our software.");
        });

        loadMenu.run(reader, System.out);
        loadMenu.add("Cancel", ActionMenu::stop);
        mainMenu.run(reader, System.out);

        dbInterface.disconnect();

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

    private static List<Set<Employee>> buildSchedule(Scanner reader, ArrayList<Employee> employees, int minWorking) {
        List<Set<Employee>> scheduled = new ArrayList<>();
        for (int d = 0; d < 7; d++) {
            scheduled.add(new HashSet<>());
        }
        AtomicBoolean scheduling = new AtomicBoolean(true);
        AtomicInteger day = new AtomicInteger();
        while (scheduling.get()) {
            int countAvalible = 0;
            ActionMenu selection = new ActionMenu();
            for (Employee employee : employees) {
                if (employee.isAvalible(Day.fromIndex(day.get()))) {
                    countAvalible++;
                    if (scheduled.get(day.get()).contains(employee)) {
                        selection.add("Unschedule #" + employee.getId() + " " + employee.getName(), (ActionMenu parent) -> {
                            scheduled.get(day.get()).remove(employee);
                            parent.stop();
                        });
                    } else {
                        selection.add("Schedule #" + employee.getId() + " " + employee.getName(), (ActionMenu parent) -> {
                            scheduled.get(day.get()).add(employee);
                            parent.stop();
                        });
                    }
                }
            }
            if (day.get() != 0) {
                selection.add("Go Back", (ActionMenu parent) -> {
                    day.getAndDecrement();
                    parent.stop();
                });
            }
            if (scheduled.get(day.get()).size() >= minWorking || scheduled.get(day.get()).size() == countAvalible) {
                if (day.get() < 6) {
                    selection.add("Next", (ActionMenu parent) -> {
                        day.getAndIncrement();
                        parent.stop();
                    });
                } else {
                    selection.add("Finish", (ActionMenu parent) -> {
                        scheduling.set(false);
                        parent.stop();
                    });
                }
            }
            System.out.print("\n" + Day.fromIndex(day.get()).name());
            selection.run(reader, System.out);
        }
        return scheduled;
    }
}