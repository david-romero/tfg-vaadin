/**
 * TestAlumno.java
 * appEducacional
 * 11/01/2014 14:14:44
 * Copyright David Romero Alcaide
 * com.app.pruebasunitarias
 */
package com.app.pruebasunitarias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.DiaDeCalendario;
import com.app.domain.model.types.Evaluacion;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.domain.model.types.itemsevaluables.Examen;

/**
 * @author David Romero Alcaide
 * 
 */
public class TestAlumno {

	@Test()
	public void testAlumno1() {
		Alumno alumno = new Alumno();
		Assert.assertNotNull(alumno);
	}

	@Test
	public void testAlumnoOK() {
		Alumno alumno = new Alumno();
		DiaDeCalendario diaDeCalendario = new DiaDeCalendario();
		diaDeCalendario.setDia(11);
		diaDeCalendario.setMes(1);
		int[] cursoAcademico = { 2013, 2014 };
		diaDeCalendario.setCurso(cursoAcademico);
		alumno.addDiaDeCalendario(diaDeCalendario);
		PadreMadreOTutor padreMadreOTutor = new PadreMadreOTutor();
		padreMadreOTutor.setNombre("Eduardo");
		padreMadreOTutor.setApellidos("Romero Bomba");
		padreMadreOTutor.setEmail("davromalc@gmail.com");
		padreMadreOTutor.setDNI("28842171X");
		padreMadreOTutor.setTelefono("655048907");
		alumno.addPadreMadreOTutor(padreMadreOTutor);
		padreMadreOTutor.addTutorando(alumno);

		Assert.assertTrue(alumno.getPadresMadresOTutores().size() == 1);
		Curso curso = new Curso();
		curso.setNivel(2);
		curso.setNivelEducativo("E.S.O.");
		curso.setIdentificador('C');
		curso.addAlumno(alumno);
		alumno.setRepiteCurso("2ª Primaria");
		NotaPorEvaluacion nota = new NotaPorEvaluacion();
		nota.setNotaFinal(9.5);
		Evaluacion evaluacion1 = new Evaluacion();
		evaluacion1.setIndicador(1);
		evaluacion1.setInicio(new Date());
		evaluacion1.setFin(new Date(System.currentTimeMillis() + 5000));
		ItemEvaluable examen = new Examen();
		examen.setFecha(new Date(System.currentTimeMillis() + 200));
		examen.setCalificacion(9.5);
		examen.setTitulo("Tema 1");
		evaluacion1.addItemEvaluable(examen);
		Asignatura matematicas = new Asignatura();
		matematicas.setNombre("Matemáticas");
		matematicas.addItemEvaluable(examen);
		curso.addAsignatura(matematicas);
		matematicas.addCriterioDeEvaluacion("Examen", 100);
		nota.setAsignatura(matematicas);
		Profesor anaRomeroBomba = new Profesor();
		anaRomeroBomba.setApellidos("Romero Bomba");
		anaRomeroBomba.setEmail("amarobo@gmail.com");
		anaRomeroBomba.setInstituto("I.E.S. San Blas");
		anaRomeroBomba.setNombre("Ana");
		anaRomeroBomba.setTelefono("959128414");
		anaRomeroBomba.setPreferenciasCita("Martes a las 17:00");
		anaRomeroBomba.addAsignaturas(matematicas);
		Evento eventoExamenProximoMartes = new Evento();
		eventoExamenProximoMartes.setProfesor(anaRomeroBomba);
		eventoExamenProximoMartes.setAsignatura(matematicas);
		eventoExamenProximoMartes.setFecha(new Date(
				System.currentTimeMillis() + 2500));
		ItemEvaluable examenFuturo = new Examen();
		examenFuturo.setFecha(new Date(System.currentTimeMillis() + 2500));
		examenFuturo.setTitulo("Tema 2");
		examenFuturo.setEvaluacion(evaluacion1);
		eventoExamenProximoMartes.setItemEvaluable(examenFuturo);
		anaRomeroBomba.addEvento(eventoExamenProximoMartes);
		DiaDeCalendario martesProximo = new DiaDeCalendario();
		martesProximo.setDia(14);
		martesProximo.setMes(1);
		martesProximo.setCurso(cursoAcademico);
		alumno.addDiaDeCalendario(martesProximo);
	}
}
