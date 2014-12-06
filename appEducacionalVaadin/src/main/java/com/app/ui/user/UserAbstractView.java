/**
* UserAbstractView.java
* appEducacionalVaadin
* 6/12/2014 21:55:44
* Copyright David
* com.app.ui.user
*/
package com.app.ui.user;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.infrastructure.security.Authority;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public abstract class UserAbstractView extends VerticalLayout implements View {

	
	protected Authority rol;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7419779239698441402L;

	/**
	 * Constructor
	 */
	public UserAbstractView() {
	}

	/**
	 * Constructor
	 * @param children
	 */
	public UserAbstractView(Component... children) {
		super(children);

	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	

	/**
	 * @return rol
	 */
	public Authority getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 * Establecer el rol
	 */
	public void setRol(Authority rol) {
		this.rol = rol;
	}
	
	public abstract void generateRol();

}
