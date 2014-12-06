/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:05:37
 * Copyright David
 * com.app.ui
 */
package com.app.ui;

import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;

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

	public LoginForm() {
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
