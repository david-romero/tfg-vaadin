/**
* EventsProvider.java
* appEducacionalVaadin
* 28/12/2014 15:50:11
* Copyright David
* com.app.presenter.data.calendario
*/
package com.app.presenter.data.calendario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Profesor;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

/**
 * @author David
 *
 */
public class EventsProvider implements CalendarEventProvider{

	/**
	 * Constructor
	 */
	public EventsProvider() {
		super();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000287512687795188L;

	@Override
    public List<CalendarEvent> getEvents(final Date startDate,
            final Date endDate) {
        // Transactions are dynamically fetched from the backend service
        // when needed.
        Collection<Cita> transactions = Lists.newArrayList();
        List<CalendarEvent> result = new ArrayList<CalendarEvent>();
        for (Cita cita : transactions) {
        	Date end = new Date(cita.getFecha().getTime()+1000);
            result.add(new CitaEvent(cita.getFecha(), end , cita));
        }
        return result;
    }
	
}
