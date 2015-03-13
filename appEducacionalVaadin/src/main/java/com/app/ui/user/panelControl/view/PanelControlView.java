/**
 * PanelControlView.java
 * appEducacionalVaadin
 * 11/1/2015 19:59:07
 * Copyright David
 * com.app.ui.user.panelControl
 */
package com.app.ui.user.panelControl.view;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
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
import com.app.presenter.event.AppEducacionalEvent.CloseOpenWindowsEvent;
import com.app.presenter.event.AppEducacionalEvent.NotificationsCountUpdatedEvent;
import com.app.presenter.event.AppEducacionalEventBus;
import com.app.ui.AppUI;
import com.app.ui.components.SparklineChart;
import com.app.ui.components.TopGrossingMoviesChart;
import com.app.ui.components.TopSixTheatersChart;
import com.app.ui.components.TopTenMoviesTable;
import com.app.ui.user.panelControl.presenter.PanelControlPresenter;
import com.google.common.eventbus.Subscribe;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class PanelControlView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3179977221799819013L;
	public static final String EDIT_ID = "dashboard-edit";
	public static final String TITLE_ID = "dashboard-title";

	private static PanelControlPresenter presenter;

	private Label titleLabel;
	private NotificationsButton notificationsButton;
	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private Window notificationsWindow;

	public PanelControlView() {
		presenter = PanelControlPresenter.getInstance();
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		AppEducacionalEventBus.register(this);

		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("appeducacional-view");
		setContent(root);
		Responsive.makeResponsive(root);

		root.addComponent(buildHeader());

		root.addComponent(buildSparklines());

		Component content = buildContent();
		root.addComponent(content);
		root.setExpandRatio(content, 1);

		// All the open sub-windows should be closed whenever the root layout
		// gets clicked.
		root.addLayoutClickListener(new LayoutClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7042531802982092911L;

			@Override
			public void layoutClick(final LayoutClickEvent event) {
				AppEducacionalEventBus.post(new CloseOpenWindowsEvent());
			}
		});
	}

	private Component buildSparklines() {
		CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");
        Responsive.makeResponsive(sparks);

        SparklineChart s = new SparklineChart("Traffic", "K", "",
        		new SolidColor("#3090F0"), 22, 20, 80);
        sparks.addComponent(s);

        s = new SparklineChart("Revenue / Day", "M", "$",
        		new SolidColor("#98DF58"), 8, 89, 150);
        sparks.addComponent(s);

        s = new SparklineChart("Checkout Time", "s", "",
        		new SolidColor("#F9DD51"), 10, 30, 120);
        sparks.addComponent(s);

        s = new SparklineChart("Theater Fill Rate", "%", "",
        		 new SolidColor("#EC6464"), 50, 34, 100);
        sparks.addComponent(s);

        return sparks;
	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);

		titleLabel = new Label("Panel de Control");
		titleLabel.setId(TITLE_ID);
		titleLabel.setSizeUndefined();
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
		titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(titleLabel);

		notificationsButton = buildNotificationsButton();
		HorizontalLayout tools = new HorizontalLayout(notificationsButton);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private NotificationsButton buildNotificationsButton() {
		NotificationsButton result = new NotificationsButton();
		result.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7959321831705470982L;

			@Override
			public void buttonClick(final ClickEvent event) {
				openNotificationsPopup(event);
			}
		});
		return result;
	}


	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("appeducacional-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(buildTopGrossingMovies());
		dashboardPanels.addComponent(buildNotes());
		dashboardPanels.addComponent(buildTop10TitlesByRevenue());
		dashboardPanels.addComponent(buildPopularMovies());

		return dashboardPanels;
	}

	private Component buildTopGrossingMovies() {
		TopGrossingMoviesChart topGrossingMoviesChart = new TopGrossingMoviesChart();
		topGrossingMoviesChart.setSizeFull();
		return createContentWrapper(topGrossingMoviesChart);
	}

	private Component buildNotes() {
		TextArea notes = new TextArea("Notes");
		String value = "Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater";
		if (!(presenter.getCurrentPerson().getNotas() == null || presenter.getCurrentPerson().getNotas().length() == 0) )
			value = presenter.getCurrentPerson().getNotas();
		notes.setValue(value);
		notes.setSizeFull();
		notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		Component panel = createContentWrapper(notes);
		panel.addStyleName("notes");
		return panel;
	}

	private Component buildTop10TitlesByRevenue() {
		Component contentWrapper = createContentWrapper(new TopTenMoviesTable());
		contentWrapper.addStyleName("top10-revenue");
		return contentWrapper;
	}

	private Component buildPopularMovies() {
		return createContentWrapper(new TopSixTheatersChart());
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
		if ( content instanceof TextArea ){
			MenuItem save = tools.addItem("", FontAwesome.SAVE,new Command() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
					String notes = ((TextArea)content).getValue();
					Persona p = presenter.getCurrentPerson();
					p.setNotas(notes);
					presenter.savePerson(p);
				}
			});
			save.setStyleName("icon-only");
		}
		
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

	private void openNotificationsPopup(final ClickEvent event) {
		VerticalLayout notificationsLayout = new VerticalLayout();
		notificationsLayout.setMargin(true);
		notificationsLayout.setSpacing(true);

		Label title = new Label("Notifications");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		notificationsLayout.addComponent(title);
		List<Notificacion> notifications = presenter.getListNotificacionesNoLeidas();

		AppEducacionalEventBus.post(new NotificationsCountUpdatedEvent());

		for (Notificacion notification : notifications) {
			VerticalLayout notificationLayout = new VerticalLayout();
			notificationLayout.addStyleName("notification-item");

			Label titleLabel = new Label(notification.getEmisor() + " "
					+ notification.getFecha() + " "
					+ notification.getContenido());
			titleLabel.addStyleName("notification-title");
			java.text.SimpleDateFormat sp = new java.text.SimpleDateFormat(
					"dd/MM/yyyy");
			Label timeLabel = new Label(sp.format(notification.getFecha()));
			timeLabel.addStyleName("notification-time");

			Label contentLabel = new Label(notification.getContenido());
			contentLabel.addStyleName("notification-content");
			contentLabel.setContentMode(ContentMode.HTML);

			notificationLayout.addComponents(titleLabel, timeLabel,
					contentLabel);
			notificationsLayout.addComponent(notificationLayout);
		}

		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth("100%");
		Button showAll = new Button("View All Notifications",
				new ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 5594631503767611801L;

					@Override
					public void buttonClick(final ClickEvent event) {
						notificationsWindow.close();
						AppUI.getCurrent().getNavigator().navigateTo("notificaciones");
					}
				});
		showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		showAll.addStyleName(ValoTheme.BUTTON_SMALL);
		footer.addComponent(showAll);
		footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
		notificationsLayout.addComponent(footer);

		if (notificationsWindow == null) {
			notificationsWindow = new Window();
			notificationsWindow.setWidth(300.0f, Unit.PIXELS);
			notificationsWindow.addStyleName("notifications");
			notificationsWindow.setClosable(false);
			notificationsWindow.setResizable(false);
			notificationsWindow.setDraggable(false);
			notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
			notificationsWindow.setContent(notificationsLayout);
		}

		if (!notificationsWindow.isAttached()) {
			notificationsWindow.setPositionY(event.getClientY()
					- event.getRelativeY() + 40);
			getUI().addWindow(notificationsWindow);
			notificationsWindow.focus();
		} else {
			notificationsWindow.close();
		}
	}

	@Override
	public void enter(final ViewChangeEvent event) {
		notificationsButton.updateNotificationsCount(null);
	}

	/*
	 * @Override public void dashboardNameEdited(final String name) {
	 * titleLabel.setValue(name); }
	 */

	private void toggleMaximized(final Component panel, final boolean maximized) {
		for (Iterator<Component> it = root.iterator(); it.hasNext();) {
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

	public static final class NotificationsButton extends Button {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2993136376885547514L;
		private static final String STYLE_UNREAD = "unread";
		public static final String ID = "dashboard-notifications";

		public NotificationsButton() {
			setIcon(FontAwesome.BELL);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			AppEducacionalEventBus.register(this);
		}

		@Subscribe
		public void updateNotificationsCount(
				final NotificationsCountUpdatedEvent event) {
			setUnreadCount(presenter.getNotificacionesNoLeidas());
		}

		public void setUnreadCount(final int count) {
			setCaption(String.valueOf(count));

			String description = "Notifications";
			if (count > 0) {
				addStyleName(STYLE_UNREAD);
				description += " (" + count + " unread)";
			} else {
				removeStyleName(STYLE_UNREAD);
			}
			setDescription(description);
		}
	}

}
