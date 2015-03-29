/**
 * LoginPresenter.java
 * appEducacionalVaadin
 * 29/3/2015 13:51:44
 * Copyright David
 * com.app.ui.login.presenter
 */
package com.app.ui.login.presenter;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.app.ui.login.view.LoginForm;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class LoginPresenter implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2210839455689014462L;
	private static LoginPresenter instance;

	private ApplicationContext applicationContext;
	private com.app.infrastructure.security.AuthManager authManager;

	private LoginPresenter() {
		loadBeans();
	}

	public static LoginPresenter getInstace() {
		if (instance == null) {
			instance = new LoginPresenter();
		}
		return instance;
	}

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ((AppUI) UI.getCurrent()).getApplicationContext();
		authManager = applicationContext.getBean(AuthManager.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
	 * ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			Button source = event.getButton();
			LoginForm parent = (LoginForm) source.getParent().getParent()
					.getParent();
			String username = parent.getTxtLogin().getValue();
			String password = parent.getTxtPassword().getValue();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(password, null);

			final Authentication auth = new UsernamePasswordAuthenticationToken(
					username, hash);
			Authentication result = authManager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(result);
			saveDataToSession(result);
			AppUI current = (AppUI) UI.getCurrent();
			NavigatorUI navigator = (NavigatorUI) current.getNavigator();
			navigator.navigateTo("user");
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Authentication failed: " + e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			Notification.show("Authentication failed: " + e.getMessage());
		}
	}

	/**
	 * @author David
	 * @param result
	 * @throws Throwable
	 */
	private void saveDataToSession(Authentication result) throws Throwable {
		UserAccount account = (UserAccount) result.getPrincipal();

		if (account != null) {
			VaadinSession.getCurrent().setAttribute(UserAccount.class, account);
		} else {
			throw new Throwable();
		}
	}

}
