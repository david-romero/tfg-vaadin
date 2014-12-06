/**
 * AppUi.java
 * appEducacionalVaadin
 * 29/11/2014 14:39:22
 * Copyright David
 * com.app.ui
 */
package com.app.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.app.infrastructure.security.Authority;
import com.app.ui.login.LoginView;
import com.app.ui.user.UserView;
import com.app.ui.user.admin.AdminView;
import com.app.ui.user.profesor.ProfesorView;
import com.app.ui.user.tutores.TutorView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;

@PreserveOnRefresh
/**
 * @author David
 *
 */
public class AppUI extends UI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2252711826491740298L;
	/**
	 * 
	 */
	private ApplicationContext applicationContext;

	@Override
	protected void init(VaadinRequest request) {
		WrappedSession session = request.getWrappedSession();
		HttpSession httpSession = ((WrappedHttpSession) session)
				.getHttpSession();
		ServletContext servletContext = httpSession.getServletContext();
		applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		NavigatorUI navigator = new NavigatorUI(this, this);
		navigator.addViewChangeListener(new ViewChangeListenerUI());
		navigator.addView("login", LoginView.class);
		navigator.addView("user", UserView.class);
		navigator.addView((Authority.PROFESOR+"/inicio").toLowerCase(), ProfesorView.class);
		navigator.addView((Authority.TUTOR+"/inicio").toLowerCase(), TutorView.class);
		navigator.addView((Authority.ADMINISTRADOR+"/inicio").toLowerCase(), AdminView.class);
		navigator.navigateTo("login");
		setNavigator(navigator);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
