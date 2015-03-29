/**
 * PanelControlPresenter.java
 * appEducacionalVaadin
 * 7/2/2015 18:13:51
 * Copyright David
 * com.app.ui.user.panelControl.presenter
 */
package com.app.ui.user.panelControl.presenter;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class PanelControlPresenter {

	private ApplicationContext applicationContext;

	private PadreMadreOTutorService tutorService;

	private ProfesorService profesorService;

	private AdministradorService adminService;

	private static NotificacionService notificacionService;

	private static Persona currentPerson;

	private static PanelControlPresenter instance;

	public PanelControlPresenter() {
		loadBeans();
	}

	public static PanelControlPresenter getInstance() {
		if (instance == null) {
			instance = new PanelControlPresenter();
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
	public int getNotificacionesNoLeidas() {
		return notificacionService
				.findProfesorEmitidas(getProfesor()).size() + notificacionService.findProfesorRecibidas(getProfesor()).size();
	}

	/**
	 * @author David
	 * @return
	 */
	public List<Notificacion> getListNotificacionesNoLeidas() {
		List<Notificacion> list = Lists.newArrayList(notificacionService
				.findProfesorEmitidas(getProfesor()));
		list.addAll(notificacionService.findProfesorRecibidas(getProfesor()));
		if ( list.size() > 2 ){
			list = list.subList(0, 2);
		}
		return list;
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

	/**
	 * @author David
	 */
	public void savePerson(Persona p) {
		profesorService.save((Profesor) p);
	}

}
