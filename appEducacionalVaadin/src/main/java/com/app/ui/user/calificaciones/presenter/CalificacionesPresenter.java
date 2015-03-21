/**
* CalificacionesPresenter.java
* appEducacionalVaadin
* 13/3/2015 23:57:48
* Copyright David
* com.app.ui.user.calificaciones.presenter
*/
package com.app.ui.user.calificaciones.presenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.app.ui.components.TablaItemsEvaluables;
import com.app.ui.components.TopSixTheatersChart;
import com.app.ui.components.TopTenMoviesTable;
import com.app.utility.Pair;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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
	
	private CssLayout dashboardPanels;
	
	private CachingMutableLocalEntityProvider<ItemEvaluable> entityProvider;

	public CalificacionesPresenter() {
		loadBeans();
		entityProvider = buldEntityProvider();
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
			Panel p = new Panel();
			p.addStyleName(ValoTheme.PANEL_BORDERLESS);
			p.setSizeFull();
			VerticalLayout tab = new VerticalLayout();
			p.setContent(tab);
			Asignatura a = profesorService.getAsignaturaCursoProfesor(c, getProfesor());
			createTab(tab,a);
			tabs.add(Pair.create(p, c.toString()));
		}
		return tabs;
	}

	/**
	 * @author David
	 * @param tab
	 */
	private void createTab(VerticalLayout tab,Asignatura asign) {
		tab.setSizeFull();
		tab.setMargin(true);
		tab.addStyleName("appeducacional-view");
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("appeducacional-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(buildItemsEvaluables(1,asign));
		dashboardPanels.addComponent(buildItemsEvaluables(2,asign));
		dashboardPanels.addComponent(buildItemsEvaluables(3,asign));
		dashboardPanels.addComponent(buildTop10TitlesByRevenue());
		tab.addComponent(dashboardPanels);
		tab.setExpandRatio(dashboardPanels, 1);
		Responsive.makeResponsive(tab);
	}
	
	private Component buildItemsEvaluables(Integer evaluacion,Asignatura a) {
		
		        
		// And there we have it
		JPAContainer<ItemEvaluable> items =
		        new JPAContainer<ItemEvaluable> (ItemEvaluable.class);
		items.setEntityProvider(entityProvider);
		Filter filter = new Compare.Equal("asignatura", a.getId());
		items.addContainerFilter(filter);
		Filter filter2 = new Compare.Equal("evaluacion.indicador", evaluacion);
		items.addContainerFilter(filter2);
		TablaItemsEvaluables tabla = new TablaItemsEvaluables(items);
		Component component =  createContentWrapper(tabla);
		component.addStyleName("top10-revenue");
		return component;
	}

	/**
	 * @author David
	 * @return
	 */
	private CachingMutableLocalEntityProvider<ItemEvaluable> buldEntityProvider() {
		// We need a factory to create entity manager
		Map<String, String> properties = new HashMap<String, String>();
		  properties.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
		  properties.put("javax.persistence.jdbc.url","jdbc:mysql://localhost:3306/appEducacional");
		  properties.put("javax.persistence.jdbc.user","rootApp");
		  properties.put("javax.persistence.jdbc.password","root123");

		  properties.put("hibernate.format_sql","true");
		  properties.put("hibernate.show_sql","false");
		  properties.put("hibernate.hbm2ddl.auto","verify");
		  properties.put("hibernate.cglib.use_reflection_optimizer","true");
		EntityManagerFactory emf =
		    Persistence.createEntityManagerFactory("appEducacional",properties);

		        
		// We need an entity manager to create entity provider
		EntityManager em = emf.createEntityManager();
		        
		// We need an entity provider to create a container        
		CachingMutableLocalEntityProvider<ItemEvaluable> entityProvider =
		    new CachingMutableLocalEntityProvider<ItemEvaluable>(ItemEvaluable.class,
		                                                  em);
		return entityProvider;
	}

	private Component buildPopularMovies() {
		return createContentWrapper(new TopSixTheatersChart());
	}
	
	private Component buildTop10TitlesByRevenue() {
		Component contentWrapper = createContentWrapper(new TopTenMoviesTable());
		contentWrapper.addStyleName("top10-revenue");
		return contentWrapper;
	}

	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("appeducacional-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("appeducacional-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1943034390434178282L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!slot.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot, true);
				} else {
					slot.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot, false);
				}
			}
		});
		max.setStyleName("icon-only");
		MenuItem root = tools.addItem("", FontAwesome.COG, null);
		root.addItem("Configure", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6850093122298654078L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});
		root.addSeparator();
		root.addItem("Close", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2993982684259298243L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}
	
	private void toggleMaximized(final Component panel, final boolean maximized) {
		for (Iterator<Component> it = panel.getParent().getParent().iterator(); it.hasNext();) {
			it.next().setVisible(!maximized);
		}
		dashboardPanels.setVisible(true);

		for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
			Component c = it.next();
			c.setVisible(!maximized);
		}

		if (maximized) {
			panel.setVisible(true);
			panel.addStyleName("max");
		} else {
			panel.removeStyleName("max");
		}
	}
}
