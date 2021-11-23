import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;

public class App extends JFrame implements ButtonPanel.SortButtonListener, MyCanvas.VisualizerProvider,
        Visualizer.SortedListener, Visualizer.EnableListener, ButtonStart.ButtonStartListener, ChangeListener {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    private final int WIDTH = 1370, HEIGHT = 768;
    private JPanel mainPanel, sliderPanel;
    private ButtonPanel buttonPanel;
    private MyCanvas canvas;
    private Visualizer visualizer;
    private PseudoCode pseudoCode;
    private ButtonStart start;
    private int select = 0;
    private JLabel lbTitle, lbBar, lbVisualizer, lbnumBar, lbFPS;
    private JSlider sldNumBar, sldFPS;
    private JButton btnInfo;

    public App() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Image icon = Toolkit.getDefaultToolkit().getImage("icon/logosort.png");
        setIconImage(icon);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Mô phỏng thuật toán sắp xếp");
        initialize();
    }

    private void initialize() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(ColorManager.BACKGROUND);
        add(mainPanel);

        // add Label Visualizer
        lbVisualizer = new JLabel("Mã lệnh");
        lbVisualizer.setBounds(940, 120, 200, 50);
        lbVisualizer.setForeground(ColorManager.TEXT_WHITE);
        lbVisualizer.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lbVisualizer);

        // add Label Bar
        lbBar = new JLabel("Mô phỏng thuật toán");
        lbBar.setBounds(250, 120, 500, 50);
        lbBar.setForeground(ColorManager.TEXT_WHITE);
        lbBar.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lbBar);

        // add Label Title
        lbTitle = new JLabel("ỨNG DỤNG MÔ PHỎNG THUẬT TOÁN SẮP XẾP", JLabel.CENTER);
        lbTitle.setBounds(0, 20, WIDTH, 50);
        lbTitle.setForeground(ColorManager.TEXT_WHITE);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(lbTitle);

        // add Button Panel
        buttonPanel = new ButtonPanel(this);
        buttonPanel.setBounds(0, 100, 250, HEIGHT);
        buttonPanel.setBackground(ColorManager.BACKGROUND);
        mainPanel.add(buttonPanel);

        start = new ButtonStart(this, "run_button", 0);
        mainPanel.add(start);

        // add Canvas Panel
        canvas = new MyCanvas(this);
        int cWidth = 650;
        int cHeight = 450;
        canvas.setFocusable(false);
        canvas.setPreferredSize(new Dimension(cWidth, cHeight));
        canvas.setBounds(250, 170, cWidth, cHeight);
        mainPanel.add(canvas);
        // pack();

        visualizer = new Visualizer(10, 10, this, this);

        pseudoCode = visualizer.pseudoCode;
        pseudoCode.setBounds(940, 170, 400, 450);
        mainPanel.add(pseudoCode);

        // Slider Panel
        sliderPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        sliderPanel.setLayout(flowLayout);
        sliderPanel.setBounds(10, 10, 250, 500);
        sliderPanel.setBackground(ColorManager.BACKGROUND);
        mainPanel.add(sliderPanel);

        // lbNumBar
        lbnumBar = new JLabel("Số cột: 10");
        lbnumBar.setForeground(ColorManager.TEXT_WHITE);
        lbnumBar.setFont(new Font("Arial", Font.BOLD, 18));
        sliderPanel.add(lbnumBar);

        // NumBarSlider
        sldNumBar = new JSlider(JSlider.HORIZONTAL, 10, 25, 10);
        sldNumBar.setBackground(ColorManager.BACKGROUND);
        sldNumBar.addChangeListener(this);
        sliderPanel.add(sldNumBar);

        // lbFPS
        lbFPS = new JLabel("Tốc độ: 10");
        lbFPS.setForeground(ColorManager.TEXT_WHITE);
        lbFPS.setFont(new Font("Arial", Font.BOLD, 18));
        sliderPanel.add(lbFPS);

        // numFPS
        sldFPS = new JSlider(JSlider.HORIZONTAL, 10, 500, 10);
        sldFPS.setBackground(ColorManager.BACKGROUND);
        sldFPS.addChangeListener(this);
        sliderPanel.add(sldFPS);

        // btnInfo
        btnInfo = new JButton();
        btnInfo.setBackground(ColorManager.BAR_WHITE);
        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormInfo form = new FormInfo();
                form.setVisible(true);
            }
        });
        btnInfo.setBackground(ColorManager.BAR_WHITE);
        btnInfo.setBounds(1280, 15, 60, 60);
        btnInfo.setIcon(new ImageIcon(String.format("buttons/info.png", "info")));
        mainPanel.add(btnInfo);
    }

    private boolean Start() {
        boolean check = true;
        state(0);
        switch (select) {
        case 0:
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sort", "", JOptionPane.WARNING_MESSAGE);
            state(1);
            check = false;
            break;
        case 1:
            visualizer.bubbleSort();
            break;
        case 2:
            visualizer.selectionSort();
            break;
        case 3:
            visualizer.insertionSort();
            break;
        case 4:
            visualizer.quickSort();
            break;
        case 5:
            visualizer.mergeSort();
            break;
        }
        return check;
    }

    @Override
    public void sortButtonListener(int id) {
        switch (id) {
        case 0:
            System.out.println("Create");
            visualizer.createArray(canvas.getWidth(), canvas.getHeight(),false);
            break;
        case 1:
            System.out.println("bubble");
            pseudoCode.ReadBubble();
            select = 1;
            break;
        case 2:
            System.out.println("select");
            pseudoCode.ReadSelection();
            select = 2;
            break;
        case 3:
            System.out.println("insert");
            pseudoCode.ReadInsertion();
            select = 3;
            break;
        case 4:
            System.out.println("quick");
            pseudoCode.ReadQuick();
            select = 4;
            break;
        case 5:
            System.out.println("merge");
            pseudoCode.ReadMerge();
            select = 5;
            break;
        case 6:
            if(visualizer.temp[0]==null){
                JOptionPane.showMessageDialog(null, "Không có mảng để phục hồi", "", JOptionPane.WARNING_MESSAGE);
            }else
                visualizer.createArray(canvas.getWidth(), canvas.getHeight(), true);
        default:
            break;
        }
    }

    @Override
    public void onDrawArray() {
        if (visualizer != null)
            visualizer.drawArray();
    }

    @Override
    public BufferStrategy getBufferStrategy() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }
        return bs;
    }

    @Override
    public void buttonStartListener(int id) {
        switch (id) {
        case 0:
            System.out.println("Start");
            boolean check = Start();
            boolean check1 = visualizer.hasArray;
            if (check&&check1)
                start.setBtnNameID("stop_button", 1);
            break;
        case 1:
            System.out.println("Stop");
            visualizer.interupt();
            //visualizer.resetArray(canvas);
            pseudoCode.reset();
            select = 0;
            start.setBtnNameID("run_button", 0);
            break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int numBar = sldNumBar.getValue();
        int numFPS = sldFPS.getValue();
        lbnumBar.setText("Số cột: " + numBar);
        lbFPS.setText("Tốc độ: " + numFPS);
        visualizer.setCapacity(numBar);
        visualizer.setFPS(numFPS);
    }

    private void state(int i) {
        switch (i) {
        case 0:
            sldNumBar.setEnabled(false);
            sldFPS.setEnabled(false);
            // buttonPanel.init(false);
            buttonPanel.setState(false);
            break;
        case 1:
            // buttonPanel.init(true);
            buttonPanel.setState(true);
            sldNumBar.setEnabled(true);
            sldFPS.setEnabled(true);
            //select = 0;
            start.setBtnNameID("run_button", 0);
            break;
        }
    }

    @Override
    public void setEnabled(int i) {
        state(i);
    }
}
