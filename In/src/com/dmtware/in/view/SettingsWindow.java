/*
 * Settings Window class
 */
package com.dmtware.in.view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.dmtware.in.dao.SQLiteCon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SettingsWindow dialog = new SettingsWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SettingsWindow() {

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("In - Settings");

		setBounds(100, 100, 344, 316);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 49, 318, 12);
		contentPanel.add(separator);

		JButton btnUsers = new JButton("Users");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				openUsers();
			}
		});
		btnUsers.setFocusPainted(false);
		btnUsers.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnUsers.setBounds(186, 74, 110, 23);
		contentPanel.add(btnUsers);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 122, 318, 12);
		contentPanel.add(separator_1);

		JButton btnRemoveAllProducts = new JButton("Delete All");
		btnRemoveAllProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removeAllProducts();
			}
		});
		btnRemoveAllProducts.setFocusPainted(false);
		btnRemoveAllProducts.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveAllProducts.setBounds(186, 145, 110, 23);
		contentPanel.add(btnRemoveAllProducts);

		JButton btnRemoveAllCategories = new JButton("Delete All");
		btnRemoveAllCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removeAllCategories();

			}
		});
		btnRemoveAllCategories.setFocusPainted(false);
		btnRemoveAllCategories.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveAllCategories.setBounds(186, 189, 110, 23);
		contentPanel.add(btnRemoveAllCategories);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 234, 318, 12);
		contentPanel.add(separator_2);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnOk.setBounds(239, 254, 89, 23);
		contentPanel.add(btnOk);

		JLabel lblInSettings = new JLabel("Program Settings");
		lblInSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblInSettings.setBounds(106, 11, 120, 14);
		contentPanel.add(lblInSettings);

		JLabel lblManageUsers = new JLabel("Manage users:");
		lblManageUsers.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblManageUsers.setHorizontalAlignment(SwingConstants.LEFT);
		lblManageUsers.setBounds(36, 78, 130, 14);
		contentPanel.add(lblManageUsers);

		JLabel lblRemoveAllProducts = new JLabel("Remove all products:");
		lblRemoveAllProducts.setHorizontalAlignment(SwingConstants.LEFT);
		lblRemoveAllProducts.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRemoveAllProducts.setBounds(36, 149, 130, 14);
		contentPanel.add(lblRemoveAllProducts);

		JLabel lblRemoveAllCategories = new JLabel("Remove all categories:");
		lblRemoveAllCategories.setHorizontalAlignment(SwingConstants.LEFT);
		lblRemoveAllCategories.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRemoveAllCategories.setBounds(36, 193, 130, 14);
		contentPanel.add(lblRemoveAllCategories);
	}
	
	// opens users window
	private void openUsers(){
		UsersWindow usersWindow = new UsersWindow();
		usersWindow.setVisible(true);
	}

	// removes all products
	private void removeAllProducts() {
		
		JOptionPane.showMessageDialog(null, "You are going to remove all products from database. This can't be undone.");

		int reply = JOptionPane.showConfirmDialog(null,
				"Do you really want to remove all products?", "Remove?",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {

			try {
				conn.removeAllQuery("Product");
				System.out.println("removed");
			} catch (Exception e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "All Products removed");

		} else {
			// do nothing
		}
	}

	// removes all categories
	private void removeAllCategories() {
		
		JOptionPane.showMessageDialog(null, "You are going to remove all categories from database. This can't be undone.");

		int reply = JOptionPane.showConfirmDialog(null,
				"Do you really want to remove all categories?", "Remove?",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {

			try {
				conn.removeAllQuery("Category");
				System.out.println("removed");
				JOptionPane.showMessageDialog(null, "All Categories removed");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Some categories have products assigned. Remove all products first");
			}

		} else {
			// do nothing
		}
	}
}
