/**
 * RetrasoService.java
 * appEducacional
 * 27/01/2014 09:15:00
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

/**
 * imports
 */
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.DiaDeCalendario;
import com.app.domain.model.types.Evaluacion;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.itemsevaluables.Retraso;
import com.app.domain.repositories.RetrasoRepository;
import com.google.common.collect.Lists;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class RetrasoService implements
		com.app.applicationservices.services.Service {

	/**
	 * Constructor
	 */
	public RetrasoService() {
		super();

	}

	@Autowired
	/**
	 * Repositorio para falta de asistencia para interactuar con la base de datos
	 */
	private RetrasoRepository retrasoRepositorio;

	// Servicios gestionados

	@Autowired
	/**
	 * Servicio de evaluacion gestionado por este servicio
	 */
	private EvaluacionService evaluacionService;

	// Métodos CRUD
	/**
	 * Crea una falta de asistencia
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ItemEvaluable create() {
		Retraso falta = new Retraso();
		falta.setFecha(new Date(System.currentTimeMillis()));
		falta.setTitulo("Retraso " + falta.getFecha());
		falta.setCalificacion(0.0);
		falta.setJustificado(false);
		// Habria que llamar al servicio de evaluacion para que nos diera la
		// evaluacion
		// en la que estuviesemos
		Evaluacion ev = evaluacionService.findEvaluacionActual();
		falta.setEvaluacion(ev);
		return falta;
	}

	/**
	 * Obtener todas las faltas de asistencia
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<ItemEvaluable> findAll() {
		List<ItemEvaluable> retrasos = Lists.newArrayList();
		retrasos.addAll(this.retrasoRepositorio.findAll());
		return retrasos;
	}

	/**
	 * Encontrar una falta de asistencia por id
	 * 
	 * @author David Romero Alcaide
	 * @param profesorId
	 * @return
	 */
	public Retraso findOne(int retrasoId) {
		Assert.isTrue(retrasoId > 0);
		return retrasoRepositorio.findOne(retrasoId);
	}

	/**
	 * Almacenar una falta de asistencia
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void save(ItemEvaluable falta) {
		Retraso retraso = (Retraso) falta;
		Assert.notNull(falta, "pasarLista.error");
		Assert.isTrue(
				falta.getFecha().after(
						new Date(System.currentTimeMillis() - 18000000)),
				"pasarLista.error");
		Assert.isTrue(falta.getAsignatura().getProfesor()
				.isIdentidadConfirmada(), "pasarLista.error");
		retrasoRepositorio.save(retraso);
	}

	/**
	 * Eliminar una falta de asistencia
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void delete(Retraso falta) {
		Assert.isTrue(falta.getAsignatura().getProfesor()
				.isIdentidadConfirmada());
		Assert.notNull(falta);
		retrasoRepositorio.delete(falta);
	}

	// Otros métodos de negocio

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.app.applicationservices.services.Service#findByDateAsignaturaAndTitulo
	 * (java.util.Date, com.app.domainLayer.domainModel.types.Asignatura,
	 * java.lang.String)
	 */
	
	public ItemEvaluable findByDateAsignaturaAndTitulo(Date date,
			Asignatura asign, String titulo, final Alumno a) {
		Collection<Retraso> items = retrasoRepositorio
				.findByDateAsignaturaTitle(date, asign.getId(), titulo);
		Retraso e = null;
		for (Retraso examenObtenido : items) {
			for (DiaDeCalendario dia : examenObtenido.getDiasDelCalendario()) {
				if (dia.getAlumno().equals(a)) {
					e = examenObtenido;
				}
			}
		}
		Assert.notNull(e);
		return e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.app.applicationservices.services.Service#delete(com.app.domainLayer
	 * .domainModel.types.ItemEvaluable)
	 */
	
	public void delete(ItemEvaluable item) {
		Retraso retraso = (Retraso) item;
		this.delete(retraso);
	}

}
