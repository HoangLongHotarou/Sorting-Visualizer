import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
    private static final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 80;
    private JLabel[] buttons;
    private int number = 7;
    private SortButtonListener listener;
    public boolean state;

    public void setState(boolean state) {
        this.state = state;
    }

    public ButtonPanel(SortButtonListener listener){
        super();
        this.state = true;
        this.listener = listener;

        buttons = new JLabel[number];
        for(int i = 0;i<buttons.length;i++){
            buttons[i] = new JLabel();
        }
        initButtons(buttons[0],"create_button",0);
        initButtons(buttons[1],"bubble_button",1);
        initButtons(buttons[2],"selection_button",2);
        initButtons(buttons[3],"insertion_button",3);
        initButtons(buttons[4],"quick_button",4);
        initButtons(buttons[5],"merge_button",5);
        initButtons(buttons[6],"restore_button",6);

        setLayout(null);
        for(int i = 0; i<buttons.length; i++){
            buttons[i].setBounds(20, 20+(BUTTON_HEIGHT+5)*i, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(buttons[i]);
        }
    }

    private void initButtons(JLabel button, String name, int id) {
        ImageIcon imgButton = new ImageIcon(String.format("buttons/%s.png",name));
        button.setIcon(imgButton);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(state==false)return;
                button.setIcon(new ImageIcon(String.format("buttons/%s_pressed.png", name)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(state==false)return;
                listener.sortButtonListener(id);
                button.setIcon(new ImageIcon(String.format("buttons/%s_entered.png",name)));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(state==false)return;
                button.setIcon(new ImageIcon(String.format("buttons/%s_entered.png",name)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(state==false)return;
                button.setIcon(new ImageIcon(String.format("buttons/%s.png",name)));
            }
        });
    }

    public interface SortButtonListener {
        void sortButtonListener(int id);
    }
}
