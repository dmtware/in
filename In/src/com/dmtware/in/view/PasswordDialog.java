/*
 * Password Dialog class
 */
package com.dmtware.in.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JLabel lblEnterMessage;
	JPasswordField passwordField;
	int option;
	public static final int OPTION_OK = 1;
	public static final int OPTION_CANCEL = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PasswordDialog dialog = new PasswordDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PasswordDialog() {
		setTitle("In - Change Password");
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PasswordDialog.class.getResource("/com/dmtware/in/view/login_lock.png")));
		setBounds(100, 100, 368, 136);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 22, 160, 20);
		contentPanel.add(passwordField);
		{
			lblEnterMessage = new JLabel("Enter current Password:");
			lblEnterMessage.setHorizontalAlignment(SwingConstants.RIGHT);
			lblEnterMessage.setBounds(10, 25, 162, 14);
			contentPanel.add(lblEnterMessage);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						option = 1;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						option = 0;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
	}
}
