/*
 * Product model for join query
 */

package com.dmtware.in.model;

public class ProductJoin {
	private String name;
	private String categoryName;
	private String type;
	private int stock;
	
	
	public ProductJoin(String name, String categoryName, String type, int stock) {
		super();
		
		this.name = name;
		this.categoryName = categoryName;
		this.type = type;
		this.stock = stock;
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


	@Override
	public String toString() {
		return String
				.format("Product [name=%s, categoryName=%s, type=%s, stock=%s]",
						name, categoryName, type, stock);
	} 
}
