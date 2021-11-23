import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class FormInfo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormInfo frame = new FormInfo();
					frame.setVisible(true);
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormInfo() {
		Image icon = Toolkit.getDefaultToolkit().getImage("icon/Logo_Group_MRL.png");
        setIconImage(icon);
		setBounds(100, 100, 322, 257);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new TitledBorder(null, "Thông tin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		JTextArea txtInfo = new JTextArea();
		txtInfo.setEditable(false);
		txtInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		txtInfo.setBackground(SystemColor.menu);
		txtInfo.setText("Ứng dụng mô phỏng thuật toán sắp xếp\nTrường Đại học Đà Lạt\nKhoa Công nghệ Thông tin\nLớp : CTK43\n Võ Đình Hoàng Long - 1911164\n Võ Công Lý - 1911166\n Nguyễn Hoàng Đăng Khoa - 1911158\nĐịa chỉ liên lạc: \n longhoangdazai@gmail.com\n minoitnk@gmail.com\n vocongly0504@gmail.com");
		contentPane.add(txtInfo);
	}
}
