import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GUIMenu adds a graphical interface to ActionMenu
 *
 * GUIMenu is a decorator over ActionMenu
 * Commands are executed every time a JButton is pressed.
 * The run method contains a JFrame, which is a mediator between the program and all the widgets it uses.
 *
 * @author  Aidan Edwards
 */
public class GUIMenu extends ActionMenu {
    String title = null;

    public void run(){
        running.set(true);
        while(running.get()){
            JFrame frame;
            if(title == null){
                frame = new JFrame();
            }else{
                frame = new JFrame(title);
            }
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            GUIMenu parent = this;
            int height = 40;
            AtomicInteger clicked = new AtomicInteger(0);
            for(int i = 0; i < actions.size(); i++){
                JButton button = new JButton(descriptions.get(i));
                button.setBounds(40, height, 200, 40);
                int finalI = i;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        //Perform function when button is pressed
                        clicked.set(finalI);
                        synchronized (GUIMenu.this){
                            GUIMenu.this.notifyAll();
                        }
                    }
                });
                frame.add(button);
                height += 80;
            }
            frame.setSize(200 + 80, height + 40);
            frame.setLayout(null);
            frame.setVisible(true);
            try {
                synchronized (this){
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.dispose();
            //int n = in.nextInt() - 1;
            if(clicked.get() > 0 && clicked.get() < actions.size()){
                actions.get(clicked.get()).run(this);
            }
        }
    }

    public void setTitle(String titleIn) {
        title = titleIn;
    }
}
