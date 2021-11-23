import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class PseudoCode extends JScrollPane{

    private DefaultListModel<String> model;
    protected JList<String> lsCode;
    
    public PseudoCode() {
        init();
    }

    private void init() {
        model = new DefaultListModel<>();
        lsCode = new JList<String>(model);
        lsCode.setBorder(new LineBorder(new Color(0, 0, 0)));
		lsCode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsCode.setFont(new Font("Monospaced",Font.BOLD,14));
        lsCode.setBackground(ColorManager.CANVAS_BACKGROUND);
        lsCode.setForeground(Color.WHITE);
        lsCode.setSelectedIndex(2);
		this.setViewportView(lsCode);
    }

    public void reset(){
        init();
    }

    public void ReadBubble(){
        init();
        try {
            File myObj = new File("fileTXT/bubble.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                model.addElement(myReader.nextLine());
            }
            myReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR","Error read file",JOptionPane.ERROR);
        }
    }

    public void ReadSelection(){
        init();
        try {
            File myObj = new File("fileTXT/selection.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                model.addElement(myReader.nextLine());
            }
            myReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR","Error read file",JOptionPane.ERROR);
        }
    }

    public void ReadInsertion(){
        init();
        try {
            File myObj = new File("fileTXT/insertion.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                model.addElement(myReader.nextLine());
            }
            myReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR","Error read file",JOptionPane.ERROR);
        }
    }
    public void ReadQuick(){
        init();
        try {
            File myObj = new File("fileTXT/quick.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                model.addElement(myReader.nextLine());
            }
            myReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR","Error read file",JOptionPane.ERROR);
        }
    }

    public void ReadMerge(){
        init();
        try {
            File myObj = new File("fileTXT/merge.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                model.addElement(myReader.nextLine());
            }
            myReader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR","Error read file",JOptionPane.ERROR);
        }
    }
}
