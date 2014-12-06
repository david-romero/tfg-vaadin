/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:56:01
 * Copyright David
 * com.app.ui
 */
package com.app.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class LoginView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4892607583348353123L;

	public LoginView() {
		LoginForm loginForm = new LoginForm();
		addComponent(loginForm);
	}

	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}

}
