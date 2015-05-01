/*
 * Product table model for join query
 */

package com.dmtware.in.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductJoinTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PRODUCT_NAME_COL = 0;
	private static final int CATEGORY_COL = 1;
	private static final int TYPE_COL = 2;
	private static final int STOCK_COL = 3;
	private static final int UNIT_COL = 4;

	private String[] columnNames = { "Product Name", "Category", "Type",
			"Stock", "Unit"};
	
	private List<ProductJoin> productsJoin;

	public ProductJoinTableModel(List<ProductJoin> theProductsJoin) {
		productsJoin = theProductsJoin;
	} 
	
	@Override
	public int getColumnCount() {
		return columnNames.length; 
	}

	@Override
	public int getRowCount() {
		return productsJoin.size(); 
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	} 

	@Override
	public Object getValueAt(int row, int col) {

		ProductJoin tempProductJoin = productsJoin.get(row);

		switch (col) {
		case PRODUCT_NAME_COL:
			return tempProductJoin.getName();
		case CATEGORY_COL:
			return tempProductJoin.getCategoryName();
		case TYPE_COL:
			return tempProductJoin.getType();
		case STOCK_COL:
			return tempProductJoin.getStock();
		case UNIT_COL:
			return tempProductJoin.getUnit();
		default:
			return tempProductJoin.getName();
		} 
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	} 

}
