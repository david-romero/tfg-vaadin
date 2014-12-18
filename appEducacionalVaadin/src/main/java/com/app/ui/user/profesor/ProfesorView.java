/**
 * ProfesorView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.tutores
 */
package com.app.ui.user.profesor;

import com.app.infrastructure.security.Authority;
import com.app.presenter.profesor.ProfesorMenuBarCommand;
import com.app.presenter.profesor.ProfesorPresenter;
import com.app.ui.logout.LogoutListener;
import com.app.ui.user.UserAbstractView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * @author David
 *
 */
public class ProfesorView extends UserAbstractView {

	private HorizontalLayout mainLayout;
	
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
		MenuBar barmenu = new MenuBar();
		// A top-level menu item that opens a submenu
		MenuItem drinks = barmenu.addItem("Beverages", null, null);
		
		// Define a common menu command for all the menu items.
		ProfesorMenuBarCommand mycommand = new ProfesorMenuBarCommand(this);

		// Submenu item with a sub-submenu
		MenuItem hots = drinks.addItem("Hot", null, null);
		hots.addItem("Tea",
		    new ThemeResource("icons/tea-16px.png"),    mycommand);
		hots.addItem("Coffee",
		    new ThemeResource("icons/coffee-16px.png"), mycommand);

		// Another submenu item with a sub-submenu
		MenuItem colds = drinks.addItem("Cold", null, null);
		colds.addItem("Milk",      null, mycommand);
		colds.addItem("Weissbier", null, mycommand);

		// Another top-level item
		MenuItem snacks = barmenu.addItem("Snacks", null, null);
		snacks.addItem("Weisswurst", null, mycommand);
		snacks.addItem("Bratwurst",  null, mycommand);
		snacks.addItem("Currywurst", null, mycommand);
		        
		// Yet another top-level item
		MenuItem servs = barmenu.addItem("Services", null, null);
		servs.addItem("Car Service", null, mycommand);
		addComponent(barmenu);
		Button logout = new Button("Logout");
		LogoutListener logoutListener = new LogoutListener();
		logout.addClickListener(logoutListener);
		addComponent(logout);
		mainLayout = new HorizontalLayout();
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

	/**
	 * @return mainLayout
	 */
	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

	/**
	 * @param mainLayout the mainLayout to set
	 * Establecer el mainLayout
	 */
	public void setMainLayout(HorizontalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}


}
