/**
 * Notificaciones.java
 * appEducacionalVaadin
 * 21/1/2015 19:49:32
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.notificaciones.view;

import java.util.List;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;
import org.vaadin.alump.lazylayouts.LazyComponentProvider;
import org.vaadin.alump.lazylayouts.LazyComponentRequestEvent;
import org.vaadin.alump.lazylayouts.LazyVerticalLayout;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class NotificacionesView extends CssLayout implements View {

	protected LazyVerticalLayout layout;
	
	protected NotificacionesPresenter presenter;
	
	protected Panel panel;
	

	public NotificacionesView(){
		presenter = NotificacionesPresenter.getInstance();
		panel = new Panel();
		layout = new LazyVerticalLayout();
	}
	
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
		initializeView();
		setHeightUndefined();
	}

	/**
	 * @author David
	 * @param t
	 */
	protected Component createNotificacionLayout(Notificacion t) {
		ThemeResource photoResource = new ThemeResource(
				"img/profile-pic-300px.jpg");
		Image img = new Image("",photoResource);
		img.addStyleName("v-icon");
		img.setWidth(40.0f, Unit.PIXELS);
		img.setHeight(100,Unit.PERCENTAGE);
		Label emisor = new Label(t.getPadreMadreOTutor().getNombre()+" "+t.getPadreMadreOTutor().getApellidos());
		emisor.addStyleName(ValoTheme.LABEL_H4);
		
		String tituloCaption = t.getTitulo().length() > 40 ? t.getTitulo().substring(0,40) : t.getTitulo();
		PrettyTime p = new PrettyTime(new Locale("es", "ES"));
		Label titulo = new Label(tituloCaption);
		titulo.addStyleName(ValoTheme.LABEL_H3);
		Label timeAgo = new Label(p.format(t.getFecha()));
		timeAgo.addStyleName(ValoTheme.LABEL_H4);
		HorizontalLayout header = new HorizontalLayout(img,emisor, titulo,timeAgo);
		header.setComponentAlignment(emisor, Alignment.MIDDLE_CENTER);
		header.setComponentAlignment(img, Alignment.MIDDLE_LEFT);
		header.setComponentAlignment(titulo, Alignment.MIDDLE_RIGHT);
		header.setComponentAlignment(timeAgo, Alignment.MIDDLE_RIGHT);
		header.setExpandRatio(img, 1f);
		header.setExpandRatio(emisor, 3f);
		header.setExpandRatio(titulo, 7f);
		header.setExpandRatio(timeAgo, 3f);
		//header.addStyleName(ValoTheme.LAYOUT_CARD);
		header.setMargin(false);
		header.setSizeFull();
		header.setSpacing(false);
		Label contenido = new Label(t.getContenido());
		contenido.setContentMode(ContentMode.HTML);
		HorizontalLayout body = new HorizontalLayout(contenido);
		body.setSizeFull();
		body.setHeight("100px");
		body.setVisible(false);
		VerticalLayout layoutNotificacion = new VerticalLayout(header,body);
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
					Animator.animate(body, new Css().translateY("-20px")).delay(50).duration(2000);
				}else{
					body.setVisible(true);
					Animator.animate(body, new Css().translateY("20px")).delay(50).duration(2000);
				}
			}
		});
		return layoutNotificacion;
	}
	
	private void initializeView(){
		setSizeFull();
		panel.setSizeFull();
		Component c = createHeaderToolbar();
		addComponent(c);
		addComponent(panel);
		// Make sure layout is inside scrolling parent (Panel or Window)
		panel.setContent(layout);
		// Enable lazy loading by providing provider
		layout.enableLazyLoading(new LazyComponentProvider() {
		  @Override
		public void onLazyComponentRequest(LazyComponentRequestEvent event) {

		    // Load more data and add UI presentation of those to layout
		    List<Notificacion> more = presenter.obtenerMasNotificaciones();
		    for(Notificacion notificacion : more) {
		       event.getComponentContainer().addComponent(createNotificacionLayout(notificacion));
		    }

		    // Disable lazy loading request when you run out of data
		    if(!presenter.existenMasNotificaciones()) {
		      event.getComponentContainer().disableLazyLoading();
		    }
		  }
		});
	}

	/**
	 * @author David
	 */
	private Component createHeaderToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
		header.setWidth(100,Unit.PERCENTAGE);
		Button showAll = new Button("",
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
		showAll.setIcon(FontAwesome.REFRESH);
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
		return header;
		
	}

	
	
}
