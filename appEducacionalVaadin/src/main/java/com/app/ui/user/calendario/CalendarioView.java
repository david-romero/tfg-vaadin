/**
* CalendarioView.java
* appEducacionalVaadin
* 27/12/2014 14:13:33
* Copyright David
* com.app.ui.user.calendario
*/
package com.app.ui.user.calendario;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.InitBinder;

import ru.xpoft.vaadin.VaadinView;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.event.AppEducacionalEvent.BrowserResizeEvent;
import com.app.presenter.event.AppEducacionalEventBus;
import com.app.ui.AppUI;
import com.app.ui.user.MenuComponent;
import com.google.common.eventbus.Subscribe;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;
import com.vaadin.ui.themes.ValoTheme;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(CalendarioView.NAME)
@SuppressWarnings("serial")
@Configurable(preConstruction = true)
public final class CalendarioView extends CssLayout implements View {

    private Calendar calendar;
    private Component tray;
    
    private ApplicationContext applicationContext;
    
	private CitaService citaService;
	
	private ProfesorService profesorService;
	
	private PadreMadreOTutorService tutorService;
    
    public static final String NAME = "calendario";

    public CalendarioView() {
        loadBeans();
    }
    
    @Override
    public void attach(){
    	setSizeFull();
        addStyleName("schedule");
        AppEducacionalEventBus.register(this);

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();
        tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        tabs.addComponent(buildCalendarView());
        tabs.addComponent(buildCatalogView());

        addComponent(tabs);

        tray = buildTray();
        addComponent(tray);

        injectMovieCoverStyles();
    }
    
    /**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ( (AppUI) UI.getCurrent() ).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
		citaService = applicationContext.getBean(CitaService.class);
		tutorService = applicationContext.getBean(PadreMadreOTutorService.class);
	}

    @Override
    public void detach() {
        super.detach();
        // A new instance of ScheduleView is created every time it's navigated
        // to so we'll need to clean up references to it on detach.
        AppEducacionalEventBus.unregister(this);
    }

    private void injectMovieCoverStyles() {
        // Add all movie cover images as classes to CSSInject
        String styles = "";
        /*for (NotaPorEvaluacion m : AppUI.getDataProvider().getMovies()) {
            WebBrowser webBrowser = Page.getCurrent().getWebBrowser();

            String bg = "url(VAADIN/themes/" + UI.getCurrent().getTheme()
                    + "/img/event-title-bg.png), url(" + m.toString() + ")";

            // IE8 doesn't support multiple background images
            if (webBrowser.isIE() && webBrowser.getBrowserMajorVersion() == 8) {
                bg = "url(" + m.toString() + ")";
            }

            styles += ".v-calendar-event-" + m.getId()
                    + " .v-calendar-event-content {background-image:" + bg
                    + ";}";
        }*/

        Page.getCurrent().getStyles().add(styles);
    }

    private Component buildCalendarView() {
        VerticalLayout calendarLayout = new VerticalLayout();
        calendarLayout.setCaption("Calendar");
        calendarLayout.setMargin(true);

        calendar = new Calendar(new MovieEventProvider());
        calendar.setWidth(100.0f, Unit.PERCENTAGE);
        calendar.setHeight(1000.0f, Unit.PIXELS);

        calendar.setHandler(new EventClickHandler() {
            @Override
            public void eventClick(final EventClick event) {
                setTrayVisible(false);
                CitaEvent movieEvent = (CitaEvent) event.getCalendarEvent();
                /*MovieDetailsWindow.open(movieEvent.getMovie(),
                        movieEvent.getStart(), movieEvent.getEnd());*/
            }
        });
        calendarLayout.addComponent(calendar);

        calendar.setFirstVisibleHourOfDay(11);
        calendar.setLastVisibleHourOfDay(23);

        calendar.setHandler(new BasicEventMoveHandler() {
            @Override
            public void eventMove(final MoveEvent event) {
                CalendarEvent calendarEvent = event.getCalendarEvent();
                if (calendarEvent instanceof CitaEvent) {
                    CitaEvent editableEvent = (CitaEvent) calendarEvent;

                    Date newFromTime = event.getNewStart();

                    // Update event dates
                    long length = editableEvent.getEnd().getTime()
                            - editableEvent.getStart().getTime();
                    setDates(editableEvent, newFromTime,
                            new Date(newFromTime.getTime() + length));
                    setTrayVisible(true);
                }
            }

            protected void setDates(final CitaEvent event, final Date start,
                    final Date end) {
                event.start = start;
                event.end = end;
            }
        });
        calendar.setHandler(new BasicEventResizeHandler() {
            @Override
            public void eventResize(final EventResize event) {
                Notification
                        .show("You're not allowed to change the movie duration");
            }
        });

        java.util.Calendar initialView = java.util.Calendar.getInstance();
        initialView.add(java.util.Calendar.DAY_OF_WEEK,
                -initialView.get(java.util.Calendar.DAY_OF_WEEK) + 1);
        calendar.setStartDate(initialView.getTime());

        initialView.add(java.util.Calendar.DAY_OF_WEEK, 6);
        calendar.setEndDate(initialView.getTime());

        return calendarLayout;
    }

    private Component buildCatalogView() {
        CssLayout catalog = new CssLayout();
        catalog.setCaption("Catalog");
        catalog.addStyleName("catalog");
        /*
        for (final NotaPorEvaluacion movie : AppUI.getDataProvider().getMovies()) {
            VerticalLayout frame = new VerticalLayout();
            frame.addStyleName("frame");
            frame.setWidthUndefined();

            // Create an instance of our stream source.
            StreamResource.StreamSource imagesource = new StreamSource() {
				
				@Override
				public InputStream getStream() {
					InputStream myInputStream = new ByteArrayInputStream(movie.getAlumno().getImagen()); 
					return myInputStream;
				}
			};
            
            StreamResource resource = new StreamResource(imagesource, "foto");
            
            Image poster = new Image(null, resource);
            poster.setWidth(100.0f, Unit.PIXELS);
            poster.setHeight(145.0f, Unit.PIXELS);
            frame.addComponent(poster);

            Label titleLabel = new Label("Nota: " +movie.getNotaFinal());
            titleLabel.setWidth(120.0f, Unit.PIXELS);
            frame.addComponent(titleLabel);

            frame.addLayoutClickListener(new LayoutClickListener() {
                @Override
                public void layoutClick(final LayoutClickEvent event) {
                    if (event.getButton() == MouseButton.LEFT) {
                        //MovieDetailsWindow.open(movie, null, null);
                    }
                }
            });
            catalog.addComponent(frame);
        }
        */
        return catalog;
    }

    private Component buildTray() {
        final HorizontalLayout tray = new HorizontalLayout();
        tray.setWidth(100.0f, Unit.PERCENTAGE);
        tray.addStyleName("tray");
        tray.setSpacing(true);
        tray.setMargin(true);

        Label warning = new Label(
                "You have unsaved changes made to the schedule");
        warning.addStyleName("warning");
        warning.addStyleName("icon-attention");
        tray.addComponent(warning);
        tray.setComponentAlignment(warning, Alignment.MIDDLE_LEFT);
        tray.setExpandRatio(warning, 1);

        ClickListener close = new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                setTrayVisible(false);
            }
        };

        Button confirm = new Button("Confirm");
        confirm.addStyleName(ValoTheme.BUTTON_PRIMARY);
        confirm.addClickListener(close);
        tray.addComponent(confirm);
        tray.setComponentAlignment(confirm, Alignment.MIDDLE_LEFT);

        Button discard = new Button("Discard");
        discard.addClickListener(close);
        discard.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                calendar.markAsDirty();
            }
        });
        tray.addComponent(discard);
        tray.setComponentAlignment(discard, Alignment.MIDDLE_LEFT);
        return tray;
    }

    private void setTrayVisible(final boolean visible) {
        final String styleReveal = "v-animate-reveal";
        if (visible) {
            tray.addStyleName(styleReveal);
        } else {
            tray.removeStyleName(styleReveal);
        }
    }

    @Subscribe
    public void browserWindowResized(final BrowserResizeEvent event) {
        if (Page.getCurrent().getBrowserWindowWidth() < 800) {
            calendar.setEndDate(calendar.getStartDate());
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }

    private class MovieEventProvider implements CalendarEventProvider {

        @Override
        public List<CalendarEvent> getEvents(final Date startDate,
                final Date endDate) {
            // Transactions are dynamically fetched from the backend service
            // when needed.
        	UserAccount account = AppUI.getCurrentUser();
        	List<Cita> allCitas = Lists.newArrayList();
        	Collection<Cita> allCitas1 = null;
        	Collection<Cita> allCitas2 = null;
        	switch ( Lists.newArrayList(account.getAuthorities()).get(0).getAuthority()) {
			case Authority.PROFESOR:
				Profesor profesor = profesorService.findByUserAccount(account);
				allCitas1 = citaService.findProfesorEmitidas(profesor);
	    		allCitas2 = citaService.findProfesorRecibidas(profesor);
				break;
			case Authority.TUTOR:
				PadreMadreOTutor tutor = tutorService.findByUserAccount(account);
				allCitas1 = citaService.findTutorEmitidas(tutor);
	    		allCitas2 = citaService.findTutorRecibidas(tutor);
				break;

			default:
				break;
			}
   		
    		allCitas.addAll(allCitas1);
    		allCitas.addAll(allCitas2);
            List<CalendarEvent> result = new ArrayList<CalendarEvent>();
            for (Cita cita : allCitas) {
            	Date end = new Date(cita.getFecha().getTime()+1000);
                result.add(new CitaEvent(cita.getFecha(), end , cita));
            }
            return result;
        }
    }

    public final class CitaEvent implements CalendarEvent {

        private Date start;
        private Date end;
        private Cita cita;

        public CitaEvent(final Date start, final Date end, final Cita cita) {
            this.start = start;
            this.end = end;
            this.cita = cita;
        }

        @Override
        public Date getStart() {
            return start;
        }

        @Override
        public Date getEnd() {
            return end;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public String getStyleName() {
            return String.valueOf(cita.getId());
        }

        @Override
        public boolean isAllDay() {
            return false;
        }

        public Cita getCita() {
            return cita;
        }

        public void setCita(final Cita cita) {
            this.cita = cita;
        }

        public void setStart(final Date start) {
            this.start = start;
        }

        public void setEnd(final Date end) {
            this.end = end;
        }

        @Override
        public String getCaption() {
            return cita.getContenido();
        }

    }

}