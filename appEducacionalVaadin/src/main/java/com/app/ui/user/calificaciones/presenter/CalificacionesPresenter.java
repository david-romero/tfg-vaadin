/**
* CalificacionesPresenter.java
* appEducacionalVaadin
* 13/3/2015 23:57:48
* Copyright David
* com.app.ui.user.calificaciones.presenter
*/
package com.app.ui.user.calificaciones.presenter;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.utility.Pair;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class CalificacionesPresenter {

	private ApplicationContext applicationContext;

	private PadreMadreOTutorService tutorService;

	private ProfesorService profesorService;

	private AdministradorService adminService;

	private static NotificacionService notificacionService;

	private static Persona currentPerson;

	private static CalificacionesPresenter instance;

	public CalificacionesPresenter() {
		loadBeans();
	}

	public static CalificacionesPresenter getInstance() {
		if (instance == null) {
			instance = new CalificacionesPresenter();
		}
		return instance;
	}

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ((AppUI) UI.getCurrent()).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
		adminService = applicationContext.getBean(AdministradorService.class);
		tutorService = applicationContext
				.getBean(PadreMadreOTutorService.class);
		notificacionService = applicationContext
				.getBean(NotificacionService.class);
	}



	/**
	 * @author David
	 * @return
	 */
	public Persona getCurrentPerson() {
		return getProfesor();
	}

	public Profesor getProfesor() {
		UserAccount account = AppUI.getCurrentUser();
		Profesor p = profesorService.findByUserAccount(account);
		return p;
	}
	
	public List<Curso> getCursos(){
		return Lists.newArrayList(profesorService.getCursosImparteDocencia(getProfesor()));
	}

	/**
	 * @author David
	 * @return
	 */
	public List<Pair<Component,String>> getTabs() {
		List<Pair<Component,String>> tabs = Lists.newArrayList();
		for (Curso c : getCursos()){
			VerticalLayout tab = new VerticalLayout();
			tab.addComponent(new Label(c.getNivelEducativo()));
			tabs.add(Pair.create(tab, c.getNivelEducativo()));
		}
		return tabs;
	}
}
