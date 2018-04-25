import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The action interface represents a lambda
 *
 * @author Aidan Edwards
 *
 */
interface action {
    void run(ActionMenu parent);
}

/**
 * A manager for a menu dynamically generated with lambdas.
 *
 * A Command is executed every time an option is picked.
 * An ActionMenu object is a State that lasts from when `run` is called to when `stop` is called.
 *
 * @author  Aidan Edwards
 */
public class ActionMenu {
    protected List<action> actions = new ArrayList<action>();
    protected List<String> descriptions = new ArrayList<String>();
    protected AtomicReference<Boolean> running = new AtomicReference<>(false);

    public void run(Scanner in, PrintStream out){
        running.set(true);
        while(running.get()){
            out.println();
            for(int i = 0; i < actions.size(); i++){
                out.println((i + 1) + ". " + descriptions.get(i));
            }
            out.print("Please select an option: ");
            int n = in.nextInt() - 1;
            if(n >= 0 && n < actions.size()){
                actions.get(n).run(this);
            }
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
