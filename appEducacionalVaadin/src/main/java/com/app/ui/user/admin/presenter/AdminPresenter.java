/**
 * AdminPresenter.java
 * appEducacionalVaadin
 * 21/3/2015 20:09:18
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.admin.presenter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Not;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class AdminPresenter implements ClickListener{

	private ApplicationContext applicationContext;

	private PadreMadreOTutorService tutorService;

	private ProfesorService profesorService;

	private AdministradorService adminService;

	private static NotificacionService notificacionService;

	private static Persona currentPerson;

	private static AdminPresenter instance;

	private EntityProvider<Persona> entityProvider;

	public AdminPresenter() {
		loadBeans();
		entityProvider = buldEntityProvider();
	}

	/**
	 * @author David
	 * @return
	 */
	public static AdminPresenter getInstance() {
		if (instance == null) {
			instance = new AdminPresenter();
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
		return getAdministrador();
	}

	public Administrador getAdministrador() {
		UserAccount account = AppUI.getCurrentUser();
		com.app.domain.model.types.Administrador p = adminService
				.findByUserAccount(account);
		return p;
	}

	/**
	 * @author David
	 * @return
	 */
	private CachingMutableLocalEntityProvider<Persona> buldEntityProvider() {
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
		CachingMutableLocalEntityProvider<Persona> entityProvider = new CachingMutableLocalEntityProvider<Persona>(
				Persona.class, em);
		return entityProvider;
	}

	public JPAContainer<Persona> getContainerPersonas() {
		// And there we have it
		JPAContainer<Persona> items = new JPAContainer<Persona>(Persona.class);
		items.setEntityProvider(entityProvider);
		items.addContainerFilter(new Not(new IsNull("userAccount")));
		return items;
	}
	
	public JPAContainer<Persona> getContainerPersonasSinIdentificar() {
		JPAContainer<Persona> items = getContainerPersonas();
		items.addContainerFilter(new Compare.Equal("identidadConfirmada", Boolean.FALSE));
		return items;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		//Id de la persona
		String idString = event.getButton().getId();
		Integer id = new Integer(idString);
		Profesor p = profesorService.findOne(id);
		if (p == null){
			com.app.domain.model.types.PadreMadreOTutor t = tutorService.findOne(id);
			t.setUserAccount(null);
			tutorService.save(t);
		}else{
			p.setUserAccount(null);
			profesorService.save(p);
		}
		Table table = (Table) event.getButton().getParent();
		table.refreshRowCache();
	}

}
