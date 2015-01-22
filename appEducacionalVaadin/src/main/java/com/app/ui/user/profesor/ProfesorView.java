/**
 * ProfesorView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.tutores
 */
package com.app.ui.user.profesor;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.app.infrastructure.security.Authority;
import com.app.ui.NavigatorUI;
import com.app.ui.user.MenuComponent;
import com.app.ui.user.UserAbstractView;
import com.app.ui.user.calendario.CalendarioView;
import com.app.ui.user.informes.InformesView;
import com.app.ui.user.notificaciones.view.NotificacionesView;
import com.app.ui.user.panelControl.PanelControlView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(ProfesorView.NAME)
/**
 * @author David
 *
 */
public class ProfesorView extends UserAbstractView {

	private NavigatorUI navigator;
	
	public static final String NAME = "profesor";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;

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
        this.navigator.addView("Informes", InformesView.class);
        this.navigator.addView("Panel de Control", PanelControlView.class);
        this.navigator.addView("notificaciones", NotificacionesView.class);
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

	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	@Override
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.PROFESOR);
	}

	


}
