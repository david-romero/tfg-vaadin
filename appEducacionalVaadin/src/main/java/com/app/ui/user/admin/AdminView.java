/**
 * AdminView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.admin
 */
package com.app.ui.user.admin;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.infrastructure.security.Authority;
import com.app.ui.logout.LogoutListener;
import com.app.ui.user.UserAbstractView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class AdminView extends UserAbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;

	public void enter(ViewChangeListener.ViewChangeEvent event) {
		generateRol();
		super.enter(event);
		removeAllComponents();
		Label labelLogin = new Label("Soy un administrador");
		addComponent(labelLogin);
		Button logout = new Button("Logout");
		LogoutListener logoutListener = new LogoutListener();
		logout.addClickListener(logoutListener);
		addComponent(logout);
	}

	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	@Override
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.ADMINISTRADOR);
	}
	
	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#getRol()
	 */
	@Override
	public Authority getRol() {
		if (this.rol==null){
			generateRol();
			return rol;
		}else{
			return super.getRol();
		}
	}

}
