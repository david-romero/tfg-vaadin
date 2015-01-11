/**
* NavigatorUI.java
* appEducacionalVaadin
* 6/12/2014 23:04:35
* Copyright David
* com.app.ui
*/
package com.app.ui;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.app.ui.user.UserAbstractView;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class NavigatorUI extends DiscoveryNavigator {

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.Navigator#fireBeforeViewChange(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	protected boolean fireBeforeViewChange(ViewChangeEvent event) {
		boolean res = super.fireBeforeViewChange(event);
		if (event.getNewView() instanceof UserAbstractView){
			checkPermisions(event);
		}
		return res;
	}



	/**
	 * @author David
	 * @param event 
	 */
	private void checkPermisions(ViewChangeEvent event) {
		if (( (UserAbstractView) event.getNewView()).getRol() == null){
			goToLoginView();
		}
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
			}else if(!authority.equals(( (UserAbstractView) event.getNewView()).getRol().getAuthority())){
				goToLoginView();
			}
		} else {
			goToLoginView();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4797700189597777378L;

	/**
	 * Constructor
	 * @param ui
	 * @param container
	 */
	public NavigatorUI(UI ui, ComponentContainer container) {
		super(ui, container);

	}

	/**
	 * Constructor
	 * @param ui
	 * @param container
	 */
	public NavigatorUI(UI ui, SingleComponentContainer container) {
		super(ui, container);

	}

	/**
	 * Constructor
	 * @param ui
	 * @param display
	 */
	public NavigatorUI(UI ui, ViewDisplay display) {
		super(ui, display);

	}

	/**
	 * Constructor
	 * @param ui
	 * @param stateManager
	 * @param display
	 */
	public NavigatorUI(UI ui, NavigationStateManager stateManager,
			ViewDisplay display) {
		super(ui, stateManager, display);

	}
	
	/**
	 * @author David
	 */
	private void goToLoginView() {
		SecurityContextHolder.clearContext();
		this.navigateTo("login");
	}

}
