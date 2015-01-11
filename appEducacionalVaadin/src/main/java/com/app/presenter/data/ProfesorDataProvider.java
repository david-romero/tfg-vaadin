/**
* ProfesorDataProvider.java
* appEducacionalVaadin
* 28/12/2014 13:11:06
* Copyright David
* com.app.presenter.data
*/
package com.app.presenter.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.Profesor;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

/**
 * @author David
 *
 */
public class ProfesorDataProvider implements DataProvider{

	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private ProfesorService profesorService;
	
	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getRecentTransactions(int)
	 */
	@Override
	public Collection<Cita> getRecentTransactions(int count) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getDailyRevenuesByMovie(int)
	 */
	@Override
	public Collection<ItemEvaluable> getDailyRevenuesByMovie(int id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getTotalMovieRevenues()
	 */
	@Override
	public Collection<ItemEvaluable> getTotalMovieRevenues() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getUnreadNotificationsCount()
	 */
	@Override
	public int getUnreadNotificationsCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getNotifications()
	 */
	@Override
	public Collection<Notificacion> getNotifications() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getTotalSum()
	 */
	@Override
	public double getTotalSum() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getMovies()
	 */
	@Override
	public Collection<NotaPorEvaluacion> getMovies() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getMovie(int)
	 */
	@Override
	public NotaPorEvaluacion getMovie(int movieId) {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#getTransactionsBetween(java.util.Date, java.util.Date, com.app.domain.model.types.Profesor)
	 */
	@Override
	public Collection<Cita> getTransactionsBetween(Date startDate,
			Date endDate, Profesor p) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.app.presenter.data.DataProvider#findAll()
	 */
	@Override
	public Collection<Cita> findAll() {
		return null;
	}
	
	

}
