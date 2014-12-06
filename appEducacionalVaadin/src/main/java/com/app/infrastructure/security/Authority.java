/* Authority.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.app.infrastructure.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class Authority implements GrantedAuthority {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Constructor
	 */
	public Authority() {
		super();
	}

	// Values -----------------------------------------------------------------
	/**
	 * 
	 */
	public static final String PROFESOR = "PROFESOR";
	/**
	 * 
	 */
	public static final String TUTOR = "TUTOR";

	/**
	 * 
	 */
	public static final String ADMINISTRADOR = "ADMINISTRADOR";

	// Attributes -------------------------------------------------------------

	/**
	 * 
	 */
	private String authority;

	@NotBlank
	@Pattern(regexp = "^" + PROFESOR + "|" + TUTOR + "|" + ADMINISTRADOR + "$")
	/**
	 * 
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(TUTOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(PROFESOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(ADMINISTRADOR);
		result.add(authority);

		return result;
	}

	// Equality ---------------------------------------------------------------

	@Override
	/**
	 * 
	 */
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	/**
	 * 
	 */
	public boolean equals(Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other)
					.getAuthority()));

		return result;
	}

}
