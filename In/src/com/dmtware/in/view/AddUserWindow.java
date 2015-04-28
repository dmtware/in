package com.dmtware.in.view;

import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddUserWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<User> users;

	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JTextField textFieldFirstName;
	private JTextField textFieldSurname;
	private JPasswordField passwordField2;

	public JTextField getTextFieldUserName() {
		return textFieldUserName;
	}

	public void setTextFieldUserName(JTextField textFieldUserName) {
		this.textFieldUserName = textFieldUserName;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JTextField getTextFieldFirstName() {
		return textFieldFirstName;
	}

	public void setTextFieldFirstName(JTextField textFieldFirstName) {
		this.textFieldFirstName = textFieldFirstName;
	}

	public JTextField getTextFieldSurname() {
		return textFieldSurname;
	}

	public void setTextFieldSurname(JTextField textFieldSurname) {
		this.textFieldSurname = textFieldSurname;
	}

	public JPasswordField getPasswordField2() {
		return passwordField2;
	}

	public void setPasswordField2(JPasswordField passwordField2) {
		this.passwordField2 = passwordField2;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserWindow dialog = new AddUserWindow();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public AddUserWindow() {
		
		// initialise database connection
		conn = new SQLiteCon();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AddUserWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setModal(true);
		setTitle("In - Add User");
		setBounds(100, 100, 360, 268);
		getContentPane().setLayout(null);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(164, 35, 180, 20);
		getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(164, 66, 180, 20);
		getContentPane().add(passwordField);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setBounds(164, 128, 180, 20);
		getContentPane().add(textFieldFirstName);

		textFieldSurname = new JTextField();
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(164, 159, 180, 20);
		getContentPane().add(textFieldSurname);

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				addUser();
			}
		});
		btnAddUser.setBounds(164, 191, 180, 23);
		getContentPane().add(btnAddUser);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AddUserWindow.class
				.getResource("/com/dmtware/in/view/User.png")));
		label.setBounds(10, 11, 72, 72);
		getContentPane().add(label);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(164, 97, 180, 20);
		getContentPane().add(passwordField2);

		JLabel lblUsername = new JLabel("Username*:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(87, 36, 67, 14);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password*:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(87, 68, 67, 14);
		getContentPane().add(lblPassword);

		JLabel lblReenterPassword = new JLabel("Re-enter Password*:");
		lblReenterPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReenterPassword.setBounds(34, 98, 120, 14);
		getContentPane().add(lblReenterPassword);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(34, 129, 120, 14);
		getContentPane().add(lblFirstName);

		JLabel lblSecondName = new JLabel("Second Name:");
		lblSecondName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecondName.setBounds(34, 160, 120, 14);
		getContentPane().add(lblSecondName);
		setLocationRelativeTo(null);
	}

	// adds user
	private void addUser() {

		String userName = textFieldUserName.getText().trim();
		@SuppressWarnings("deprecation")
		String password = passwordField.getText().trim();
		String firstName = textFieldFirstName.getText().trim();
		String surname = textFieldSurname.getText().trim();

		try {
			users = conn.getAllUsers();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// if not empty
		if (!emptyFields()) {
			// if password match
			if (passwordMatch()) {
				System.out.println("before try");
					
				// check if exists
				boolean userExists = false;
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getUserName().equalsIgnoreCase(userName)) {

						System.out.println("Exists " + users.get(i).getUserName()
								+ " " + userName);
						userExists = true;
						break;
					}
				}

				// if doesn't exist
				if (!userExists) {
					try {
						conn.insertUserQuery(userName, password, firstName, surname);
						System.out.println("After insert query");
						setVisible(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null,
							"This user already exists");
				}		
				
				
			} else {
				JOptionPane.showMessageDialog(null, "Password doesn't match");
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"Please fill up all required fields");
		}

	}

	// checks if empty
	@SuppressWarnings("deprecation")
	private boolean emptyFields() {

		boolean userName, password1, password2;

		userName = textFieldUserName.getText().trim().equalsIgnoreCase("") ? true
				: false;
		password1 = passwordField.getText().trim().equalsIgnoreCase("") ? true
				: false;
		password2 = passwordField2.getText().trim().equalsIgnoreCase("") ? true
				: false;

		if (!userName || !password1 || !password2) {
			return false;
		} else
			return true;
	}

	// checks if password match
	private boolean passwordMatch() {

		@SuppressWarnings("deprecation")
		boolean match = passwordField.getText().trim()
				.equals(passwordField2.getText().trim()) ? true : false;

		return match;
	}
}
