/*
 * Unit table model for join Query
 */

package com.dmtware.in.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class UnitTableModel extends AbstractTableModel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int UNIT_ID_COL = 0;
	private static final int UNIT_NAME_COL = 1;

	private String[] columnNames = {"Id", "Name"};
	
	private List<Unit> units;

	public UnitTableModel(List<Unit> theUnits) {
		units = theUnits;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return units.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		Unit tempUnit = units.get(row);

		switch (col) {
		case UNIT_ID_COL:
			return tempUnit.getId();
		case UNIT_NAME_COL:
			return tempUnit.getName();
		default:
			return tempUnit.getName();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 
}