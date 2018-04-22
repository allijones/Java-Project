import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface action {
    void actionFunction();
}

public class Functionality {

    public static void interact() throws SQLException {
        Scanner reader = new Scanner(System.in);
        boolean running = true;

        List<Pair<String, action>> actions = new ArrayList<Pair<String, action>>();
        actions.add(new Pair<>("Read Schedule", () -> {

        }));
        actions.add(new Pair<>("Modify Schedule", () -> {

        }));
        actions.add(new Pair<>("Save Schedule", () -> {

        }));
        actions.add(new Pair<>("Load Schedule", () -> {

        }));
        actions.add(new Pair<>("Clear Schedule", () -> {

        }));
        actions.add(new Pair<>("Exit", () -> {

        }));

        while(running){
            for(int i = 0; i < actions.size(); i++){
                System.out.println((i + 1) + actions.get(i).getKey());
            }
        }
        /*
        System.out.println("Enter '1' to create a new schedule, enter '2' to update an existing schedule.");
        int n = reader.nextInt();

        if(n == 1){
            ManipulateDays available = new ManipulateDays(DBInterface.create());
            ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
            ArrayList<Integer> avalibleList = available.availableDay(Day.Monday);
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
