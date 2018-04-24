/**
 * The Main class ...
 *
 * @author Aidan Edwards
 *
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A program that can load employee schedule data, then save it in other formats
 * and build a work schedule out of it.
 *
 * @author Aidan Edwards
 */
public class Main {
    public static void main(String[] argv) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<Employee>();

        DBInterface dbInterface = DBInterface.create("database");
        ManipulateEmployees filter = new ManipulateEmployees(dbInterface);
        ManipulateDays available = new ManipulateDays(dbInterface);

        Scanner reader = new Scanner(System.in);

        AtomicBoolean useGUI = new AtomicBoolean(false);

        ActionMenu uiSelect = new ActionMenu();
        uiSelect.add("Use Terminal", (ActionMenu parent) -> {
            useGUI.set(false);
            parent.stop();
        });
        uiSelect.add("Use GUI", (ActionMenu parent) -> {
            useGUI.set(true);
            parent.stop();
        });

        uiSelect.run(reader, System.out);

        ActionMenu saveMenu;
        if (useGUI.get()) {
            saveMenu = new GUIMenu();
        } else {
            saveMenu = new ActionMenu();
        }
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
            PrintWriter writer = getPrintWriter(reader, useGUI);
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
        saveMenu.add("Cancel", ActionMenu::stop);


        ActionMenu loadMenu;
        if (useGUI.get()) {
            loadMenu = new GUIMenu();
        } else {
            loadMenu = new ActionMenu();
        }
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
                String filename = getCSVSource(reader, useGUI);;
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

        ActionMenu mainMenu;
        if (useGUI.get()) {
            mainMenu = new GUIMenu();
        } else {
            mainMenu = new ActionMenu();
        }
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
        mainMenu.add("Save Data", (ActionMenu) -> {
            if (useGUI.get()) {
                ((GUIMenu)saveMenu).run();
            } else {
                saveMenu.run(reader, System.out);
            }
        });
        mainMenu.add("Load Data", (ActionMenu) -> {
            if (useGUI.get()) {
                ((GUIMenu)loadMenu).run();
            } else {
                loadMenu.run(reader, System.out);
            }
        });
        mainMenu.add("Clear Loaded Data", (ActionMenu) -> {
            employees.clear();
        });
        mainMenu.add("Build Schedule", (ActionMenu) -> {
            List<Set<Employee>> scheduled = Main.buildSchedule(reader, employees, 3, useGUI);
            PrintWriter writer = null;
            writer = getPrintWriter(reader, useGUI);
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
            String infilename = getCSVSource(reader, useGUI);
            try {
                Scanner csvIn = new Scanner(new File(infilename));
                String[] lines = new String[7];
                for (int i = 0; i < 6; i++) {
                    lines[i] = csvIn.nextLine();
                }
                List<Set<Employee>> scheduled = Main.buildSchedule(reader, employees, 0, useGUI);
                PrintWriter writer = null;
                writer = getPrintWriter(reader, useGUI);
                for (int i = 0; i < 6; i++) {
                    writer.print(lines[i]);
                    for (Employee employee : scheduled.get(i)) {
                        writer.print("," + employee.getId() + "," + employee.getName());
                    }
                    writer.println();
                }
                writer.close();
            } catch (FileNotFoundException e) {
                if(useGUI.get()){
                    JOptionPane.showMessageDialog(null, "Invalid path.");
                }else {
                    System.out.println("Invalid path.");
                }
            }
        });
        mainMenu.add("Exit", (ActionMenu parent) -> {
            parent.stop();
            System.out.println("\nThank you for using our software.");
        });

        if (useGUI.get()) {
            ((GUIMenu)loadMenu).run();
        } else {
            loadMenu.run(reader, System.out);
        }

        loadMenu.add("Cancel", ActionMenu::stop);

        if (useGUI.get()) {
            ((GUIMenu)mainMenu).run();
        } else {
            mainMenu.run(reader, System.out);
        }

        dbInterface.disconnect();
    }

    private static PrintWriter getPrintWriter(Scanner reader, AtomicBoolean useGUI) {
        String filename;
        PrintWriter writer = null;
        if(useGUI.get()){
            filename = JOptionPane.showInputDialog("Enter a name for the CSV file:");
        }else{
            System.out.print("\nEnter a name for the CSV file: ");
            filename = reader.next();
        }
        while (writer == null) {
            try {
                writer = new PrintWriter(filename, "UTF-8");
            } catch (FileNotFoundException e) {
                if(useGUI.get()){
                    filename = JOptionPane.showInputDialog("Invalid path, try again: ");
                }else{
                    System.out.print("Invalid path, try again: ");
                    filename = reader.next();

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    public static String getCSVSource(Scanner reader, AtomicBoolean useGUI){
        String infilename;
        if(useGUI.get()){
            infilename = JOptionPane.showInputDialog("Path to source CSV file:");
        }else{
            System.out.print("Path to source CSV file: ");
            infilename = reader.next();
        }
        return infilename;
    }

    private static List<Set<Employee>> buildSchedule(Scanner reader, ArrayList<Employee> employees, int minWorking, AtomicBoolean useGUI) {
        List<Set<Employee>> scheduled = new ArrayList<>();
        for (int d = 0; d < 7; d++) {
            scheduled.add(new HashSet<>());
        }
        AtomicBoolean scheduling = new AtomicBoolean(true);
        AtomicInteger day = new AtomicInteger();
        while (scheduling.get()) {
            int countAvalible = 0;
            ActionMenu selection;
            if (useGUI.get()) {
                selection = new GUIMenu();
            } else {
                selection = new ActionMenu();
            }
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
            if (useGUI.get()) {
                ((GUIMenu)selection).setTitle(Day.fromIndex(day.get()).name());
            } else {
                System.out.print("\n" + Day.fromIndex(day.get()).name());
            }
            if (useGUI.get()) {
                ((GUIMenu)selection).run();
            } else {
                selection.run(reader, System.out);
            }
        }
        return scheduled;
    }
}