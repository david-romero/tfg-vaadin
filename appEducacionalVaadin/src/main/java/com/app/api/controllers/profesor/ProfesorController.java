/**
 * ProfesorController.java
 * appEducacional
 * 19/03/2014 16:43:27
 * Copyright David Romero Alcaide
 * com.app.api.controllers.profesor
 */
package com.app.api.controllers.profesor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AsignaturaService;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.Profesor;
import com.app.utility.EventCalendar;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@Transactional
@RequestMapping("/profesor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorController extends AbstractController {

	@Autowired
	private ProfesorService profesorService;

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private AsignaturaService asignaturaService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private CitaService citaService;

	@RequestMapping(value = "/asignaturas", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	Map<Integer, String> getTodasAsignaturas() {
		List<Asignatura> listaConDuplicados = Lists
				.newArrayList(asignaturaService.findAll());
		Map<Integer, String> map = Maps.newHashMap();
		for (Asignatura a : listaConDuplicados) {
			if (!map.values().contains(a.getNombre())) {
				map.put(a.getId(), a.getNombre());
			}
		}
		return map;
	}

	@RequestMapping(value = "/numeroNotificaciones", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	int getNumeroNotificacionesRecibidas() {
		Profesor p = profesorService.findPrincipal();
		Assert.notNull(p);
		int numeroNotificacionesRecibidas = notificacionService
				.findProfesorRecibidas(p).size();
		return numeroNotificacionesRecibidas;
	}

	@RequestMapping(value = "/getEventos", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	Map<String, Object> getEventosCalendario(@RequestParam("from") long from,
			@RequestParam("to") long to) {
		Map<String, Object> mapa = Maps.newHashMap();
		mapa.put("success", 1);
		Profesor p = profesorService.findPrincipal();
		Assert.notNull(p);
		Collection<Evento> eventos = eventoService
				.findAllProfesorGroupedAllTime();
		Collection<Cita> citas = citaService.findProfesorEmitidas(p);
		citas.addAll(citaService.findProfesorRecibidas(p));
		int numeroEventos = citas.size() + eventos.size();
		EventCalendar[] eventosCalendar = new EventCalendar[numeroEventos];
		int i = 0;
		for (Evento event : eventos) {
			EventCalendar evento = new EventCalendar();
			evento.setId(event.getId());
			evento.setStart(event.getFecha().getTime());
			evento.setEnd(event.getFecha().getTime() + 3600);
			evento.setTitle(event.getItemEvaluable().getTitulo());
			evento.setUrl("app/profesor/evento/evaluarItems.do?eventId="
					+ event.getId());
			eventosCalendar[i] = evento;
			i++;
		}
		for (Cita event : citas) {
			EventCalendar evento = new EventCalendar();
			evento.setId(event.getId());
			evento.setStart(event.getFecha().getTime());
			evento.setEnd(event.getFecha().getTime() + 3600);
			evento.setTitle("Cita con : "
					+ event.getPadreMadreOTutor().getNombre() + " "
					+ event.getPadreMadreOTutor().getApellidos() + " "
					+ event.getContenido());
			evento.setUrl("app/profesor/verCitas.do");
			eventosCalendar[i] = evento;
			i++;
		}
		mapa.put("result", eventosCalendar);
		return mapa;
	}

}
