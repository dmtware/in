/*
 * User model
 */

package com.dmtware.in.model;

public class User {
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String surname;
	
	// constructor
	public User(int id, String userName, String password, String firstName,
			String surname) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.surname = surname;
	}
	
	// getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	
	@Override
	public String toString() {
		return String
				.format("User [id=%s, userName=%s, password=%s, firstName=%s, surname=%s]",
						id, userName, password, firstName, surname);
	} 
}
