/**
 * TopGrossingMoviesChart.java
 * appEducacionalVaadin
 * 18/1/2015 21:32:27
 * Copyright David
 * com.app.ui.components
 */
package com.app.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.google.common.collect.Lists;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.ui.UI;

/**
 * @author David
 *
 */
public class TopGrossingMoviesChart extends Chart {

	private transient ApplicationContext applicationContext;

	private ProfesorService profesorService;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1387352152513936704L;

	public TopGrossingMoviesChart() {
		loadBeans();
		setCaption("Top Grossing Movies");
		getConfiguration().setTitle("");
		getConfiguration().getChart().setType(ChartType.BAR);
		getConfiguration().getChart().setAnimation(false);
		getConfiguration().getxAxis().getLabels().setEnabled(false);
		getConfiguration().getxAxis().setTickWidth(0);
		getConfiguration().getyAxis().setTitle("");
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
		List<ItemEvaluable> movies = new ArrayList<ItemEvaluable>(profesorService.findAllItems(profesor));

		List<Series> series = new ArrayList<Series>();
		for (int i = 0; i < 6; i++) {
			ItemEvaluable itemEvaluable = movies.get(i);
			PlotOptionsBar opts = new PlotOptionsBar();
			// opts.setColor(Color.RED);
			opts.setBorderWidth(0);
			opts.setShadow(false);
			opts.setPointPadding(0.4);
			opts.setAnimation(false);
			ListSeries item = new ListSeries(itemEvaluable.getTitulo(),
					itemEvaluable.getCalificacion());
			item.setPlotOptions(opts);
			series.add(item);

		}
		getConfiguration().setSeries(series);

		Credits c = new Credits("");
		getConfiguration().setCredits(c);

		PlotOptionsBar opts = new PlotOptionsBar();
		opts.setGroupPadding(0);
		getConfiguration().setPlotOptions(opts);

	}
	
	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ( (AppUI) UI.getCurrent() ).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
	}

}
