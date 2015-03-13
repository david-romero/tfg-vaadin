/**
* CalendarioPresenter.java
* appEducacionalVaadin
* 25/1/2015 0:48:37
* Copyright David
* com.app.ui.user.calendario.presenter
*/
package com.app.ui.user.calendario.presenter;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.applicationservices.services.Service;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.exceptions.GeneralException;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.ui.user.calendario.CalendarioView;
import com.app.ui.user.notificaciones.view.NotificacionesView;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.app.applicationservices.services.*;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

/**
 * @author David
 *
 */
public class CalendarioPresenter {
	
	private ApplicationContext applicationContext;

	private CitaService citaService;

	private ProfesorService profesorService;

	private Persona person;

	private PadreMadreOTutorService tutorService;

	private EventoService eventoService;
	
	private CalendarioView view;

	public CalendarioPresenter(CalendarioView view) {
		loadBeans();
		this.view = view;
	}
	
	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ((AppUI) UI.getCurrent()).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
		citaService = applicationContext.getBean(CitaService.class);
		tutorService = applicationContext
				.getBean(PadreMadreOTutorService.class);
		eventoService = applicationContext.getBean(EventoService.class);
	}

	/**
	 * @author David
	 * @param cita
	 * @param class1
	 * @throws GeneralException 
	 */
	public void save(Cita cita, Class<?> clazz) throws GeneralException {
		if ( clazz.equals(Profesor.class) ){
			citaService.save(cita, getProfesor());
			
		}
		view.showTab(0);
	}
	
	public Profesor getProfesor(){
		UserAccount account = AppUI.getCurrentUser();
		Profesor p = profesorService.findByUserAccount(account);
		return p;
	}

	/**
	 * @author David
	 * @param class1
	 * @return
	 */
	public IndexedContainer getContainer(Class<PadreMadreOTutor> clazz) {
		IndexedContainer container = null;
		if ( clazz.equals(PadreMadreOTutor.class) ){
			List<PadreMadreOTutor> tutores = profesorService.getTutoresAlumnosPertenecientesProfesor(getProfesor());
			container = new IndexedContainer(tutores);
		}
		return container;
	}
	
	public Cita create(Class<?> clazz){
		Cita cita = citaService.create();
		if ( clazz.equals(PadreMadreOTutor.class) ){
			
		}else{
			cita.setProfesor(getProfesor());
		}
		cita.setEmisor(clazz.getSimpleName());
		cita.setLeida(false);
		return cita;
	}

	/**
	 * @author David
	 * @return
	 */
	public List<Cita> findAll() {
		return Lists.newArrayList(citaService.findAll());
	}

}
