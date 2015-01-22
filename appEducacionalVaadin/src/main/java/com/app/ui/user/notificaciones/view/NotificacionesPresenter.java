/**
* NotificacionesPresenter.java
* appEducacionalVaadin
* 21/1/2015 19:52:38
* Copyright David
* com.app.ui.user.notificaciones.view
*/
package com.app.ui.user.notificaciones.view;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.event.AppEducacionalEventBus;
import com.app.presenter.event.AppEducacionalEvent.NotificationsCountUpdatedEvent;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class NotificacionesPresenter {

	private ApplicationContext applicationContext;

	private NotificacionService notificacionService;

	private ProfesorService profesorService;

	private Persona person;

	private PadreMadreOTutorService tutorService;

	private EventoService eventoService;
	
	private NotificacionesView view;
	
	public NotificacionesPresenter(){
		loadBeans();
	}

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ((AppUI) UI.getCurrent()).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
		notificacionService = applicationContext.getBean(NotificacionService.class);
		tutorService = applicationContext
				.getBean(PadreMadreOTutorService.class);
		eventoService = applicationContext.getBean(EventoService.class);
	}

	/**
	 * @author David
	 * @return
	 */
	public List<Notificacion> getNotificacines() {
		List<Notificacion> notifications = Lists.newArrayList();
		UserAccount account = AppUI.getCurrentUser();
			switch ( Lists.newArrayList(account.getAuthorities()).get(0).getAuthority() ) {
			case Authority.PROFESOR:
				Profesor p = profesorService.findByUserAccount(account);
				notifications.addAll(p.getNotificaciones());
				break;
			case Authority.TUTOR:
				PadreMadreOTutor tutor = tutorService.findByUserAccount(account);
				notifications.addAll(tutor.getNotificaciones());
				break;

			default:
				break;
			}

		return notifications;
	}
	
}
