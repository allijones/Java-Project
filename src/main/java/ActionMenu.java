import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

interface action {
    void run(ActionMenu parent);
}

public class ActionMenu {
    private List<action> actions = new ArrayList<action>();
    private List<String> descriptions = new ArrayList<String>();
    private AtomicReference<Boolean> running = new AtomicReference<>(false);

    public void run(Scanner in){
        running.set(true);
        System.out.println();
        while(running.get()){
            for(int i = 0; i < actions.size(); i++){
                System.out.println((i + 1) + ". " + descriptions.get(i));
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
