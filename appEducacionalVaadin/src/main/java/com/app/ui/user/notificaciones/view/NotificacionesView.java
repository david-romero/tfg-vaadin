/**
 * Notificaciones.java
 * appEducacionalVaadin
 * 21/1/2015 19:49:32
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.notificaciones.view;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.Persona;
import com.app.ui.AppUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class NotificacionesView extends GridLayout implements View {

	private List<Notificacion> notificaciones;

	private Integer contenedorFilas ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6600627527098656615L;



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		if ( notificaciones == null ){
			initializeView();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.AbstractComponent#attach()
	 */
	@Override
	public void attach() {
		super.attach();
		if ( notificaciones == null ){
			initializeView();
		}
		notificaciones.forEach(new Consumer<Notificacion>() {

			@Override
			public void accept(Notificacion t) {
				
				Label fecha = new Label(t.getFecha().toString());
				Label titulo = new Label(t.getTitulo());
				HorizontalLayout header = new HorizontalLayout(fecha,titulo);
				header.setSizeFull();
				TextArea contenido = new TextArea(t.getContenido());
				contenido.setReadOnly(true);
				HorizontalLayout body = new HorizontalLayout(contenido);
				body.setSizeFull();
				Accordion accordion = new Accordion(header,body);
				accordion.setSizeFull();
				addComponent(accordion, 0, contenedorFilas);
				contenedorFilas++;
			}
		});
	}
	
	private void initializeView(){
		NotificacionesPresenter presenter = new NotificacionesPresenter();
		notificaciones = presenter.getNotificacines();
		contenedorFilas = 0;
		setColumns(1);
		setRows(notificaciones.size()+1);
		setSizeFull();
		createHeaderToolbar();
	}

	/**
	 * @author David
	 */
	private void createHeaderToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
		header.setWidth("100%");
		Button showAll = new Button("View All Notifications",
				new ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 5594631503767611801L;

					@Override
					public void buttonClick(final ClickEvent event) {
						AppUI.getCurrent().getNavigator().navigateTo("notificaciones");
					}
				});
		showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		showAll.addStyleName(ValoTheme.BUTTON_SMALL);
		Button add = new Button("",FontAwesome.PLUS);
		add.addClickListener(e->{
			NotificacionCreateWindow window = new NotificacionCreateWindow();
			AppUI.getCurrent().addWindow(window);
		});
				
		add.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		add.addStyleName(ValoTheme.BUTTON_SMALL);
		header.addComponent(showAll);
		header.addComponent(add);
		header.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
		addComponent(header, 0 , contenedorFilas);
		contenedorFilas++;
		
	}

	private class NotificacionCreateWindow extends Window{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1299573468494488377L;
		
	}
	
}
