import java.sql.*;
import java.util.*;

public class ManipulateTables {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CONNECTION = "jdbc:derby:table1;";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    /**
     * save: inserts a new employee name into the database of names and IDs,
     * or changes an employee entry, depending on if the employee parameter has an
     * id or not.
     *
     * @param employee
     *
     * */
    public void save(Employee employee) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement pstmt;
        if(employee.getId() > 0){
            pstmt = dbConnection.prepareStatement("UPDATE EMPLOYEE SET " + "NAME = ?" + "WHERE ID = ?");
            pstmt.setInt(2, employee.getId());
        }else{
            //stmt.execute("INSERT INTO EMPLOYEE" + "(NAME, EMAIL, AGE, GENDER, SALARY)");
            pstmt = dbConnection.prepareStatement("INSERT INTO EMPLOYEE" + "(NAME)" + "VALUES" + "(?)", Statement.RETURN_GENERATED_KEYS);
        }
        pstmt.setString(1, employee.getName());
        pstmt.executeUpdate();
    }
}
