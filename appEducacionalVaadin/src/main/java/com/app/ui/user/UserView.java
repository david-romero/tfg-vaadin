/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:05:37
 * Copyright David
 * com.app.ui
 */
package com.app.ui.user;

import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.app.infrastructure.security.Authority;
import com.app.ui.logout.LogoutListener;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Component
@VaadinView(UserView.NAME)
/**
 * @author David
 *
 */
public class UserView extends VerticalLayout implements View {

	public static final String NAME = "user";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;

	public void enter(ViewChangeListener.ViewChangeEvent event) {
		removeAllComponents();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Collection<? extends GrantedAuthority> authorities = authentication
					.getAuthorities();
			GrantedAuthority grantedAuthority = Lists.newArrayList(authorities)
					.get(0);
			String authority = grantedAuthority.getAuthority();
			if (authority.equals("ROLE_ANONYMOUS")) {
				goToLoginView();
			}
			if (authority.equals(Authority.ADMINISTRADOR)) {
				goToView(Authority.ADMINISTRADOR);
			} 
			if (authority.equals(Authority.PROFESOR)) {
				goToView(Authority.PROFESOR);
			} 
			if (authority.equals(Authority.TUTOR)) {
				goToView(Authority.TUTOR);
			} 
		} else {
			goToLoginView();
		}
	}

	/**
	 * @author David
	 * @param administrador
	 */
	private void goToView(String view) {
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo((view+"/inicio").toLowerCase());
	}

	/**
	 * @author David
	 */
	private void goToLoginView() {
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo("login");
	}
}
