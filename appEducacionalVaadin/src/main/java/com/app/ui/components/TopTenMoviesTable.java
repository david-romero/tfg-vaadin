/**
* TopTenMoviesTable.java
* appEducacionalVaadin
* 15/1/2015 21:15:11
* Copyright David
* com.app.ui.components
*/
package com.app.ui.components;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.google.common.collect.Lists;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class TopTenMoviesTable extends Table{

	private transient ApplicationContext applicationContext;
	
	private ProfesorService profesorService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171156784711547709L;

	@Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        if (colId.equals("revenue")) {
            if (property != null && property.getValue() != null) {
                Double r = (Double) property.getValue();
                String ret = new DecimalFormat("#.##").format(r);
                result = "$" + ret;
            } else {
                result = "";
            }
        }
        return result;
    }

    public TopTenMoviesTable() {
    	loadBeans();
        setCaption("Top 10 Mejores Alumnos");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setColumnAlignment("revenue", Align.RIGHT);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSizeFull();
        UserAccount account = AppUI.getCurrentUser();
    	Profesor profesor = null;
    	switch ( Lists.newArrayList(account.getAuthorities()).get(0).getAuthority()) {
		case Authority.PROFESOR:
			profesor = profesorService.findByUserAccount(account);
			break;
		case Authority.TUTOR:
			break;

		default:
			break;
		}
        List<ItemEvaluable> movieRevenues = new ArrayList<ItemEvaluable>(
                profesorService.findAllItems(profesor));
        Collections.sort(movieRevenues, new Comparator<ItemEvaluable>() {

			@Override
			public int compare(ItemEvaluable item1, ItemEvaluable item2) {
				return item1.getFecha().compareTo(item2.getFecha());
			}
        });

        setContainerDataSource(new BeanItemContainer<ItemEvaluable>(
                ItemEvaluable.class, movieRevenues.subList(0, 10)));

        setVisibleColumns("titulo", "calificacion");
        setColumnHeaders("Titulo", "Calificación");
        setColumnExpandRatio("titulo", 2);
        setColumnExpandRatio("calificacion", 1);

        setSortContainerPropertyId("titulo");
        setSortAscending(false);
    }

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ( (AppUI) UI.getCurrent() ).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
	}
	
}
