/**
 * TutorController.java
 * appEducacional
 * 25/06/2014 20:22:01
 * Copyright David Romero Alcaide
 * com.app.api.controllers.tutor
 */
package com.app.api.controllers.tutor;

import java.util.Collection;
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
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.EventoService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Evento;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.utility.EventCalendar;
import com.google.common.collect.Maps;

@Controller
@Transactional
@RequestMapping("/tutor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class TutorController extends AbstractController {

	@Autowired
	private PadreMadreOTutorService tutorService;

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private CitaService citaService;

	@Autowired
	private EventoService eventoService;

	@RequestMapping(value = "/numeroNotificaciones", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	int getNumeroNotificacionesRecibidas() {
		PadreMadreOTutor p = tutorService.findPrincipal();
		Assert.notNull(p);
		int numeroNotificacionesRecibidas = notificacionService
				.findTutorRecibidas(p).size();
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
		PadreMadreOTutor p = tutorService.findPrincipal();
		Assert.notNull(p);
		Collection<Evento> eventos = eventoService.findAllTutor(p);
		Collection<Cita> citas = citaService.findTutorEmitidas(p);
		citas.addAll(citaService.findTutorRecibidas(p));
		int numeroEventos = citas.size() + eventos.size();
		EventCalendar[] eventosCalendar = new EventCalendar[numeroEventos];
		int i = 0;
		i = almacenarEventosEnCalendario(eventos, eventosCalendar, i);
		almacenarCitasEnCalendario(citas, eventosCalendar, i);
		mapa.put("result", eventosCalendar);
		return mapa;
	}

	/**
	 * @author David Romero Alcaide
	 * @param citas
	 * @param eventosCalendar
	 * @param i
	 * @return
	 */
	private void almacenarCitasEnCalendario(Collection<Cita> citas,
			EventCalendar[] eventosCalendar, int i) {
		for (Cita event : citas) {
			EventCalendar evento = new EventCalendar();
			evento.setId(event.getId());
			evento.setStart(event.getFecha().getTime());
			evento.setEnd(event.getFecha().getTime() + 3600);
			evento.setTitle("Cita con : " + event.getProfesor().getNombre()
					+ " " + event.getProfesor().getApellidos() + " "
					+ event.getContenido());
			evento.setUrl("app/profesor/verCitas.do");
			eventosCalendar[i] = evento;
			i++;
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param eventos
	 * @param eventosCalendar
	 * @param i
	 * @return
	 */
	private int almacenarEventosEnCalendario(Collection<Evento> eventos,
			EventCalendar[] eventosCalendar, int i) {
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
		return i;
	}

}
