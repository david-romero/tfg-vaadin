/**
 * TestPopulateDatabase.java
 * appEducacional
 * 14/01/2014 21:20:27
 * Copyright David Romero Alcaide
 * com.app.pruebasunitarias
 */
package com.app.pruebasunitarias;

/**
 * imports
 */
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
import com.app.domain.model.types.itemsevaluables.Actitud;
import com.app.domain.model.types.itemsevaluables.Cuaderno;
import com.app.domain.model.types.itemsevaluables.EjerciciosEntregados;
import com.app.domain.model.types.itemsevaluables.Examen;
import com.app.domain.model.types.itemsevaluables.Trabajo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/java/com/app/pruebasunitarias/TestPopulateDatabase-context.xml" })
/**
 * @author David Romero Alcaide
 *
 */
public class TestPopulateDatabase {

	@Autowired(required = true)
	Profesor profesor1;
	@Autowired(required = true)
	Profesor profesor2;
	@Autowired(required = true)
	Profesor profesor3;
	@Autowired(required = true)
	PadreMadreOTutor tutor1;
	@Autowired(required = true)
	PadreMadreOTutor tutor2;
	@Autowired(required = true)
	PadreMadreOTutor tutor3;
	@Autowired(required = true)
	PadreMadreOTutor tutor4;
	@Autowired(required = true)
	Curso curso1;
	@Autowired(required = true)
	Curso curso2;
	@Autowired(required = true)
	Alumno alumno1;
	@Autowired(required = true)
	Alumno alumno2;
	@Autowired(required = true)
	Alumno alumno3;
	@Autowired(required = true)
	Asignatura asignatura1;
	@Autowired(required = true)
	Asignatura asignatura2;
	@Autowired(required = true)
	Asignatura asignatura3;
	@Autowired(required = true)
	Asignatura asignatura4;
	@Autowired(required = true)
	Asignatura asignatura5;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario1;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario2;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario3;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario4;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario5;
	@Autowired(required = true)
	DiaDeCalendario diaDeCalendario6;
	@Autowired(required = true)
	Evaluacion evaluacion1;
	@Autowired(required = true)
	Evaluacion evaluacion2;
	@Autowired(required = true)
	ItemEvaluable item1;
	@Autowired(required = true)
	ItemEvaluable item2;
	@Autowired(required = true)
	Evento evento1;

	@Test
	public void testNotNullOk() {
		Assert.notNull(alumno1);
		Assert.notNull(alumno2);
		Assert.notNull(alumno3);
		Assert.notNull(profesor1);
		Assert.notNull(profesor2);
		Assert.notNull(profesor3);
		Assert.notNull(tutor1);
		Assert.notNull(tutor2);
		Assert.notNull(tutor3);
		Assert.notNull(tutor4);
		Assert.notNull(curso1);
		Assert.notNull(curso2);
		Assert.notNull(asignatura1);
		Assert.notNull(asignatura2);
		Assert.notNull(asignatura3);
		Assert.notNull(asignatura4);
		Assert.notNull(asignatura5);
		Assert.notNull(diaDeCalendario1);
		Assert.notNull(diaDeCalendario2);
		Assert.notNull(diaDeCalendario3);
		Assert.notNull(diaDeCalendario4);
		Assert.notNull(diaDeCalendario5);
		Assert.notNull(diaDeCalendario6);
		Assert.notNull(item1);
		Assert.notNull(item2);
		Assert.notNull(evaluacion1);
		Assert.notNull(evaluacion2);
		Assert.notNull(evento1);
	}

	@Test
	public void testCalcularNotaEvaluacionAlumno1() {
		Logger logger = Logger.getLogger("com.app");
		/*
		 * Establecemos el nivel del logger aunque esto se suele hacer por
		 * ficheros de configuracion
		 */
		logger.setLevel(Level.INFO);
		logger.debug("Inicio calculo nota evaluación");
		NotaPorEvaluacion notaPrimeraEvaluacion = new NotaPorEvaluacion();
		// Obtenemos una asignatura
		alumno3.addDiaDeCalendario(diaDeCalendario5);
		asignatura4
				.setCriteriosDeEvaluacion(establecerCriteriosEvaluacion(asignatura4));
		// Obtenemos items. Existe una consulta hecha. Aquí en código
		Map<ItemEvaluable, Double> notas = obtenerItemsAlumnoEvaluacion(
				alumno3, evaluacion1, asignatura4);
		Double sumatorio = 0.0;
		Assert.notNull(asignatura4);
		Assert.notNull(asignatura4.getCriteriosDeEvaluacion());

		for (ItemEvaluable item : notas.keySet()) {
			double nota = notas.get(item);
			double porcentaje = 0;
			for (String item2 : asignatura4.getCriteriosDeEvaluacion().keySet()) {
				if (item.getClass()
						.getName()
						.compareTo(
								("com.app.domain.model.types.itemsevaluables." + item2)) == 0) {
					int porc = asignatura4.getCriteriosDeEvaluacion()
							.get(item2);
					porcentaje = (porc / 100.0);
				}
			}
			sumatorio += nota * porcentaje;
		}
		notaPrimeraEvaluacion.setNotaFinal(sumatorio);
		notaPrimeraEvaluacion.setAsignatura(asignatura1);
		notaPrimeraEvaluacion.setEvaluacion(evaluacion2);
		alumno1.addNotaPorEvaluacion(notaPrimeraEvaluacion);
		Assert.isTrue(sumatorio > 0);
		logger.debug("nota obtenida para el alumno " + alumno1.getNombre()
				+ " : " + sumatorio);
	}

	/**
	 * @author David Romero Alcaide
	 * @param alumno12
	 * @param evaluacion22
	 */
	private Map<ItemEvaluable, Double> obtenerItemsAlumnoEvaluacion(
			Alumno alumno12, Evaluacion evaluacion22, Asignatura asignatura1) {
		Map<ItemEvaluable, Double> items = Maps.newHashMap();
		double numExamenes = 0;
		double sumNotaExamenes = 0;
		double numTrabajos = 0;
		double sumNotaTrabajos = 0;
		double numActitud = 0;
		double sumNotaActitud = 0;
		double numCuaderno = 0;
		double sumNotaCuaderno = 0;
		double numEjercicios = 0;
		double sumNotaEjercicios = 0;
		Logger logger = Logger.getLogger("com.app");
		/*
		 * Establecemos el nivel del logger aunque esto se suele hacer por
		 * ficheros de configuracion
		 */
		logger.setLevel(Level.INFO);
		logger.info("0 bucle");
		Assert.notEmpty(alumno12.getDiasDelCalendario());
		List<DiaDeCalendario> dias = Lists.newArrayList(alumno12
				.getDiasDelCalendario());
		for (DiaDeCalendario dia : dias) {
			logger.info("1 bucle");
			for (ItemEvaluable item : dia.getItemsEvaluable()) {
				logger.info("2 bucle");
				System.out.println(item.getEvaluacion());
				System.out.println(evaluacion22);
				System.out.println(item.getAsignatura());
				System.out.println(asignatura1);
				if (item.getEvaluacion().equals(evaluacion22)
						&& item.getAsignatura().equals(asignatura1)) {

					if (item instanceof Examen) {
						sumNotaExamenes += item.getCalificacion();
						numExamenes++;
					}
					if (item instanceof Trabajo) {
						sumNotaTrabajos += item.getCalificacion();
						numTrabajos++;
					}
					if (item instanceof Actitud) {
						sumNotaActitud += item.getCalificacion();
						numActitud++;
					}
					if (item instanceof Cuaderno) {
						sumNotaCuaderno += item.getCalificacion();
						numCuaderno++;
					}
					if (item instanceof EjerciciosEntregados) {
						sumNotaEjercicios += item.getCalificacion();
						numEjercicios++;
					}
				}
			}
		}
		// Assert.isTrue(numExamenes > 0 || numTrabajos > 0 || numActitud > 0 ||
		// numCuaderno > 0 || numEjercicios > 0 );
		if (numExamenes > 0) {
			items.put(new Examen(), sumNotaExamenes / numExamenes);
		} else {
			items.put(new Examen(), 0.0);
		}
		if (numTrabajos > 0) {
			items.put(new Trabajo(), sumNotaTrabajos / numTrabajos);
		} else {
			items.put(new Trabajo(), 0.0);
		}
		if (numActitud > 0) {
			items.put(new Actitud(), sumNotaActitud / numActitud);
		} else {
			items.put(new Actitud(), 0.0);
		}
		if (numCuaderno > 0) {
			items.put(new Cuaderno(), sumNotaCuaderno / numCuaderno);
		} else {
			items.put(new Cuaderno(), 0.0);
		}
		if (numEjercicios > 0) {
			items.put(new EjerciciosEntregados(), sumNotaEjercicios
					/ numEjercicios);
		} else {
			items.put(new EjerciciosEntregados(), 0.0);
		}

		return items;
	}

	/**
	 * @author David Romero Alcaide
	 * @param asignaturaVinculada
	 */
	private Map<String, Integer> establecerCriteriosEvaluacion(
			Asignatura asignaturaVinculada) {
		Map<String, Integer> criterios = Maps.newHashMap();
		criterios.put("Examen", 70);
		criterios.put("Trabajo", 20);
		criterios.put("Cuaderno", 10);
		return criterios;
	}
}
