/*
 * Product model
 */

package com.dmtware.in.model;

public class Product {
	private int id;
	private String name;
	private int categoryId;
	private String type;
	private int stock;
	
	
	public Product(int id, String name, int categoryId, String type, int stock) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.type = type;
		this.stock = stock;
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
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
				.format("Product [id=%s, name=%s, categoryId=%s, type=%s, stock=%s]",
						id, name, categoryId, type, stock);
	} 
}
