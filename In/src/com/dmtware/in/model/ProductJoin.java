/*
 * Product model for join query
 */

package com.dmtware.in.model;

public class ProductJoin {
	private int id;
	private String name;
	private String categoryName;
	private String type;
	private int stock;
	private String unit;
	private int stockAlarm;
	
	
	
	public ProductJoin(int id, String name, String categoryName, String type, int stock, String unit, int stockAlarm) {
		super();
		this.id = id;
		this.name = name;
		this.categoryName = categoryName;
		this.type = type;
		this.stock = stock;
		this.unit = unit;
		this.stockAlarm = stockAlarm;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	

	public int getstockAlarm() {
		return stockAlarm;
	}

	public void setstockAlarm(int stockAlarm) {
		this.stockAlarm = stockAlarm;
	}
	
	
	@Override
	public String toString() {
		return String
				.format("Product [name=%s, categoryName=%s, type=%s, stock=%s]",
						name, categoryName, type, stock);
	} 
}
