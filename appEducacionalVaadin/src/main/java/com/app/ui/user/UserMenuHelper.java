/**
* UserMenuHelper.java
* appEducacionalVaadin
* 22/12/2014 22:30:04
* Copyright David
* com.app.ui.user
*/
package com.app.ui.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.jaas.AuthorityGranter;

import com.app.infrastructure.security.Authority;
import com.app.ui.user.alumnos.view.AlumnosView;
import com.app.ui.user.calendario.view.CalendarioView;
import com.app.ui.user.calificaciones.view.CalificacionesView;
import com.app.ui.user.informes.InformesView;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.app.ui.user.profesor.view.ProfesorView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * @author David
 *
 */
public enum UserMenuHelper {

	
	
	PANELCONTROL("Panel de Control", PanelControlView.class, FontAwesome.HOME, true,Arrays.asList(new Authority(Authority.PROFESOR))), 
	SALES("sales", ProfesorView.class, FontAwesome.BAR_CHART_O, false,Arrays.asList(new Authority(Authority.PROFESOR))), 
	TRANSACTIONS("transactions", ProfesorView.class, FontAwesome.TABLE, false,Arrays.asList(new Authority(Authority.PROFESOR))), 
	REPORTS("Informes", InformesView.class, FontAwesome.FILE_TEXT_O, true,Arrays.asList(new Authority(Authority.PROFESOR))),
	SCHEDULE("calendario", CalendarioView.class, FontAwesome.CALENDAR_O, false,Arrays.asList(new Authority(Authority.PROFESOR))),
	CALIFICACIONES("Calificaciones", CalificacionesView.class, FontAwesome.EDIT, false,Arrays.asList(new Authority(Authority.PROFESOR))),
	ALUMNOS("Alumnos", AlumnosView.class, FontAwesome.SEARCH, false,Arrays.asList(new Authority(Authority.PROFESOR))),
	PERSONASSINIDENTIFICAR("Alumnos", AlumnosView.class, FontAwesome.SEARCH, false,Arrays.asList(new Authority(Authority.ADMINISTRADOR)));
	

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private List<com.app.infrastructure.security.Authority> roles;

    private UserMenuHelper(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful,List<com.app.infrastructure.security.Authority> roles) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.roles = roles;
    }

    /**
	 * @return roles
	 */
	public List<com.app.infrastructure.security.Authority> getRoles() {
		return roles;
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
