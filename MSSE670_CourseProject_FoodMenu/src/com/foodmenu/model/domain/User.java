package com.foodmenu.model.domain;

import java.io.Serializable;

/**
 * 
 * @author Zach Stanfill
 * Domain User Class
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1234567L;
	
	/** First Name of the User */
	private String firstName;
	
	/** Last Name of the User */
	private String lastName;
	
	/** Email Address of the User */
	private String emailAddress;
	
	/** Password of the User */
	private String password;
	
	/** Recovery Phrase of the User */
	private String recoveryPhrase;
	
	/** Age of the User */
	private int age;
	
	/** Role of the User */
	private String role;
	
	/** Default Constructor */
	public User () {
		
	}
	
	/**
	 * Overloaded Constructor
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param password
	 * @param recoveryPhrase
	 * @param age
	 * @param role
	 */
	public User (String firstName, String lastName, String emailAddress, 
			String password, String recoveryPhrase, int age, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.recoveryPhrase = recoveryPhrase;
		this.age = age;
		this.role = role; 
	}
	
	public User (String firstName, String lastName, String emailAddress, String recoveryPhrase,
			int age, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.recoveryPhrase = recoveryPhrase;
		this.age = age;
		this.role = role; 
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the recoveryPhrase
	 */
	public String getRecoveryPhrase() {
		return recoveryPhrase;
	}

	/**
	 * @param recoveryPhrase the recoveryPhrase to set
	 */
	public void setRecoveryPhrase(String recoveryPhrase) {
		this.recoveryPhrase = recoveryPhrase;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * validate() method
	 * @return boolean if all fields for FoodItem object is valid
	 */
	public boolean validate() {
		if(firstName == null) return false;
		if(lastName == null)return false;
		if(emailAddress == null)return false;
		if(password == null)return false;
		if(recoveryPhrase == null)return false;
		if(age <= 0)return false;
		if(role == null)return false;
		
		return true;
	}

}
