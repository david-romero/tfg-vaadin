/**
 * TestProfesor.java
 * appEducacional
 * 11/01/2014 17:42:33
 * Copyright David Romero Alcaide
 * com.app.pruebasunitarias
 */
package com.app.pruebasunitarias;

import java.util.Date;

import org.junit.Test;

import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.Profesor;

/**
 * @author David Romero Alcaide
 * 
 */
public class TestProfesor {

	@Test(expected = IllegalArgumentException.class)
	public void testProfesorFail() {
		Profesor anaRomeroBomba = new Profesor();
		anaRomeroBomba.setApellidos("Romero Bomba");
		anaRomeroBomba.setEmail("amarobo@gmail.com");
		anaRomeroBomba.setInstituto("I.E.S. San Blas");
		anaRomeroBomba.setNombre("Ana");
		anaRomeroBomba.setTelefono("959128414");
		anaRomeroBomba.setPreferenciasCita("Martes a las 17:00");
		Asignatura matematicas = new Asignatura();
		matematicas.setNombre("matematicas");
		Asignatura matematicas2 = new Asignatura();
		matematicas2.setNombre("matematicas");
		anaRomeroBomba.addAsignaturas(matematicas);
		anaRomeroBomba.addAsignaturas(matematicas2);
	}

	@Test
	public void testProfesorOk() {
		Profesor anaRomeroBomba = new Profesor();
		anaRomeroBomba.setApellidos("Romero Bomba");
		anaRomeroBomba.setEmail("amarobo@gmail.com");
		anaRomeroBomba.setInstituto("I.E.S. San Blas");
		anaRomeroBomba.setNombre("Ana");
		anaRomeroBomba.setTelefono("959128414");
		anaRomeroBomba.setPreferenciasCita("Martes a las 17:00");
		Asignatura matematicas = new Asignatura();
		matematicas.setNombre("matematicas");
		Notificacion cita = new Cita();
		cita.setContenido("Cita por el mal comportamiento de su hijo");
		cita.setFecha(new Date(System.currentTimeMillis() + 50));
		anaRomeroBomba.addNotificacion(cita);
	}

}
