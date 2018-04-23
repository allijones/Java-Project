import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestActionMenu {
    @Test
    void testOne(){
        List<Integer> order = new ArrayList<>();
        ActionMenu testMenu = new ActionMenu();
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
        testMenu.run(new Scanner("4 2 3 1 4 4 5"));
        int[] verify = {4, 2, 3, 1, 4, 4};
        Assertions.assertArrayEquals(order.stream().mapToInt(i->i).toArray(), verify);
    }
}
