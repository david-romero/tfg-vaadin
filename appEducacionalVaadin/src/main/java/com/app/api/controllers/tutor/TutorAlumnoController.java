/**
 * ProfesorAlumnoController.java
 * appEducacional
 * 26/01/2014 12:57:53
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.api.controllers.tutor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.context.WebApplicationContext;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AlumnoService;

import com.app.domain.model.types.Alumno;

import com.app.domain.model.types.ItemEvaluable;

import com.app.utility.Event;
import com.google.common.base.Function;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
@Transactional
@RequestMapping("/tutor/alumno")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class TutorAlumnoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AlumnoService alumnoService;

	/**
	 * Constructor
	 */
	public TutorAlumnoController() {
		super();
	}

	@RequestMapping(value = "/events/{alumnoId}/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Event> getEvents(@PathVariable int alumnoId) {
		Alumno alum = alumnoService.findOne(alumnoId);
		Assert.notNull(alum);
		Assert.isTrue(alumnoId > 0);
		List<ItemEvaluable> items = alumnoService.obtenerItemsAlumno(alum);
		Iterable<Event> list = Iterables.transform(items,
				new Function<ItemEvaluable, Event>() {
					public Event apply(ItemEvaluable from) {
						Event e = new Event();
						e.setDate(from.getFecha());
						e.setTitle(from.getTitulo());
						e.setDescription("Asignatura: "
								+ from.getAsignatura().getNombre()
								+ " Calificación: " + from.getCalificacion());
						e.setType(from.getClass().getSimpleName());
						return e;
					}
				});
		return Lists.newArrayList(list);
	}

	@RequestMapping(value = "/estadisticasPorAlumno", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Double> getEstadisticas(@RequestParam("alumnoId") int alumnoId) {
		List<Double> list = Lists.newArrayList();
		for (int i = 0; i < 11; i++)
			list.add(0.0);
		Alumno a = alumnoService.findOne(alumnoId);
		Assert.notNull(a);
		List<ItemEvaluable> items = alumnoService.obtenerItemsAlumno(a);
		Iterable<Double> notas = Iterables.transform(items,
				new Function<ItemEvaluable, Double>() {

					public Double apply(ItemEvaluable input) {
						return input.getCalificacion();
					}
				});
		return Lists.newArrayList(notas);
	}
}
