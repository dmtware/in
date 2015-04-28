/*
 * Categories Window class
 */
package com.dmtware.in.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JDialog;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.User;
import com.dmtware.in.model.UserTableModel;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UsersWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<User> users;
	String newUser = "";

	// table
	private JTable tableUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UsersWindow dialog = new UsersWindow();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
	public UsersWindow() {
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UsersWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setTitle("In - Users");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 560, 242);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 11, 429, 192);
		getContentPane().add(scrollPane);

		tableUsers = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend) {
				// Always toggle on single selection
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
			}
		};
		tableUsers.setFocusable(false);

		scrollPane.setViewportView(tableUsers);

		tableUsers.setFillsViewportHeight(true);
		tableUsers.setBackground(SystemColor.window);
		tableUsers.setSelectionBackground(new Color(163, 193, 228));
		tableUsers.setRequestFocusEnabled(false);
		tableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UsersWindow.class
				.getResource("/com/dmtware/in/view/User.png")));
		label.setBounds(22, 11, 72, 72);
		getContentPane().add(label);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFocusPainted(false);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Add user
				addUser();
			}
		});
		btnAdd.setBounds(14, 119, 88, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setFocusPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeUser();
			}
		});
		btnRemove.setBounds(14, 149, 88, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUser();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEdit.setFocusPainted(false);
		btnEdit.setBounds(14, 179, 88, 23);
		getContentPane().add(btnEdit);

		setLocationRelativeTo(null);

		getUsersToTable();

	}

	// get all users to a table
	private void getUsersToTable() {
		try {

			users = conn.getAllUsers();

			UserTableModel model = new UserTableModel(users);
			tableUsers.setModel(model);

			hideColumns();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add user
	private void addUser() {
		AddUserWindow addUserWindow = new AddUserWindow();
		addUserWindow.setVisible(true);
		while (addUserWindow.isVisible()) {

		}
		getUsersToTable();
	}

	// remove user
	private void removeUser() {
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableUsers.getSelectedRow() == -1)) {

			int selectedRow = tableUsers.getSelectedRow();

			String userId = tableUsers.getValueAt(selectedRow, idCol)
					.toString();

			String userName = tableUsers.getValueAt(selectedRow, nameCol)
					.toString();

			System.out.println(userId + " " + userName);

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to remove this user?", "Remove?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeUserQuery(userId, userName);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				getUsersToTable();

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove category please select category row first");
		}
	}

	// edit user
	private void updateUser() {

		if (!(tableUsers.getSelectedRow() == -1)) {
			EditUserWindow editUserWindow = new EditUserWindow();
			
			// insert data from table to the fields
			//int idCol = 0;
			int userNameCol = 1;
			int passwordCol = 2;
			int firstNameCol = 3;
			int surnameCol = 4;

			int selectedRow = tableUsers.getSelectedRow();

			editUserWindow.textFieldUserName.setText(tableUsers.getValueAt(selectedRow,
					userNameCol).toString().trim());
			editUserWindow.passwordField.setText(tableUsers.getValueAt(selectedRow,
					passwordCol).toString().trim());
			editUserWindow.textFieldFirstName.setText(tableUsers.getValueAt(selectedRow,
					firstNameCol).toString().trim());
			editUserWindow.textFieldSurname.setText(tableUsers.getValueAt(selectedRow,
					surnameCol).toString().trim());
			
			editUserWindow.setVisible(true);
			
			while (editUserWindow.isVisible()) {

			}
			//refresh table
			getUsersToTable();
						
		} else {
			JOptionPane.showMessageDialog(null,
					"In order to edit please select user first");
		}
	}

	// hides columns
	private void hideColumns() {
		// remove/hide Id table
		TableColumn myTableColumn0 = tableUsers.getColumnModel().getColumn(0);
		TableColumn myTableColumn2 = tableUsers.getColumnModel().getColumn(2);

		// tableCategories.getColumnModel().removeColumn(myTableColumn0);
		myTableColumn0.setMaxWidth(0);
		myTableColumn0.setMinWidth(0);
		myTableColumn0.setPreferredWidth(0);

		myTableColumn2.setMaxWidth(0);
		myTableColumn2.setMinWidth(0);
		myTableColumn2.setPreferredWidth(0);
	}
}
