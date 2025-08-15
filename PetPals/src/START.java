import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class START {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new START();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public START() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton singup_btn = new JButton("SignUp");
		singup_btn.setFont(new Font("Sylfaen", Font.BOLD, 25));
		singup_btn.setBounds(591, 186, 152, 51);
		frame.getContentPane().add(singup_btn);
		
		JButton login_btn = new JButton("Login");
		login_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new login();
				frame.dispose(); //  present frame will be closed
			}
		});
		login_btn.setFont(new Font("Sylfaen", Font.BOLD, 25));
		login_btn.setBounds(323, 186, 152, 51);
		frame.getContentPane().add(login_btn);
		
		JLabel welcome = new JLabel("Welcome To PetPals");
		welcome.setFont(new Font("Tahoma", Font.BOLD, 40));
		welcome.setBounds(323, 38, 420, 51);
		frame.getContentPane().add(welcome);
		
		JLabel start_bg = new JLabel("");
		start_bg.setIcon(new ImageIcon("E:\\JAVA\\PetPals\\image\\pet_bg.jpg"));
		start_bg.setBounds(-57, -38, 1121, 499);
		frame.getContentPane().add(start_bg);
		frame.setVisible(true);
	}
}
