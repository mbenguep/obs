package Bank;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Login extends JFrame {

	private JFrame loginFrame;
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JLabel lblNewLabel;

	public Login() {
		loginFrame = new JFrame("OOP Bank System");
		loginFrame.setResizable(false);
		loginFrame.setBounds(300, 300, 700, 700);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(null);
		loginFrame.setVisible(true);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Can't set look and feel");
		}

		JLabel userIdText = new JLabel("        CUSTOMER ID");
		userIdText.setBounds(90, 70, 400, 200);
		loginFrame.getContentPane().add(userIdText);

		JLabel passwordText = new JLabel("        PASSWORD");
		passwordText.setBounds(90, 225, 288, 100);
		loginFrame.getContentPane().add(passwordText);

		usernameBox = new JTextField();
		usernameBox.setBounds(244, 145, 300, 50);
		loginFrame.getContentPane().add(usernameBox);
		usernameBox.setColumns(10);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int userId = Integer.parseInt(usernameBox.getText());
				@SuppressWarnings("deprecation")
				String password = passwordBox.getText();

				String sqlUser = "root";
				String sqlPassword = "";
				String url = "jdbc:mysql://localhost:3306/oop?autoReconnect=true&useSSL=false";

				try {

					Connection con = DriverManager.getConnection(url, sqlUser, sqlPassword);

					PreparedStatement st = (PreparedStatement) con.prepareStatement(
							"Select customerId, " + "password from user where customerId=? and password=?");

					st.setLong(1, userId);
					st.setString(2, password);
					ResultSet rs = st.executeQuery();
					
					if (rs.next()) {

						// Show pop message to the user
						System.out.println("Welcome to OOP Bank System");
						JOptionPane.showMessageDialog(null, "You have successfully logged in");
						
						loginFrame.dispose();

						new BankWindow();

					} else {
						JOptionPane.showMessageDialog(null, "Wrong Username & Password");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				//

			}
		});
		loginButton.setBounds(300, 350, 92, 35);
		loginFrame.getContentPane().add(loginButton);

		passwordBox = new JPasswordField();
		passwordBox.setBounds(244, 250, 300, 50);
		loginFrame.getContentPane().add(passwordBox);

		lblNewLabel = new JLabel("OOP BANK SYSTEM LOGIN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblNewLabel.setBounds(175, 20, 700, 50);
		loginFrame.getContentPane().add(lblNewLabel);

	}

}
