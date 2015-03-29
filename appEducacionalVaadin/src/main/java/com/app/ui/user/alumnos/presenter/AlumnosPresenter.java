/**
 * AlumnosPresenter.java
 * appEducacionalVaadin
 * 21/3/2015 11:34:26
 * Copyright David
 * com.app.ui.user.alumnos.presenter
 */
package com.app.ui.user.alumnos.presenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.AlumnoService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.domain.model.types.itemsevaluables.FaltaDeAsistencia;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.profesor.ProfesorPresenter;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Or;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class AlumnosPresenter {

	private ApplicationContext applicationContext;

	private PadreMadreOTutorService tutorService;

	private ProfesorService profesorService;
	
	private AlumnoService alumnoService;

	private AdministradorService adminService;

	private static NotificacionService notificacionService;

	private static Persona currentPerson;

	private static AlumnosPresenter instance;

	private EntityProvider<Alumno> entityProvider;

	public AlumnosPresenter() {
		loadBeans();
		entityProvider = buldEntityProvider();
	}

	public static AlumnosPresenter getInstance() {
		if (instance == null) {
			instance = new AlumnosPresenter();
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
		alumnoService = applicationContext
				.getBean(AlumnoService.class);
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
	 * @return
	 */
	private CachingMutableLocalEntityProvider<Alumno> buldEntityProvider() {
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
		CachingMutableLocalEntityProvider<Alumno> entityProvider = new CachingMutableLocalEntityProvider<Alumno>(
				Alumno.class, em);
		return entityProvider;
	}

	/**
	 * @author David
	 * @return
	 */
	public JPAContainer<Alumno> getAlumnosContainerEnCurso() {
		List<Curso> cursos = Lists.newArrayList(profesorService.getCursosImparteDocencia(getProfesor()));
		
		JPAContainer<Alumno> items = getAlumnosContainer();
		List<Filter> filters = Lists.newArrayList();
		for (Curso curso : cursos){
			Filter filter = new Compare.Equal("curso", curso.getId());
			filters.add(filter);
		}		
		Filter[] array = new Filter[filters.size()];
		filters.toArray(array); // fill the array
		Filter filterOr = new Or(array);
		items.addContainerFilter(filterOr);
		return items;
	}

	/**
	 * @author David
	 * @return
	 */
	private JPAContainer<Alumno> getAlumnosContainer() {
		// And there we have it
		JPAContainer<Alumno> items = new JPAContainer<Alumno>(
				Alumno.class);
		items.setEntityProvider(entityProvider);
		return items;
	}
	
	public Container getFaltasAsistenciaAlumno(Alumno a){
		
		Collection<FaltaDeAsistencia> faltas = profesorService.findAllFaltaSinJustificarMiAsignaturas(a,getProfesor());
		IndexedContainer container = new IndexedContainer(faltas);
		return container;
	}

	/**
	 * @author David
	 * @param idAlumno
	 * @return
	 */
	public Alumno getAlumno(Integer idAlumno) {
		return alumnoService.findOne(idAlumno);
	}

}
