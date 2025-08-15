import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class login {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel if_not_signup_lbl = new JLabel("If not sign up already, then");
		if_not_signup_lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		if_not_signup_lbl.setBounds(157, 301, 177, 27);
		frame.getContentPane().add(if_not_signup_lbl);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(171, 141, 232, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton singup_btn = new JButton("SignUp");
		singup_btn.setFont(new Font("Century Schoolbook", Font.BOLD, 13));
		singup_btn.setBounds(344, 301, 85, 27);
		frame.getContentPane().add(singup_btn);
		
		JButton login_btn = new JButton("Login");
		login_btn.setForeground(new Color(255, 255, 255));
		login_btn.setBackground(new Color(0, 128, 128));
		login_btn.setFont(new Font("Century Schoolbook", Font.BOLD, 25));
		login_btn.setBounds(332, 358, 152, 51);
		frame.getContentPane().add(login_btn);
		
		JLabel welcome = new JLabel("Login Page");
		welcome.setFont(new Font("Tahoma", Font.BOLD, 40));
		welcome.setBounds(171, 38, 222, 51);
		frame.getContentPane().add(welcome);
		
		JLabel username_lbl = new JLabel("Username");
		username_lbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		username_lbl.setBounds(171, 100, 232, 41);
		frame.getContentPane().add(username_lbl);
		
		JLabel pass_lbl = new JLabel("Password");
		pass_lbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		pass_lbl.setBounds(171, 198, 232, 41);
		frame.getContentPane().add(pass_lbl);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(171, 238, 232, 41);
		frame.getContentPane().add(passwordField);
		
		JButton back_btn = new JButton("Back");
		back_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new START();
				frame.dispose();
			}
		});
		back_btn.setForeground(Color.BLACK);
		back_btn.setFont(new Font("Century Schoolbook", Font.BOLD, 25));
		back_btn.setBackground(new Color(255, 128, 128));
		back_btn.setBounds(131, 358, 152, 51);
		frame.getContentPane().add(back_btn);
		
		JLabel start_bg = new JLabel("");
		start_bg.setFont(new Font("Tahoma", Font.PLAIN, 18));
		start_bg.setIcon(new ImageIcon("E:\\JAVA\\PetPals\\image\\pet_bg.jpg"));
		start_bg.setBounds(-124, -38, 748, 499);
		frame.getContentPane().add(start_bg);
		frame.setVisible(true);
	}
}
