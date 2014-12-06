/**
 * ExamenService.java
 * appEducacional
 * 15/02/2014 17:19:32
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

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
import com.app.domain.model.types.itemsevaluables.Examen;
import com.app.domain.repositories.ExamenRepository;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class ExamenService implements
		com.app.applicationservices.services.Service {

	@Autowired
	private ExamenRepository examenRepositorio;

	@Autowired
	private EvaluacionService evaluacionService;

	// Métodos CRUD

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Examen create() {
		Examen examen = new Examen();
		return examen;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<ItemEvaluable> findAll() {
		List<ItemEvaluable> ite = Lists.newArrayList();
		ite.addAll(examenRepositorio.findAll());
		return ite;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesorId
	 * @return
	 */
	public Examen findOne(int examenId) {
		Assert.isTrue(examenId > 0);
		Examen exam;
		exam = examenRepositorio.findOne(examenId);
		return exam;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void save(Examen exam) {
		Assert.notNull(exam);
		Assert.notNull(exam.getAsignatura());
		Assert.notNull(exam.getEvaluacion());
		examenRepositorio.save(exam);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void delete(Examen examen) {
		Assert.notNull(examen);
		Assert.isTrue(examen.getId() > 0);
		examenRepositorio.delete(examen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.app.applicationservices.services.Service#save(com.app.domainLayer
	 * .domainModel.types.ItemEvaluable)
	 */
	public void save(ItemEvaluable item) {
		Assert.isInstanceOf(Examen.class, item);
		Examen examen = (Examen) item;
		if (examen.getEvaluacion() == null) {
			Evaluacion ev = evaluacionService.findEvaluacionActual();
			item.setEvaluacion(ev);
		}
		save(examen);
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
		Collection<Examen> items = examenRepositorio.findByDateAsignaturaTitle(
				date, asign.getId(), titulo);
		Examen e = Iterables.find(items, 
				new Predicate<Examen>(){
					public boolean apply(Examen input) {
						boolean bandera = false;
						for (DiaDeCalendario dia : input.getDiasDelCalendario()){
							bandera = dia.getAlumno().equals(a);
						}
						return bandera;
					}
			
		});
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
		Assert.notNull(item);
		Assert.isInstanceOf(Examen.class, item);
		Examen e = (Examen) item;
		this.examenRepositorio.delete(e);
	}

}
