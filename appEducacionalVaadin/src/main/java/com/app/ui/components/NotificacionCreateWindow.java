/**
 * NotificacionCreateWindow.java
 * appEducacionalVaadin
 * 24/1/2015 0:18:38
 * Copyright David
 * com.app.ui.components
 */
package com.app.ui.components;

import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.ui.user.notificaciones.presenter.NotificacionesPresenter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class NotificacionCreateWindow extends Window {

	@PropertyId("titulo")
	private TextField titulo;
	@PropertyId("contenido")
	private RichTextArea contenido;
	@PropertyId("padreMadreOTutor")
	private ComboBox combo;
	
	private Notificacion notificacion;
	
	private NotificacionesPresenter presenter;

	private final BeanFieldGroup<Notificacion> fieldGroup;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1299573468494488377L;

	public NotificacionCreateWindow(Notificacion notificacion,NotificacionesPresenter presenter) {
		this.notificacion = notificacion;
		this.presenter = presenter;
		Responsive.makeResponsive(this);

		

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setHeight(85.0f, Unit.PERCENTAGE);
		setWidth(55, Unit.PERCENTAGE);
		VerticalLayout content = new VerticalLayout();
		content.addComponent(buildForm());
		content.addComponent(buildFooter());
		setContent(content);
		
		fieldGroup = new BeanFieldGroup<Notificacion>(Notificacion.class);
		fieldGroup.bindMemberFields(this);
		fieldGroup.setItemDataSource(notificacion);
		fieldGroup.setBuffered(true);
		
	}

	/**
	 * @author David
	 * @return
	 */
	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -296792393671130920L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();

					presenter.save(notificacion,com.app.domain.model.types.Profesor.class);

					Notification success = new Notification(
							"Profile updated successfully");
					success.setDelayMsec(2000);
					success.setStyleName("bar success small");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());

					close();
				} catch (CommitException e) {
					Notification.show("Error while updating profile",
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();

		Button discard = new Button("Cancelar");
		discard.addStyleName(ValoTheme.BUTTON_PRIMARY);
		discard.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2506358446761353801L;

			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();

				close();

			}
		});
		HorizontalLayout footersButtons = new HorizontalLayout();
		footersButtons.addComponent(ok);
		footersButtons.addComponent(discard);
		footersButtons.setSpacing(true);
		footer.addComponent(footersButtons);
		footer.setComponentAlignment(footersButtons, Alignment.TOP_RIGHT);
		return footer;
	}

	/**
	 * @author David
	 */
	private Component buildForm() {
		VerticalLayout root = new VerticalLayout();
		root.setCaption("Notificación");
		root.setIcon(FontAwesome.PAPER_PLANE);
		root.setSpacing(true);
		root.setMargin(true);
		root.setSizeFull();
		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		titulo = new TextField("Título");
		details.addComponent(titulo);
		contenido = new RichTextArea("Contenido");
		contenido.setSizeFull();

		IndexedContainer container =  
				presenter.getContainer(PadreMadreOTutor.class);
		
		combo = new ComboBox("Tutor", container);
		combo.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		combo.addStyleName(ValoTheme.COMBOBOX_LARGE);
			
		details.addComponent(combo);
		
		details.addComponent(contenido);
		return root;
	}

}
