/**
 * EventoProfesorControllerWeb.java
 * appEducacional
 * 20/01/2014 18:36:06
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

/**
 * imports
 */
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AlumnoService;
import com.app.applicationservices.services.CursoService;
import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.Profesor;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
@Transactional
@RequestMapping("/profesor/evento")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorEventoControllerWeb extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

	@Autowired
	/**
	 * 
	 */
	private EventoService eventoService;

	@Autowired
	/**
	 * 
	 */
	private AlumnoService alumnoService;

	@Autowired
	/**
	 * 
	 */
	private CursoService cursoService;

	// Application context
	@Autowired
	/**
	 * 
	 */
	private ApplicationContext appContext;

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ProfesorEventoControllerWeb.class);

	// Constructors -----------------------------------------------------------

	/**
	 * empty constructor Constructor
	 */
	public ProfesorEventoControllerWeb() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/evaluar", method = RequestMethod.GET)
	/**
	 * Lista profesores
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView list() {
		ModelAndView result;
		Collection<Evento> profesores;
		profesores = eventoService.findAllProfesorGrouped();
		result = new ModelAndView("profesor/eventos");
		result.addObject("eventos", profesores);
		result.addObject("url", "app/profesor/evento/evaluarItems.do?eventId");
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/alta", method = RequestMethod.GET)
	/**
	 * Lista profesores
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView create() {
		ModelAndView result;
		Evento e = eventoService.create();
		Profesor p = profesorService.findPrincipal();
		Assert.notNull(p);
		Assert.isTrue(p.isIdentidadConfirmada());
		e.setProfesor(p);
		
		Collection<Curso> misCursos = profesorService
				.getCursosImparteDocencia();
		Collection<Alumno> misAlumnos = profesorService
				.getTodosLosAlumnosProfesor();
		result = new ModelAndView("evento/alta");
		result.addObject("evento", e);
		result.addObject("alumnos", misAlumnos);
		result.addObject("cursos", misCursos);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	/**
	 * Lista profesores
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardar(Evento event, String tipoEvento,
			String alumnoSeleccionado, String cursoSeleccionado) {
		ModelAndView result = new ModelAndView("evento/alta");
		if (tipoEvento != null && tipoEvento.trim().compareTo("") != 0) {
			// Obtengo el tipo de evento
			com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
					.getBean(tipoEvento + "Service");
			if ((alumnoSeleccionado != null && cursoSeleccionado != null)
					|| (alumnoSeleccionado == null && cursoSeleccionado == null)) {
				result.addObject("message", "error2");
			} else {

				try {
					if (alumnoSeleccionado != null) {
						// Ha seleccionado los alumnos
						establecerEventoAAlumnos(event, tipoEvento,
								alumnoSeleccionado, result, servicio);
					} else {
						// Ha seleccionado los cursos
						establecerEventoACursos(event, tipoEvento,
								cursoSeleccionado, result, servicio);
					}
				} catch (CloneNotSupportedException opps) {
					result.addObject("message", "error");
				}

			}
		}
		Collection<Curso> misCursos = profesorService
				.getCursosImparteDocencia();
		Collection<Alumno> misAlumnos = profesorService
				.getTodosLosAlumnosProfesor();

		result.addObject("evento", event);
		result.addObject("alumnos", misAlumnos);
		result.addObject("cursos", misCursos);

		return result;
	}

	

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

	/**
	 * @author David Romero Alcaide
	 * @param event
	 * @param tipo_evento
	 * @param curso_seleccionado
	 * @param result
	 * @param servicio
	 * @throws CloneNotSupportedException
	 */
	private void establecerEventoACursos(Evento event, String tipoEvento,
			String cursoSeleccionado, ModelAndView result,
			com.app.applicationservices.services.Service servicio)
			throws CloneNotSupportedException {
		String[] ids = cursoSeleccionado.split(",");
		for (String id : ids) {
			Curso curso = cursoService.findOne(Integer.valueOf(id));
			for (Alumno temp : curso.getAlumnos()) {
				Asignatura asignVinculada = profesorService
						.getAsignaturaCursoProfesor(temp.getCurso());
				SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyyy");
				String tituloEvento = tipoEvento + " para la fecha "
						+ sp.format(event.getFecha());
				crearItemParaEvento(event.getFecha(), tipoEvento, result,
						temp, asignVinculada, tituloEvento);
				vincularItemAEvento(event, result, servicio, asignVinculada,
						tituloEvento, temp);
			}
		}
	}
	
	/**
	 * @author David Romero Alcaide
	 * @param event
	 * @param tipo_evento
	 * @param alumno_seleccionado
	 * @param result
	 * @param servicio
	 * @throws CloneNotSupportedException
	 */
	private void establecerEventoAAlumnos(Evento event, String tipoEvento,
			String alumnoAeleccionado, ModelAndView result,
			com.app.applicationservices.services.Service servicio)
			throws CloneNotSupportedException {
		String[] ids = alumnoAeleccionado.split(",");
		for (String id : ids) {

			Alumno temp = alumnoService.findOne(Integer.valueOf(id));
			Asignatura asignVinculada = profesorService
					.getAsignaturaCursoProfesor(temp.getCurso());
			SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyyy");
			String tituloEvento = tipoEvento + " para la fecha "
					+ sp.format(event.getFecha());
			crearItemParaEvento(event.getFecha(), tipoEvento, result, temp,
					asignVinculada, tituloEvento);
			vincularItemAEvento(event, result, servicio, asignVinculada,
					tituloEvento, temp);
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param event
	 * @param tipo_evento
	 * @param result
	 * @param temp
	 * @param asignVinculada
	 * @param tituloEvento
	 */
	private void crearItemParaEvento(Date eventDate, String tipoEvento,
			ModelAndView result, Alumno temp, Asignatura asignVinculada,
			String tituloEvento) {
		try {
			beginTransaction();
			alumnoService.anadirDiaDiaDeCalendario(temp, eventDate);
			alumnoService.crearItemEvaluacion(eventDate, temp, asignVinculada,
					tipoEvento, tituloEvento);
			commitTransaction();
			result.addObject("message", "ok");
		} catch (Exception e) {
			try {
				rollbackTransaction();
			} catch (NullPointerException ex) {
				LOGGER.error(ex.getMessage(), ex.getCause());
			}
			result.addObject("message", "error");
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param event
	 * @param result
	 * @param servicio
	 * @param asignVinculada
	 * @param tituloEvento
	 */
	private void vincularItemAEvento(Evento event, ModelAndView result,
			com.app.applicationservices.services.Service servicio,
			Asignatura asignVinculada, String tituloEvento, Alumno alum) {
		try {
			beginTransaction();
			ItemEvaluable item = null;
			item = servicio.findByDateAsignaturaAndTitulo(event.getFecha(),
					asignVinculada, tituloEvento, alum);
			event.setAsignatura(asignVinculada);
			event.setItemEvaluable(item);
			Evento event_clone = event.clone();
			eventoService.save(event_clone);
			commitTransaction();
			result.addObject("message", "ok");
		} catch (Exception e) {
			try {
				rollbackTransaction();
			} catch (NullPointerException ex) {
				LOGGER.error(ex.getMessage(), ex.getCause());
			}
			result.addObject("message", "error");
			LOGGER.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/evaluarItems", method = RequestMethod.GET)
	/**
	 * Lista profesores
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView getItemsDeEvento(int eventId) {
		ModelAndView result = new ModelAndView("evento/items");
		Evento event = eventoService.findOne(eventId);
		Assert.notNull(event);
		Assert.isTrue(event.getFecha().before(
				new Date(System.currentTimeMillis())));
		Collection<Evento> eventosRelacionados = eventoService
				.findParecidos(event);
		Iterable<ItemEvaluable> items = Iterables.transform(
				eventosRelacionados, new Function<Evento, ItemEvaluable>() {
					public ItemEvaluable apply(Evento event) {
						return event.getItemEvaluable();
					}
				});
		result.addObject("items", Lists.newArrayList(items));
		result.addObject("eventoId", eventId);
		return result;
	}
}
