/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:05:37
 * Copyright David
 * com.app.ui
 */
package com.app.ui.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class LoginFormListener implements Button.ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 558804448768403387L;

	@Autowired
	private com.app.infrastructure.security.AuthManager authManager;

	/**
	 * Constructor
	 */
	public LoginFormListener() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
	 * ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		try {
			Button source = event.getButton();
			LoginForm parent = (LoginForm) source.getParent();
			String username = parent.getTxtLogin().getValue();
			String password = parent.getTxtPassword().getValue();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(password, null);;
			final Authentication auth = new UsernamePasswordAuthenticationToken(
					username, hash);
			Authentication result = authManager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(result);
			AppUI current = (AppUI) UI.getCurrent();
			NavigatorUI navigator = (NavigatorUI) current.getNavigator();
			navigator.navigateTo("user");
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Authentication failed: " + e.getMessage());
		}
	}

}
