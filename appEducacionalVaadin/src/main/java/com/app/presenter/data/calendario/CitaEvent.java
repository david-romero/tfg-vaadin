/**
* CitaEvent.java
* appEducacionalVaadin
* 28/12/2014 15:45:52
* Copyright David
* com.app.presenter.data.calendario
*/
package com.app.presenter.data.calendario;

import java.util.Date;

import com.app.domain.model.types.Cita;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

/**
 * @author David
 *
 */
public class CitaEvent implements CalendarEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6181216578066766443L;
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
        return cita.getContenido();
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
