/*
 * Category table model for join Query
 */

package com.dmtware.in.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CategoryTableModel extends AbstractTableModel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int CATEGORY_ID_COL = 0;
	private static final int CATEGORY_NAME_COL = 1;

	private String[] columnNames = {"Id", "Name"};
	
	private List<Category> categories;

	public CategoryTableModel(List<Category> theCategories) {
		categories = theCategories;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return categories.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		Category tempCategory = categories.get(row);

		switch (col) {
		case CATEGORY_ID_COL:
			return tempCategory.getId();
		case CATEGORY_NAME_COL:
			return tempCategory.getName();
		default:
			return tempCategory.getName();
		} 
		
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 
}