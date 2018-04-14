import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import java.sql.*;

public class Main {
    public static void main(String[] argv) throws SQLException {
        DatabaseConnection myConnection = null;
        myConnection.createConnection();
        CreateTable newTable = null;
        newTable.createTable();
        CreateDaysTable newTable2 = null;
        newTable2.createTable();
        //EmployeeGateway manip = new EmployeeGateway();
        Employee employee1 = new Employee();

        employee1.setName("Alli");
        employee1.setId(0);
        ManipulateEmployees filter = new ManipulateEmployees();

        //for some reason this is giving me an error that says
        //the employees table does not exist... not sure why
        filter.save(employee1);
    }
}
