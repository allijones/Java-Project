import java.sql.SQLException;
import java.util.Scanner;

public class Functionality {

    public static void interact() throws SQLException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter '1' to create a new schedule, enter '2' to update an existing schedule.");
        int n = reader.nextInt();

        if(n == 1){
            ManipulateDays available = new ManipulateDays();
            ManipulateEmployees filter = new ManipulateEmployees(DBInterface.create());
            available.availableMonday();
            for(int i = 0; i < available.mondayList.size(); i++){
                filter.findById(available.mondayList.get(i));
            }
            System.out.println("Please enter 3 ID numbers to work on Monday: ");
            int a = reader.nextInt();
            int b = reader.nextInt();
            int c = reader.nextInt();

            //send names out to the CSV file
        }
        else{

        }
    }
}
