/**
* CitaLayout.java
* appEducacionalVaadin
* 30/3/2015 17:47:31
* Copyright David
* com.app.ui.user.citas.view
*/
package com.app.ui.user.citas.view;

import java.util.Date;

import com.app.domain.model.types.Cita;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.infrastructure.exceptions.GeneralException;
import com.app.ui.user.calendario.presenter.CalendarioPresenter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class CitaLayout extends VerticalLayout {

	@PropertyId("titulo")
	private TextField titulo;
	@PropertyId("contenido")
	private RichTextArea contenido;
	@PropertyId("padreMadreOTutor")
	private ComboBox combo;
	@PropertyId("fecha")
	private DateField datepicker;
	
	private Cita cita;
	
	private CalendarioPresenter presenter;

	private final BeanFieldGroup<Cita> fieldGroup;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1299573468494488377L;

	public CitaLayout(Cita cita) {
		this.cita = cita;
		this.presenter = CalendarioPresenter.getInstance();
		Responsive.makeResponsive(this);

		setSizeFull();
		VerticalLayout content = new VerticalLayout();
		content.addComponent(buildForm());
		content.addComponent(buildFooter());
		
		addComponent(content);
		
		fieldGroup = new BeanFieldGroup<Cita>(Cita.class);
		fieldGroup.bindMemberFields(this);
		fieldGroup.setItemDataSource(cita);
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

					presenter.save(cita,com.app.domain.model.types.Profesor.class);

					Notification success = new Notification(
							"Cita creada satisfactoriamente");
					success.setDelayMsec(2000);
					success.setStyleName("bar success small");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());
					
				} catch (CommitException | GeneralException e) {
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

			}
		});
		HorizontalLayout footersButtons = new HorizontalLayout();
		footersButtons.addComponent(ok);
		footersButtons.addComponent(discard);
		footersButtons.setSpacing(true);
		footer.addComponent(footersButtons);
		footer.setComponentAlignment(footersButtons, Alignment.BOTTOM_RIGHT);
		return footer;
	}

	/**
	 * @author David
	 */
	private Component buildForm() {
		VerticalLayout root = new VerticalLayout();
		root.setSpacing(true);
		root.setMargin(true);
		root.setSizeFull();
		root.addComponent(generateHeader());
		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		titulo = new TextField("Título");
		details.addComponent(titulo);
		datepicker = new DateField("Fecha", cita.getFecha());
		datepicker.setSizeFull();
		datepicker.setRangeStart(new Date());
		datepicker.setResolution(Resolution.MINUTE);
		datepicker.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(datepicker);
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

	/**
	 * @author David
	 * @return
	 */
	private Component generateHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
		header.setWidth(100,Unit.PERCENTAGE);
		Label etiqueta = new Label("Nueva Cita");
		etiqueta.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		etiqueta.addStyleName(ValoTheme.BUTTON_SMALL);
		Label icon = new Label(FontAwesome.CALENDAR.getHtml());
		icon.setContentMode(ContentMode.HTML);		
		icon.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		icon.addStyleName(ValoTheme.BUTTON_SMALL);
		header.addComponent(etiqueta);
		header.addComponent(icon);
		header.setComponentAlignment(icon, Alignment.MIDDLE_RIGHT);
		header.setHeightUndefined();
		return header;
	}

}

