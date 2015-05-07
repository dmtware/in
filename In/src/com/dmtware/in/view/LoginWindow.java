/*
 * In - Login Window 
 */

package com.dmtware.in.view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.dmtware.in.dao.SQLiteCon;

import java.awt.Toolkit;

public class LoginWindow {

	// database connection class declaration
	public SQLiteCon conn;
	
	// fields that need access and change
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JFrame frmInLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmInLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {

		initialize();
		
		// connect to database
		conn = new SQLiteCon();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInLogin = new JFrame();
		
		frmInLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/com/dmtware/in/view/logo_new.png")));
		frmInLogin.setResizable(false);
		frmInLogin.setTitle("In - Login");
		frmInLogin.setBounds(100, 100, 362, 240);
		frmInLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInLogin.setLocationRelativeTo(null);
		frmInLogin.getContentPane().setLayout(null);
		frmInLogin.getContentPane().setBackground(new Color(56, 56, 56));
		

		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setForeground(Color.LIGHT_GRAY);
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserName.setBounds(74, 106, 102, 14);
		frmInLogin.getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(74, 138, 102, 14);
		frmInLogin.getContentPane().add(lblPassword);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(186, 104, 148, 20);
		frmInLogin.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(186, 135, 148, 20);
		frmInLogin.getContentPane().add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(204, 204, 204));
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// verify user and password
				conn.login(SQLiteCon.myConn, textFieldUserName, passwordField);
				if(SQLiteCon.isConnected == true) {
					frmInLogin.dispose();
				}
			}
		});
		btnLogin.setBounds(245, 166, 89, 30);
		frmInLogin.getContentPane().add(btnLogin);

		// enter key
		frmInLogin.getRootPane().setDefaultButton(btnLogin);
		
		JLabel labelPadLock = new JLabel("");
		labelPadLock.setIcon(new ImageIcon(LoginWindow.class.getResource("/com/dmtware/in/view/login_lock_new.png")));
		labelPadLock.setBounds(10, 168, 30, 30);
		frmInLogin.getContentPane().add(labelPadLock);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon(LoginWindow.class.getResource("/com/dmtware/in/view/logo_new_64_no_bckg.png")));
		labelLogo.setBounds(10, 11, 64, 64);
		frmInLogin.getContentPane().add(labelLogo);
		
		JLabel labelInventoryManagement = new JLabel("");
		labelInventoryManagement.setIcon(new ImageIcon(LoginWindow.class.getResource("/com/dmtware/in/view/inv_management.png")));
		labelInventoryManagement.setBounds(72, 25, 200, 50);
		frmInLogin.getContentPane().add(labelInventoryManagement);
		
	}
}
