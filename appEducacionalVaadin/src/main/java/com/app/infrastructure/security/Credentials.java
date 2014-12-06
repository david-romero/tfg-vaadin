/** Credentials.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 *  
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */
package com.app.infrastructure.security;

/**
 * 
 */
import javax.validation.constraints.Size;

/**
 * 
 * @author David Romero Alcaide
 * 
 */
public class Credentials {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 * Constructor
	 */
	public Credentials() {
		super();
	}

	// Attributes -------------------------------------------------------------
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;

	@Size(min = 5, max = 32)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param username
	 */
	public void setJ_username(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
