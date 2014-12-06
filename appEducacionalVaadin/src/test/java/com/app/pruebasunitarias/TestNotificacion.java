/**
 * TestNotificacion.java
 * appEducacional
 * 21/07/2014 19:58:09
 * Copyright David Romero Alcaide
 * com.app.pruebasunitarias
 */
package com.app.pruebasunitarias;

import java.util.Date;

import org.junit.Test;
import org.springframework.util.Assert;

import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;

/**
 * @author David Romero Alcaide
 * 
 */
public class TestNotificacion {

	@Test()
	public void testProfesorFail() {
		Notificacion noti = new Notificacion();
		PadreMadreOTutor tutor = new PadreMadreOTutor();
		Profesor profe = new Profesor();
		noti.setContenido("Prueba de notificaicon");
		noti.setPadreMadreOTutor(tutor);
		tutor.addNotificacion(noti);
		profe.addNotificacion(noti);
		noti.setProfesor(profe);
		noti.setFecha(new Date(System.currentTimeMillis()));
		noti.setEmisor("PROFESOR");
		Assert.notNull(noti.getPadreMadreOTutor());
		Assert.notNull(noti.getPadreMadreOTutor());
		Assert.isTrue(profe.getClass().getSimpleName().toUpperCase()
				.compareTo(noti.getEmisor()) == 0);
		Assert.isTrue(noti.getFecha().before(
				new Date(System.currentTimeMillis() + 2)));
		Assert.notEmpty(profe.getNotificaciones());
		Assert.notEmpty(tutor.getNotificaciones());
		tutor.setNotificaciones(tutor.getNotificaciones());
		Assert.notEmpty(tutor.getNotificaciones());
		tutor.removeNotificacion(noti);
		Assert.isTrue(tutor.getNotificaciones().isEmpty());
	}

}
