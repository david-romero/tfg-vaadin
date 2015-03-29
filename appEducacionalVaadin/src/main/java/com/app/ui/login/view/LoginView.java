/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:56:01
 * Copyright David
 * com.app.ui
 */
package com.app.ui.login.view;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.app.ui.login.presenter.LoginPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(LoginView.NAME)
/**
 * @author David
 *
 */
public class LoginView extends VerticalLayout implements View {

	public static final String NAME = "login";

	protected LoginPresenter presenter;

	
	private LoginForm loginForm;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4892607583348353123L;

	public LoginView() {
		loginForm = new LoginForm();
		presenter = LoginPresenter.getInstace();
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
		setSizeFull();
		setStyleName("loginview");
		addComponent(loginForm);

		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		
		addStyleName("v-align-center");
	}	

}
