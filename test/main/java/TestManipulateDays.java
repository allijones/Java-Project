import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests ManipulateDays
 *
 * @author  Aidan Edwards
 */
class TestManipulateDays {
    static DBInterface dbInterface;
    static ManipulateDays mDays;

    @BeforeAll
    static void setUp() {
        dbInterface = DBInterface.create("testdaysdatabase");
        mDays = new ManipulateDays(dbInterface);
        try {
            mDays.delete();
            mDays.save(EmployeeFactory.create("Alli", new boolean[]{false, true, true, true, false, true, false}, 1));
            mDays.save(EmployeeFactory.create("Aidan", new boolean[]{false, true, true, true, false, false, true}, 2));
            mDays.save(EmployeeFactory.create("Bob", new boolean[]{true, false, true, false, true, true, true}, 3));
            mDays.save(EmployeeFactory.create("Sarah", new boolean[]{false, false, true, false, false, false, true}, 4));
            mDays.save(EmployeeFactory.create("Kelly", new boolean[]{true, true, true, false, false, false, true}, 5));
            mDays.save(EmployeeFactory.create("Joe", new boolean[]{true, true, true, true, false, false, false}, 6));
            mDays.save(EmployeeFactory.create("Cerny", new boolean[]{true, true, true, true, true, true, true}, 7));
            mDays.save(EmployeeFactory.create("Rachel", new boolean[]{true, true, true, false, true, true, true}, 8));
            mDays.save(EmployeeFactory.create("Billy", new boolean[]{false, true, false, true, true, true, true}, 9));
            mDays.save(EmployeeFactory.create("Sam", new boolean[]{false, false, false, true, false, false, false}, 10));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        dbInterface.disconnect();
    }

    @Test
    void testOne(){
        try {
            List<Integer> list = mDays.availableDay(Day.SUNDAY);
            Assertions.assertArrayEquals(new int[]{3, 5, 6, 7, 8}, list.stream().mapToInt(i->i).toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }

    @Test
    void testTwo(){
        try {
            List<Integer> list = mDays.availableDay(Day.WEDNESDAY);
            Assertions.assertArrayEquals(new int[]{1, 2, 6, 7, 9, 10}, list.stream().mapToInt(i->i).toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }

    @Test
    void testThree(){
        try {
            List<Integer> list = mDays.availableDay(Day.FRIDAY);
            Assertions.assertArrayEquals(new int[]{1, 3, 7, 8, 9}, list.stream().mapToInt(i->i).toArray());
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.assertFalse(true);
        }
    }
}