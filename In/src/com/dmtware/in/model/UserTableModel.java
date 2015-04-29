/*
 * User table model for join Query
 */

package com.dmtware.in.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int USER_ID_COL = 0;
	private static final int USER_USERNAME_COL = 1;
	private static final int USER_PASSWORD_COL = 2;
	private static final int USER_FIRSTNAME_COL = 3;
	private static final int USER_SURNAME_COL = 4;

	private String[] columnNames = { "Id", "User Name", "Password",
			"First Name", "Last Name" };

	private List<User> users;

	public UserTableModel(List<User> theUsers) {
		users = theUsers;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		User tempUser = users.get(row);

		switch (col) {
		case USER_ID_COL:
			return tempUser.getId();
		case USER_USERNAME_COL:
			return tempUser.getUserName();
		case USER_PASSWORD_COL:
			return tempUser.getPassword();
		case USER_FIRSTNAME_COL:
			return tempUser.getFirstName();
		case USER_SURNAME_COL:
			return tempUser.getSurname();

		default:
			return tempUser.getUserName();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}