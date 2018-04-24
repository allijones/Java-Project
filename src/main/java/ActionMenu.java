/**
 * The action interface ...
 *
 * @author Aidan Edwards
 *
 */

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

interface action {
    void run(ActionMenu parent);
}

/**
 * The ActionMenu class ...
 *
 * @author Aidan Edwards
 *
 */
public class ActionMenu {
    private List<action> actions = new ArrayList<action>();
    private List<String> descriptions = new ArrayList<String>();
    private AtomicReference<Boolean> running = new AtomicReference<>(false);

    public void run(Scanner in, PrintStream out){
        running.set(true);
        out.println();
        while(running.get()){
            for(int i = 0; i < actions.size(); i++){
                out.println((i + 1) + ". " + descriptions.get(i));
            }
            int n = in.nextInt() - 1;
            actions.get(n).run(this);
        }
    }

    public void stop(){
        running.set(false);
    }

    public void add(String description, action a) {
        actions.add(a);
        descriptions.add(description);
    }
}
