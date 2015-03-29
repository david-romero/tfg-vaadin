/**
 * AdminView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.admin
 */
package com.app.ui.user.admin.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.app.applicationservices.services.AdministradorService;
import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.Authority;
import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.app.ui.user.MenuComponent;
import com.app.ui.user.UserAbstractView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.app.ui.user.admin.view.banear.usuarios.*;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(AdminView.NAME)
/**
 * @author David
 *
 */
public class AdminView extends UserAbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;
	
	public static final String NAME = "admin";
	
	private ApplicationContext applicationContext;
	private com.app.infrastructure.security.AuthManager authManager;
	private AdministradorService adminService;
	
	private NavigatorUI navigator;

	public AdminView(){
		loadBeans();
	}
	
	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ( (AppUI) UI.getCurrent() ).getApplicationContext();
		authManager = applicationContext.getBean(AuthManager.class);
		adminService = applicationContext.getBean(AdministradorService.class);
	}
	
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		generateRol();
		super.enter(event);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.AbstractComponent#attach()
	 */
	@Override
	public void attach() {
		super.attach();
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
        this.navigator.addView("BanearUsuario", BanearUsuarioView.class);
        this.navigator.navigateTo("BanearUsuario");
	}

	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	@Override
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.ADMINISTRADOR);
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
