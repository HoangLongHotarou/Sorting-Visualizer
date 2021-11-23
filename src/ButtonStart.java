import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonStart extends JLabel implements MouseListener {
    private static final int BUTTON_WIDTH = 80, BUTTON_HEIGHT = 80;
    private ButtonStartListener listener;
    private String btnName;
    private int id;
  

    public void setBtnNameID(String name, int id) {
        this.btnName = name;
        this.id = id;
        this.setIcon(new ImageIcon(String.format("buttons/%s.png", this.btnName)));
    }

    public ButtonStart(ButtonStartListener listener, String btnName, int id) {
        super();
        this.listener = listener;
        this.btnName = btnName;
        this.id = id;
        setBounds(530, 640, BUTTON_WIDTH, BUTTON_HEIGHT);
        ImageIcon imgButton = new ImageIcon(String.format("buttons/%s.png", btnName));
        this.setIcon(imgButton);
            this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setIcon(new ImageIcon(String.format("buttons/%s_pressed.png", btnName)));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        listener.buttonStartListener(id);
        this.setIcon(new ImageIcon(String.format("buttons/%s_entered.png", btnName)));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setIcon(new ImageIcon(String.format("buttons/%s_entered.png", btnName)));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setIcon(new ImageIcon(String.format("buttons/%s.png", btnName)));
    }

    public interface ButtonStartListener {
        void buttonStartListener(int id);
    }
}