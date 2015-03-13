/**
 * Notificaciones.java
 * appEducacionalVaadin
 * 21/1/2015 19:49:32
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.notificaciones.view;

import java.util.List;

import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import com.app.domain.model.types.Notificacion;
import com.app.ui.AppUI;
import com.app.ui.user.notificaciones.presenter.NotificacionesPresenter;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class NotificacionesView extends CssLayout implements View {

	private List<Notificacion> notificaciones;

	
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
		setHeightUndefined();
		if ( notificaciones == null ){
			initializeView();
		}
		
		for ( Notificacion t : notificaciones ){
			ThemeResource photoResource = new ThemeResource(
					"img/profile-pic-300px.jpg");
			Image img = new Image("",photoResource);
			img.addStyleName("v-icon");
			img.setWidth(40.0f, Unit.PIXELS);
			img.setHeight(100,Unit.PERCENTAGE);
			Label emisor = new Label(t.getPadreMadreOTutor().getNombre()+" "+t.getPadreMadreOTutor().getApellidos());
			emisor.addStyleName(ValoTheme.LABEL_H4);
			
			
			Label titulo = new Label(t.getTitulo());
			titulo.addStyleName(ValoTheme.LABEL_H3);
			HorizontalLayout header = new HorizontalLayout(img,emisor, titulo);
			header.setComponentAlignment(emisor, Alignment.MIDDLE_CENTER);
			header.setComponentAlignment(img, Alignment.MIDDLE_LEFT);
			header.setComponentAlignment(titulo, Alignment.MIDDLE_RIGHT);
			header.setExpandRatio(img, 1f);
			header.setExpandRatio(emisor, 3f);
			header.setExpandRatio(titulo, 7f);
			//header.addStyleName(ValoTheme.LAYOUT_CARD);
			header.setMargin(false);
			header.setSizeFull();
			header.setSpacing(false);
			Label contenido = new Label(t.getContenido());
			contenido.setContentMode(ContentMode.HTML);
			HorizontalLayout body = new HorizontalLayout(contenido);
			body.setSizeFull();
			body.setVisible(false);
			VerticalLayout layout = new VerticalLayout(header,body);
			addComponent(layout);
			header.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
			header.addLayoutClickListener(new LayoutClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -1749687906071209879L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (body.isVisible()){
						body.setVisible(false);
						Animator.animate(body, new Css().translateY("-100px")).delay(0).duration(2000);
					}else{
						body.setVisible(true);
						Animator.animate(body, new Css().translateY("100px")).delay(0).duration(2000);
					}
				}
			});
		}

	}
	
	private void initializeView(){
		NotificacionesPresenter presenter = new NotificacionesPresenter();
		notificaciones = presenter.getNotificacines();
		setSizeFull();
		createHeaderToolbar(presenter);
	}

	/**
	 * @author David
	 */
	private void createHeaderToolbar(NotificacionesPresenter presenter) {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
		header.setWidth(100,Unit.PERCENTAGE);
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
		add.addClickListener(presenter);
				
		add.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		add.addStyleName(ValoTheme.BUTTON_SMALL);
		header.addComponent(showAll);
		header.addComponent(add);
		header.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
		header.setHeightUndefined();
		addComponent(header);
		
	}

	
	
}
