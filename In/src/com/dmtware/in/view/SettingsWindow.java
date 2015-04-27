/*
 * Settings Window class
 */
package com.dmtware.in.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SettingsWindow extends JDialog {

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
		btnUsers.setFocusPainted(false);
		btnUsers.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnUsers.setBounds(186, 77, 110, 23);
		contentPanel.add(btnUsers);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 122, 318, 12);
		contentPanel.add(separator_1);
		
		JButton btnRemoveAllProducts = new JButton("Delete All");
		btnRemoveAllProducts.setFocusPainted(false);
		btnRemoveAllProducts.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveAllProducts.setBounds(186, 145, 110, 23);
		contentPanel.add(btnRemoveAllProducts);
		
		JButton btnRemoveAllCategories = new JButton("Delete All");
		btnRemoveAllCategories.setFocusPainted(false);
		btnRemoveAllCategories.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveAllCategories.setBounds(186, 189, 110, 23);
		contentPanel.add(btnRemoveAllCategories);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 234, 318, 12);
		contentPanel.add(separator_2);
		
		JButton btnOk = new JButton("Ok");
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
		lblManageUsers.setBounds(36, 81, 130, 14);
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
}
