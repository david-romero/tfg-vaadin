/**
* DataProvider.java
* appEducacionalVaadin
* 26/12/2014 21:23:05
* Copyright David
* com.app.presenter.data
*/
package com.app.presenter.data;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transaction;

import com.app.domain.model.types.Cita;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.Profesor;

/**
 * @author David
 *
 */
public interface DataProvider {

	/**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */
    Collection<Cita> getRecentTransactions(int count);

    /**
     * @param id
     *            Movie identifier.
     * @return A Collection of daily revenues for the movie.
     */
    Collection<ItemEvaluable> getDailyRevenuesByMovie(int id);

    /**
     * @return Total revenues for each listed movie.
     */
    Collection<ItemEvaluable> getTotalMovieRevenues();

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<Notificacion> getNotifications();

    /**
     * @return The total summed up revenue of sold movie tickets
     */
    double getTotalSum();

    /**
     * @return A Collection of movies.
     */
    Collection<NotaPorEvaluacion> getMovies();

    /**
     * @param movieId
     *            Movie's identifier
     * @return A Movie instance for the given id.
     */
    NotaPorEvaluacion getMovie(int movieId);

    /**
     * @param startDate
     * @param endDate
     * @return A Collection of Transactions between the given start and end
     *         dates.
     */
    Collection<Cita> getTransactionsBetween(Date startDate, Date endDate,Profesor p);
    
    Collection<Cita> findAll();
	
}
