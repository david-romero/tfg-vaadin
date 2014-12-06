/**
 * LogoutListener.java
 * appEducacionalVaadin
 * 29/11/2014 14:59:59
 * Copyright David
 * com.app.ui
 */
package com.app.ui.logout;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;



/**
 * @author David
 *
 */
public class LogoutListener implements Button.ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911443316178155175L;

	/**
	 * Constructor
	 */
	public LogoutListener() {
	}

	public void buttonClick(Button.ClickEvent clickEvent) {
	    SecurityContextHolder.clearContext();
	    //UI.getCurrent().close();
	    Navigator navigator = UI.getCurrent().getNavigator();
	    navigator.navigateTo("login");
	  }
	
}