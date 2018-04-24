import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestActionMenu {
    private static List<Integer> order;
    private static ActionMenu testMenu;
    @BeforeAll
    static void setUp(){
        order = new ArrayList<>();
        testMenu = new ActionMenu();
        testMenu.add("Action 1", (ActionMenu)->{
            order.add(1);
        });
        testMenu.add("Action 2", (ActionMenu)->{
            order.add(2);
        });
        testMenu.add("Action 3", (ActionMenu)->{
            order.add(3);
        });
        testMenu.add("Action 4", (ActionMenu)->{
            order.add(4);
        });
        testMenu.add("Cancel Action", ActionMenu::stop);

    }
    @Test
    void testOne(){
        testMenu.run(new Scanner("4 2 3 1 4 4 5"), new NullPrintStream());
        int[] verify = {4, 2, 3, 1, 4, 4};
        Assertions.assertArrayEquals(verify, order.stream().mapToInt(i->i).toArray());
        order.clear();
    }
    @Test
    void testTwo(){
        testMenu.run(new Scanner("1 2 3 4 3 2 1 2 3 2 1 1 1 2 3 4 3 4 3 3 3 5"), new NullPrintStream());
        int[] verify = {1, 2, 3, 4, 3, 2, 1, 2, 3, 2, 1, 1, 1, 2, 3, 4, 3, 4, 3, 3, 3};
        Assertions.assertArrayEquals(verify, order.stream().mapToInt(i->i).toArray());
        order.clear();
    }
    @Test
    void testThree(){
        testMenu.add("Action 6", (ActionMenu)->{
            order.add(6);
        });
        testMenu.run(new Scanner("1 2 6 3 4 1 2 6 3 2 4 6 1 2 3 6 1 2 3 4 5"), new NullPrintStream());
        int[] verify = {1, 2, 6, 3, 4, 1, 2, 6, 3, 2, 4, 6, 1, 2, 3, 6, 1, 2, 3, 4};
        Assertions.assertArrayEquals(verify, order.stream().mapToInt(i->i).toArray());
        order.clear();
    }
}
