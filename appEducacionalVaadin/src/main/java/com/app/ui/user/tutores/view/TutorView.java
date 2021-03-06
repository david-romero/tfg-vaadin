/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:05:37
 * Copyright David
 * com.app.ui
 */
package com.app.ui.user.tutores.view;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.app.infrastructure.security.Authority;
import com.app.ui.NavigatorUI;
import com.app.ui.user.MenuComponent;
import com.app.ui.user.UserAbstractView;
import com.app.ui.user.calendario.view.CalendarioView;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;

/**
 * @author David
 *
 */
public class TutorView extends UserAbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;
	
	private DiscoveryNavigator navigator;

	public void enter(ViewChangeListener.ViewChangeEvent event) {
		generateRol();
		super.enter(event);
		removeAllComponents();
		generateView();
	}
	
	/**
	 * @author David
	 */
	private void generateView() {
		setSizeFull();
        addStyleName("mainview");

        addComponent(new MenuComponent());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);
        
        this.navigator = new NavigatorUI(getUI(), content);
        this.navigator.addView("calendario", CalendarioView.class);
        this.navigator.addView("panelControl", PanelControlView.class);
	}

	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	@Override
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.TUTOR);
	}

	
	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#getRol()
	 */
	@Override
	public Authority getRol() {
		if (this.rol==null){
			generateRol();
			return rol;
		}else{
			return super.getRol();
		}
	}
	
}
