/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:56:01
 * Copyright David
 * com.app.ui
 */
package com.app.ui.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.xpoft.vaadin.VaadinView;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.LoginService;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(LoginView.NAME)
/**
 * @author David
 *
 */
public class LoginView extends VerticalLayout implements View, ClickListener {

	public static final String NAME = "login";

	private ClickListener clickListener;

	private ApplicationContext applicationContext;
	private com.app.infrastructure.security.AuthManager authManager;
	private AdministradorService adminService;
	private ProfesorService profesorService;
	private PadreMadreOTutorService tutorService;

	
	private LoginForm loginForm;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4892607583348353123L;

	public LoginView() {
		clickListener = this;
		loginForm = new LoginForm();
		loadBeans();
	}

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ( (AppUI) UI.getCurrent() ).getApplicationContext();
		authManager = applicationContext.getBean(AuthManager.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
	}

	private class LoginForm extends VerticalLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2405284242525298112L;
		private TextField txtLogin;
		private PasswordField txtPassword;

		public TextField getTxtLogin() {
			return txtLogin;
		}

		public PasswordField getTxtPassword() {
			return txtPassword;
		}

		private Component buildFields() {
			HorizontalLayout fields = new HorizontalLayout();
			fields.setSpacing(true);
			fields.addStyleName("fields");

			txtLogin = new TextField("Username");
			txtLogin.setIcon(FontAwesome.USER);
			txtLogin.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			txtPassword = new PasswordField("Password");
			txtPassword.setIcon(FontAwesome.LOCK);
			txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

			final Button signin = new Button("Sign In");
			signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
			signin.setClickShortcut(KeyCode.ENTER);
			signin.focus();

			fields.addComponents(txtLogin, txtPassword, signin);
			fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

			signin.addClickListener(clickListener);
			return fields;
		}

		private Component buildLabels() {
			CssLayout labels = new CssLayout();
			labels.addStyleName("labels");

			Label welcome = new Label("Welcome");
			welcome.setSizeUndefined();
			welcome.addStyleName(ValoTheme.LABEL_H4);
			welcome.addStyleName(ValoTheme.LABEL_COLORED);
			labels.addComponent(welcome);

			Label title = new Label("QuickTickets Dashboard");
			title.setSizeUndefined();
			title.addStyleName(ValoTheme.LABEL_H3);
			title.addStyleName(ValoTheme.LABEL_LIGHT);
			labels.addComponent(title);
			return labels;
		}

		@Override
		public void attach() {
			final VerticalLayout loginPanel = new VerticalLayout();
			loginPanel.setSizeUndefined();
			loginPanel.setSpacing(true);
			Responsive.makeResponsive(loginPanel);
			loginPanel.addStyleName("login-panel");

			loginPanel.addComponent(buildLabels());
			loginPanel.addComponent(buildFields());
			loginPanel.addComponent(new CheckBox("Remember me", true));
			addComponent(loginPanel);
			setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		}
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

	@Override
	public void attach() {
		super.attach();
		setSizeFull();
		setStyleName("loginview");
		addComponent(loginForm);

		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		
		addStyleName("v-align-center");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.AbstractComponent#detach()
	 */
	@Override
	public void detach() {
		super.detach();
	}

}
