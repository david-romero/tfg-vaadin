/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:05:37
 * Copyright David
 * com.app.ui
 */
package com.app.ui.login;

import org.springframework.context.ApplicationContext;

import com.app.ui.AppUI;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author David
 *
 */
public class LoginForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6294808981525997285L;
	private TextField txtLogin = new TextField("Login: ");
	private PasswordField txtPassword = new PasswordField("Password: ");
	private Button btnLogin = new Button("Login");
	private Embedded e = new Embedded(null, new ExternalResource(
			"http://www.youtube.com/v/meXvxkn1Y_8&hl=en_US&fs=1&"));

	public LoginForm() {
		e.setMimeType("application/x-shockwave-flash");
		e.setParameter("allowFullScreen", "true");
		e.setWidth("320px");
		e.setHeight("265px");
		addComponent(e);
		addComponent(txtLogin);
		addComponent(txtPassword);
		addComponent(btnLogin);
		LoginFormListener loginFormListener = getLoginFormListener();
		btnLogin.addClickListener(loginFormListener);
	}

	public LoginFormListener getLoginFormListener() {
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(LoginFormListener.class);
	}

	public TextField getTxtLogin() {
		return txtLogin;
	}

	public PasswordField getTxtPassword() {
		return txtPassword;
	}
}
