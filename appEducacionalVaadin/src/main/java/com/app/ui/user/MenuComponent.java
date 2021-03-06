/**
 * MenuComponent.java
 * appEducacionalVaadin
 * 19/12/2014 23:55:58
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Persona;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.event.AppEducacionalEvent.NotificationsCountUpdatedEvent;
import com.app.presenter.event.AppEducacionalEvent.PostViewChangeEvent;
import com.app.presenter.event.AppEducacionalEvent.ProfileUpdatedEvent;
import com.app.presenter.event.AppEducacionalEvent.ReportsCountUpdatedEvent;
import com.app.presenter.event.AppEducacionalEvent.UserLoggedOutEvent;
import com.app.presenter.event.AppEducacionalEventBus;
import com.app.ui.AppUI;
import com.google.common.eventbus.Subscribe;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class MenuComponent extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078384311201893042L;

	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private static final String STYLE_VISIBLE = "valo-menu-visible";
	private Label notificationsBadge;
	private Label reportsBadge;
	private MenuItem settingsItem;

	private ApplicationContext applicationContext;

	private PadreMadreOTutorService tutorService;

	private ProfesorService profesorService;

	private AdministradorService adminService;

	public MenuComponent() {
		addStyleName("valo-menu");
		setId(ID);
		setSizeUndefined();

		// There's only one DashboardMenu per UI so this doesn't need to be
		// unregistered from the UI-scoped DashboardEventBus.
		// DashboardEventBus.register(this);

		loadBeans();

		setCompositionRoot(buildContent());

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
	}

	private Component buildContent() {
		final CssLayout menuContent = new CssLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.addStyleName("no-vertical-drag-hints");
		menuContent.addStyleName("no-horizontal-drag-hints");
		menuContent.setWidth(null);
		menuContent.setHeight("100%");

		menuContent.addComponent(buildTitle());
		menuContent.addComponent(buildUserMenu());
		menuContent.addComponent(buildToggleButton());
		menuContent.addComponent(buildMenuItems());

		return menuContent;
	}

	private Component buildTitle() {
		Label logo = new Label("Guardians <strong>Dashboard</strong>",
				ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		return logoWrapper;
	}

	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		final Persona user = getCurrentUser();
		if (user.getImagen() != null && user.getImagen().length > 0) {
			com.vaadin.server.StreamResource source;
			StreamSource source2 = new StreamResource.StreamSource() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3823582436185258502L;

				public InputStream getStream() {
					InputStream reportStream = null;
					reportStream = new ByteArrayInputStream(user.getImagen());
					return reportStream;
				}
			};
			source = new StreamResource(source2, "profile-picture.png");
			settingsItem = settings.addItem("", source, null);
		} else {
			settingsItem = settings.addItem("", new ThemeResource(
					"img/profile-pic-300px.jpg"), null);
		}
		updateUserName(null);
		settingsItem.addItem("Editar Perfil", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5784771381981479180L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				ProfilePreferencesWindow.open(user, false);
			}
		});
		settingsItem.addItem("Preferiencias", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5102562606395914017L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				ProfilePreferencesWindow.open(user, true);
			}
		});
		settingsItem.addSeparator();
		settingsItem.addItem("Sign Out", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1740304688423596121L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				AppEducacionalEventBus.post(new UserLoggedOutEvent());
			}
		});
		return settings;
	}

	/**
	 * @author David
	 * @return
	 */
	private Persona getCurrentUser() {
		UserAccount account = AppUI.getCurrentUser();
		switch (Lists.newArrayList(account.getAuthorities()).get(0)
				.getAuthority()) {
		case Authority.PROFESOR:
			return profesorService.findByUserAccount(account);
		case Authority.TUTOR:
			return tutorService.findByUserAccount(account);
		case Authority.ADMINISTRADOR:
			return adminService.findByUserAccount(account);

		default:
			break;
		}
		return null;
	}

	private Component buildToggleButton() {
		Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5668303758895705951L;

			@Override
			public void buttonClick(final ClickEvent event) {
				if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
					getCompositionRoot().removeStyleName(STYLE_VISIBLE);
				} else {
					getCompositionRoot().addStyleName(STYLE_VISIBLE);
				}
			}
		});
		valoMenuToggleButton.setIcon(FontAwesome.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		return valoMenuToggleButton;
	}

	private Component buildMenuItems() {
		CssLayout menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");
		menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

		for (final UserMenuHelper view : UserMenuHelper.values()) {
			Authority auth = Lists.newArrayList(
					getCurrentUser().getUserAccount().getAuthorities()).get(0);
			boolean tienePermisos = view.getRoles().contains(auth);
			if (tienePermisos) {
				Component menuItemComponent = new ValoMenuItemButton(view);

				if (view == UserMenuHelper.REPORTS) {
					// Add drop target to reports button
					DragAndDropWrapper reports = new DragAndDropWrapper(
							menuItemComponent);
					reports.setDragStartMode(DragStartMode.NONE);
					reports.setDropHandler(new DropHandler() {

						/**
					 * 
					 */
						private static final long serialVersionUID = -3509580222538356821L;

						@Override
						public void drop(final DragAndDropEvent event) {
							UI.getCurrent()
									.getNavigator()
									.navigateTo(
											UserMenuHelper.REPORTS
													.getViewName());
							Table table = (Table) event.getTransferable()
									.getSourceComponent();

						}

						@Override
						public AcceptCriterion getAcceptCriterion() {
							return AcceptItem.ALL;
						}

					});
					menuItemComponent = reports;
				}

				if (view == UserMenuHelper.PANELCONTROL) {
					notificationsBadge = new Label();
					notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
					menuItemComponent = buildBadgeWrapper(menuItemComponent,
							notificationsBadge);
				}
				if (view == UserMenuHelper.REPORTS) {
					reportsBadge = new Label();
					reportsBadge.setId(REPORTS_BADGE_ID);
					menuItemComponent = buildBadgeWrapper(menuItemComponent,
							reportsBadge);
				}

				menuItemsLayout.addComponent(menuItemComponent);
			}
		}
		return menuItemsLayout;

	}

	private Component buildBadgeWrapper(final Component menuItemButton,
			final Component badgeLabel) {
		CssLayout dashboardWrapper = new CssLayout(menuItemButton);
		dashboardWrapper.addStyleName("badgewrapper");
		dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
		dashboardWrapper.setWidth(100.0f, Unit.PERCENTAGE);
		badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
		badgeLabel.setWidthUndefined();
		badgeLabel.setVisible(false);
		dashboardWrapper.addComponent(badgeLabel);
		return dashboardWrapper;
	}

	@Override
	public void attach() {
		super.attach();
		updateNotificationsCount(null);
	}

	@Subscribe
	public void postViewChange(final PostViewChangeEvent event) {
		// After a successful view change the menu can be hidden in mobile view.
		getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	}

	@Subscribe
	public void updateNotificationsCount(
			final NotificationsCountUpdatedEvent event) {

		int unreadNotificationsCount = 5;
		notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
		notificationsBadge.setVisible(unreadNotificationsCount > 0);
	}

	@Subscribe
	public void updateReportsCount(final ReportsCountUpdatedEvent event) {
		reportsBadge.setValue(String.valueOf(event.getCount()));
		reportsBadge.setVisible(event.getCount() > 0);
	}

	@Subscribe
	public void updateUserName(final ProfileUpdatedEvent event) {
		Persona user = getCurrentUser();
		settingsItem.setText(user.getUserAccount().getUsername());
	}

	public final class ValoMenuItemButton extends Button {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5684186527583637546L;

		private static final String STYLE_SELECTED = "selected";

		private final UserMenuHelper view;

		public ValoMenuItemButton(final UserMenuHelper view) {
			this.view = view;
			setPrimaryStyleName("valo-menu-item");
			setIcon(view.getIcon());
			setCaption(view.getViewName().substring(0, 1).toUpperCase()
					+ view.getViewName().substring(1));
			AppEducacionalEventBus.register(this);
			addClickListener(new ClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3156699731388657209L;

				@Override
				public void buttonClick(final ClickEvent event) {
					UI.getCurrent().getNavigator()
							.navigateTo(view.getViewName());
				}
			});

		}

		@Subscribe
		public void postViewChange(final PostViewChangeEvent event) {
			removeStyleName(STYLE_SELECTED);
			if (event.getView() == view) {
				addStyleName(STYLE_SELECTED);
			}
		}
	}
}
