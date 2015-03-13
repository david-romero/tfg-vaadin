/**
* UserMenuHelper.java
* appEducacionalVaadin
* 22/12/2014 22:30:04
* Copyright David
* com.app.ui.user
*/
package com.app.ui.user;

import com.app.ui.user.calendario.CalendarioView;
import com.app.ui.user.calificaciones.CalificacionesView;
import com.app.ui.user.informes.InformesView;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.app.ui.user.profesor.ProfesorView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * @author David
 *
 */
public enum UserMenuHelper {

	PANELCONTROL("Panel de Control", PanelControlView.class, FontAwesome.HOME, true), 
	SALES("sales", ProfesorView.class, FontAwesome.BAR_CHART_O, false), 
	TRANSACTIONS("transactions", ProfesorView.class, FontAwesome.TABLE, false), 
	REPORTS("Informes", InformesView.class, FontAwesome.FILE_TEXT_O, true),
	SCHEDULE("calendario", CalendarioView.class, FontAwesome.CALENDAR_O, false),
	CALIFICACIONES("Calificaciones", CalificacionesView.class, FontAwesome.EDIT, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private UserMenuHelper(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static UserMenuHelper getByViewName(final String viewName) {
    	UserMenuHelper result = null;
        for (UserMenuHelper viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
	
}
