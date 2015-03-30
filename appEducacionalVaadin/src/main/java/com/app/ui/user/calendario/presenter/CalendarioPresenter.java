/**
 * CalendarioPresenter.java
 * appEducacionalVaadin
 * 25/1/2015 0:48:37
 * Copyright David
 * com.app.ui.user.calendario.presenter
 */
package com.app.ui.user.calendario.presenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.exceptions.GeneralException;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Between;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class CalendarioPresenter {

	private ApplicationContext applicationContext;

	private CitaService citaService;

	private ProfesorService profesorService;

	private PadreMadreOTutorService tutorService;

	private EventoService eventoService;

	protected static CalendarioPresenter instance;

	protected EntityProvider<Cita> entityProvider;

	private CalendarioPresenter() {
		loadBeans();
		entityProvider = buldEntityProvider();
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
		if (clazz.equals(Profesor.class)) {
			citaService.save(cita, getProfesor());

		}
	}

	public Profesor getProfesor() {
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
		if (clazz.equals(PadreMadreOTutor.class)) {
			List<PadreMadreOTutor> tutores = profesorService
					.getTutoresAlumnosPertenecientesProfesor(getProfesor());
			container = new IndexedContainer(tutores);
		}
		return container;
	}

	public Cita create(Class<?> clazz) {
		Cita cita = citaService.create();
		if (clazz.equals(PadreMadreOTutor.class)) {

		} else {
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
	public List<Cita> findAllCitas() {
		List<Cita> all = Lists.newArrayList();
		all.addAll(citaService.findProfesorEmitidas(getProfesor()));
		all.addAll(citaService.findProfesorRecibidas(getProfesor()));
		return all;
	}

	/**
	 * @author David
	 * @return
	 */
	public List<com.app.domain.model.types.Evento> findAllEventos() {
		List<com.app.domain.model.types.Evento> all = Lists.newArrayList();
		all.addAll(eventoService.findAllProfesor(getProfesor()));
		return all;
	}

	/**
	 * @author David
	 * @return
	 */
	public static CalendarioPresenter getInstance() {
		if (instance == null) {
			instance = new CalendarioPresenter();
		}
		return instance;
	}

	/**
	 * @author David
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JPAContainer<Cita> findAllBetweenDates(Date startDate, Date endDate) {
		// And there we have it
		JPAContainer<Cita> items = new JPAContainer<Cita>(Cita.class);
		items.setEntityProvider(entityProvider);
		Between between = new Between("fecha", startDate, endDate);
		items.addContainerFilter(between);
		return items;
	}

	/**
	 * @author David
	 * @return
	 */
	private CachingMutableLocalEntityProvider<Cita> buldEntityProvider() {
		// We need a factory to create entity manager
		Map<String, String> properties = new HashMap<String, String>();
		properties
				.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		properties.put("javax.persistence.jdbc.url",
				"jdbc:mysql://localhost:3306/appEducacional");
		properties.put("javax.persistence.jdbc.user", "rootApp");
		properties.put("javax.persistence.jdbc.password", "root123");

		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.hbm2ddl.auto", "verify");
		properties.put("hibernate.cglib.use_reflection_optimizer", "true");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				"appEducacional", properties);

		// We need an entity manager to create entity provider
		EntityManager em = emf.createEntityManager();

		// We need an entity provider to create a container
		CachingMutableLocalEntityProvider<Cita> entityProvider = new CachingMutableLocalEntityProvider<Cita>(
				Cita.class, em);
		return entityProvider;
	}

}
