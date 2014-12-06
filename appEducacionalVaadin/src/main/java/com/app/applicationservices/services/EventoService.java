/**
 * EventoService.java
 * appEducacional
 * 16/01/2014 11:38:28
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

/**
 * imports
 */
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.DiaDeCalendario;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.domain.repositories.EventoRepository;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class EventoService {

	@Autowired
	/**
	 * 
	 */
	AlumnoService alumnoService;
	
	@Autowired
	/**
	 * 
	 */
	ProfesorService profesorService;

	@Autowired
	/**
	 * 
	 */
	EventoRepository eventoRepository;

	/**
	 * Constructor
	 */
	public EventoService() {
		super();

	}

	/**
	 * @author David Romero Alcaide
	 * @param eventoJson
	 */
	public void save(Evento eventoJson) {
		Assert.notNull(eventoJson);
		Assert.notNull(eventoJson.getAsignatura());
		Assert.notNull(eventoJson.getFecha());
		Assert.notNull(eventoJson.getItemEvaluable());
		Assert.notNull(eventoJson.getProfesor());
		Assert.isTrue(eventoJson.getAsignatura().getProfesor().equals(eventoJson.getProfesor()));
		Assert.isTrue(eventoJson.getItemEvaluable().getAsignatura().equals(eventoJson.getAsignatura()));
		Assert.isTrue(eventoJson.getFecha().equals(eventoJson.getItemEvaluable().getFecha()));
		Assert.notEmpty(eventoJson.getItemEvaluable().getDiasDelCalendario());
		for (DiaDeCalendario dia : eventoJson.getItemEvaluable().getDiasDelCalendario()){
			Assert.notNull(dia.getAlumno());
		}
		eventoRepository.save(eventoJson);
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Evento create() {
		Evento e = new Evento();
		Assert.notNull(e);
		return e;
	}

	public Collection<Evento> findAllProfesor(Profesor profesor) {
		Assert.notNull(profesor);
		return eventoRepository.findEventosProfesorPasados(profesor.getId());
	}

	public Collection<Evento> findAllProfesorPasadoFuturo(Profesor profesor) {
		Assert.notNull(profesor);
		return eventoRepository.findAllEventosProfesor(profesor.getId());
	}

	public Collection<Evento> findAllProfesor() {
		Assert.notNull(profesorService.findPrincipal());
		return findAllProfesor(profesorService.findPrincipal());
	}

	public Collection<Evento> findAllProfesorGrouped() {
		Assert.notNull(profesorService.findPrincipal());
		List<Evento> allEventos = Lists
				.newArrayList(findAllProfesor(profesorService.findPrincipal()));
		Set<Evento> eventos = Sets.newHashSet();
		eventos.add(allEventos.get(0));
		for (Evento e : allEventos) {
			boolean existe = false;
			for (Evento eventoGuardo : eventos) {

				if (eventoGuardo.getAsignatura().equals(e.getAsignatura())
						&& eventoGuardo.getFecha().equals(e.getFecha())
						&& eventoGuardo
								.getItemEvaluable()
								.getClass()
								.getSimpleName()
								.compareTo(
										e.getItemEvaluable().getClass()
												.getSimpleName()) == 0) {
					existe = true;
				}
			}
			if (!existe) {
				eventos.add(e);
			}
		}

		return eventos;
	}

	public Collection<Evento> findAllProfesorGroupedAllTime() {
		Assert.notNull(profesorService.findPrincipal());
		List<Evento> allEventos = Lists
				.newArrayList(findAllProfesorPasadoFuturo(profesorService
						.findPrincipal()));
		Set<Evento> eventos = Sets.newHashSet();
		eventos.add(allEventos.get(0));
		for (Evento e : allEventos) {
			boolean existe = false;
			for (Evento eventoGuardo : eventos) {

				if (eventoGuardo.getAsignatura().equals(e.getAsignatura())
						&& eventoGuardo.getFecha().equals(e.getFecha())
						&& eventoGuardo
								.getItemEvaluable()
								.getClass()
								.getSimpleName()
								.compareTo(
										e.getItemEvaluable().getClass()
												.getSimpleName()) == 0) {
					existe = true;
				}
			}
			if (!existe) {
				eventos.add(e);
			}
		}

		return eventos;
	}

	/**
	 * @author David Romero Alcaide
	 * @param eventId
	 * @return
	 */
	public Evento findOne(int eventId) {
		Assert.isTrue(eventId > 0);
		return eventoRepository.findOne(eventId);
	}

	public Collection<Evento> findAll() {
		return eventoRepository.findAll();
	}

	/**
	 * @author David Romero Alcaide
	 * @param event
	 * @return
	 */
	public Collection<Evento> findParecidos(final Evento event) {
		List<Evento> allEventos = Lists.newArrayList(findAll());
		List<Evento> filtrados = Lists.newArrayList(Iterables.filter(
				allEventos, new Predicate<Evento>() {
					public boolean apply(Evento p) {
						return p.getAsignatura().equals(event.getAsignatura())
								&& p.getFecha().equals(event.getFecha()) && p
								.getItemEvaluable()
								.getClass()
								.getSimpleName()
								.compareTo(
										event.getItemEvaluable().getClass()
												.getSimpleName()) == 0;
					}
				}));
		return filtrados;
	}

	/**
	 * @author David Romero Alcaide
	 * @param p
	 * @return
	 */
	public Collection<Evento> findAllTutor(PadreMadreOTutor p) {
		Set<Evento> eventos = Sets.newHashSet();
		for (Alumno alum : p.getTutorandos()){
			Collection<Profesor> profesoresAlumno = alumnoService.getProfesores(alum);
			for (Profesor profesor : profesoresAlumno){
				eventos.addAll(findAllProfesor(profesor));
			}
		}
		return eventos;
	}

}
