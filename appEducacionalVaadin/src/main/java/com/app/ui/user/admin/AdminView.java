/**
 * AdminView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.admin
 */
package com.app.ui.user.admin;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.xpoft.vaadin.VaadinView;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.ui.logout.LogoutListener;
import com.app.ui.user.MenuComponent;
import com.app.ui.user.UserAbstractView;
import com.app.ui.user.profesor.ProfesorView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
        UserAccount account = AppUI.getCurrentUser();
        Administrador admin = adminService.findByUserAccount(account);
        content.addComponent(new Label(admin.getUserAccount().getUsername()));
        addComponent(content);
        setExpandRatio(content, 1.0f);
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
