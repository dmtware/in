package com.dmtware.in.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);
		
		int stock = Integer.parseInt(table.getModel().getValueAt(row, 4).toString());
		int stockAlarm = Integer.parseInt(table.getModel().getValueAt(row, 6).toString());
		
		if (stock <= stockAlarm) {
			comp.setForeground(Color.red);
		} else {
			comp.setForeground(null);
		}


		return (comp);
	}

}