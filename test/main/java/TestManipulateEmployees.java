import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

/**
 * Tests ManipulateEmployees
 *
 * @author  Aidan Edwards
 */
class TestManipulateEmployees {
    static DBInterface dbInterface;
    static ManipulateEmployees mEmployees;

    @BeforeAll
    static void setUp() {
        dbInterface = DBInterface.create("testemployeesdatabase");
        mEmployees = new ManipulateEmployees(dbInterface);
        try {
            mEmployees.delete();
            mEmployees.save(EmployeeFactory.create("Alli", new boolean[]{false, true, true, true, false, true, false}));
            mEmployees.save(EmployeeFactory.create("Aidan", new boolean[]{false, true, true, true, false, false, true}));
            mEmployees.save(EmployeeFactory.create("Bob", new boolean[]{true, false, true, false, true, true, true}));
            mEmployees.save(EmployeeFactory.create("Sarah", new boolean[]{false, false, true, false, false, false, true}));
            mEmployees.save(EmployeeFactory.create("Kelly", new boolean[]{true, true, true, false, false, false, true}));
            mEmployees.save(EmployeeFactory.create("Joe", new boolean[]{true, true, true, true, false, false, false}));
            mEmployees.save(EmployeeFactory.create("Cerny", new boolean[]{true, true, true, true, true, true, true}));
            mEmployees.save(EmployeeFactory.create("Rachel", new boolean[]{true, true, true, false, true, true, true}));
            mEmployees.save(EmployeeFactory.create("Billy", new boolean[]{false, true, false, true, true, true, true}));
            mEmployees.save(EmployeeFactory.create("Sam", new boolean[]{false, false, false, true, false, false, false}));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        dbInterface.disconnect();
    }

    @Test
    void count() {
        try {
            Assertions.assertEquals(10, mEmployees.count());
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }

    @Test
    void loadAll(){
        try {
            Map<Integer, String> loaded = mEmployees.loadAll();
            for(String name : loaded.values()){
                Assertions.assertTrue(Arrays.asList(new String[]{"Alli", "Aidan", "Bob", "Sarah", "Kelly", "Joe", "Cerny", "Rachel", "Billy", "Sam"}).contains(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}