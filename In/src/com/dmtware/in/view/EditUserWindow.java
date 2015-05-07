/*
 * Edit User class
 */
package com.dmtware.in.view;

import java.awt.Color;
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

public class EditUserWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<User> users;

	JTextField textFieldUserName;
	JPasswordField passwordField;
	JTextField textFieldFirstName;
	JTextField textFieldSurname;
	JPasswordField passwordField2;
	
	String currentId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditUserWindow dialog = new EditUserWindow();
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
	public EditUserWindow() {

		// initialise database connection
		conn = new SQLiteCon();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditUserWindow.class.getResource("/com/dmtware/in/view/logo_new.png")));
		setModal(true);
		setTitle("In - Edit User");
		setBounds(100, 100, 360, 268);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(56, 56, 56));

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

		JButton btnAddUser = new JButton("Update User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUser();
			}
		});
		btnAddUser.setBounds(164, 191, 180, 23);
		btnAddUser.setBackground(new Color(204, 204, 204));
		getContentPane().add(btnAddUser);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(EditUserWindow.class
				.getResource("/com/dmtware/in/view/User.png")));
		label.setBounds(10, 11, 72, 72);
		getContentPane().add(label);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(164, 97, 180, 20);
		getContentPane().add(passwordField2);

		JLabel lblUsername = new JLabel("Username*:");
		lblUsername.setForeground(Color.LIGHT_GRAY);
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(87, 36, 67, 14);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password*:");
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(87, 68, 67, 14);
		getContentPane().add(lblPassword);

		JLabel lblReenterPassword = new JLabel("Re-enter Password*:");
		lblReenterPassword.setForeground(Color.LIGHT_GRAY);
		lblReenterPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReenterPassword.setBounds(34, 98, 120, 14);
		getContentPane().add(lblReenterPassword);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setForeground(Color.LIGHT_GRAY);
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(34, 129, 120, 14);
		getContentPane().add(lblFirstName);

		JLabel lblSecondName = new JLabel("Second Name:");
		lblSecondName.setForeground(Color.LIGHT_GRAY);
		lblSecondName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecondName.setBounds(34, 160, 120, 14);
		getContentPane().add(lblSecondName);
		setLocationRelativeTo(null);
	}

	// adds user
	private void updateUser() {

		if (!emptyFields()) {
			
			String userName = textFieldUserName.getText().trim();
			String password1 = new String(passwordField.getPassword());
			@SuppressWarnings("unused")
			String password2 = new String(passwordField2.getPassword());
			String firstName = textFieldFirstName.getText().trim();
			String surname = textFieldSurname.getText().trim();

			if (passwordMatch()) {

				if (!userName.equalsIgnoreCase("admin")) {
					
					try {
						conn.updateUserQuery(currentId, userName, password1, firstName, surname);
						System.out.println("updated");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "You cannot rename user into \"admin\".");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Password doesn't match.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Make sure that all required fields are filled.");
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
