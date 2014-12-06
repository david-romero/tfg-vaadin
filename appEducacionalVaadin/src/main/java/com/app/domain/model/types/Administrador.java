/**
 * Administrador.java
 * appEducacional
 * 10/02/2014 19:44:29
 * Copyright David Romero Alcaide
 * com.app.domainLayer.domainModel.types
 */
package com.app.domain.model.types;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David Romero Alcaide
 *
 */
public class Administrador extends Persona {

	/**
	 * Constructor
	 */
	public Administrador() {
		super();

	}

}
