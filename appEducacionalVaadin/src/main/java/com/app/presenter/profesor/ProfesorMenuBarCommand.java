/**
* ProfesorMenuBarCommand.java
* appEducacionalVaadin
* 18/12/2014 20:21:35
* Copyright David
* com.app.presenter.profesor
*/
package com.app.presenter.profesor;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.ui.user.profesor.ProfesorView;
import com.google.common.base.Predicate;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.QueryModifierDelegate;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;

/**
 * @author David
 *
 */
public class ProfesorMenuBarCommand implements MenuBar.Command{

	private ProfesorView view;
	
	@Autowired
	private ProfesorService service;
	
	@Autowired
	/**
	 * Manager
	 */
	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * Constructor
	 * @param view
	 */
	public ProfesorMenuBarCommand(ProfesorView view) {
		this.view = view;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4631001243386447417L;

	/* (non-Javadoc)
	 * @see com.vaadin.ui.MenuBar.Command#menuSelected(com.vaadin.ui.MenuBar.MenuItem)
	 */
	@Override
	public void menuSelected(MenuItem selectedItem) {
		// Create a persistent person container
		JPAContainer<Alumno> persons =
		    JPAContainerFactory.make(Alumno.class, "appEducacional");
		view.getMainLayout().removeAllComponents();
		Table tabla = new Table("Mis alumnos",persons);
		view.getMainLayout().addComponent(tabla);
	}

}