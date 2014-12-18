/**
 * TutorProfesorControllerWeb.java
 * appEducacional
 * 04/05/2014 09:02:52
 * Copyright David Romero Alcaide
 * com.app.web.controllers.tutor
 */
package com.app.web.controllers.tutor;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.*;

import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;

@Controller
@Transactional
@RequestMapping("/tutor/profesor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class TutorProfesorControllerWeb extends AbstractController {
	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(TutorProfesorControllerWeb.class);

	@Autowired
	/**
	 * 
	 */
	private PadreMadreOTutorService tutorService;

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

	@Autowired
	/**
	 * 
	 */
	private CitaService citaService;

	@Autowired
	/**
	 * 
	 */
	private NotificacionService notificacionService;

	@RequestMapping(value = "concertarCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView concertarCita(@RequestParam int profesorId) {
		Assert.isTrue(profesorId > 0, "error");
		ModelAndView result;
		Profesor profe = profesorService.findOne(profesorId);
		Assert.notNull(profe, "error");
		PadreMadreOTutor padre = tutorService.findPrincipal();
		Assert.notNull(padre, "error");
		result = new ModelAndView("tutor/cita");
		Cita cita = citaService.create();
		cita.setEmisor("TUTOR");
		cita.setProfesor(profe);
		cita.setPadreMadreOTutor(padre);
		result.addObject("cita", cita);
		result.addObject("profesorId", profesorId);
		result.addObject("preferenciasCita", profe.getPreferenciasCita());

		return result;
	}

	@RequestMapping(value = "guardarCita", method = RequestMethod.POST, params = "save")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarCita(@Valid Cita cita, BindingResult bind) {
		ModelAndView result;
		if (bind.hasErrors()) {
			result = new ModelAndView("tutor/cita");
			result.addObject("cita", cita);
			result.addObject("profesorId", cita.getProfesor().getId());
			result.addObject("message", "error");
		} else {
			try {
				beginTransaction();
				cita.setEmisor("TUTOR");
				citaService.save(cita, tutorService.findPrincipal());
				commitTransaction();
				result = new ModelAndView("tutor/cita");
				result.addObject("cita", cita);
				result.addObject("profesorId", cita.getProfesor().getId());
				result.addObject("message", "ok");
			} catch (Exception e) {
				rollbackTransaction();
				LOGGER.error(e);
				result = new ModelAndView("tutor/cita");
				result.addObject("cita", cita);
				result.addObject("profesorId", cita.getProfesor().getId());
				result.addObject("message", "error");
			}
		}
		return result;
	}

	@RequestMapping(value = "enviarNotificacion", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView enviarNotificacion() {
		try {
			ModelAndView result = new ModelAndView("tutor/enviarNotificacion");
			Notificacion noti = notificacionService.create();
			noti.setEmisor("TUTOR");
			PadreMadreOTutor tutor = tutorService.findPrincipal();
			Assert.notNull(tutor);
			noti.setPadreMadreOTutor(tutor);
			Collection<Profesor> profesores = tutorService.getTodosProfesores();
			result.addObject("notificacion", noti);
			result.addObject("profesores", profesores);
			result.addObject("requestURI",
					"appEducacional/app/tutor/profesor/enviarNotificacion.do");
			return result;
		} catch (IllegalArgumentException e) {
			return new ModelAndView("security/login");
		}

	}

	@RequestMapping(value = "guardarNotificacion", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param noti
	 * @param destinatario
	 * @param tipoNotificacion
	 * @param bind
	 * @return
	 */
	public ModelAndView enviarNotificacionFinProceso(@Valid Notificacion noti,
			BindingResult bind) {
		ModelAndView result = enviarNotificacion();
		if (bind.hasErrors()) {
			result = enviarNotificacion();
			result.addObject("message", "error");
			return result;
		} else {
			try {
				noti.setEmisor("TUTOR");
				beginTransaction();
				notificacionService.save(noti);
				commitTransaction();
				result.addObject("message", "ok");
			} catch (Exception e) {
				try {
					rollbackTransaction();
					result.addObject("message", "error");
					LOGGER.error(e);
				} catch (NullPointerException oops) {
					LOGGER.error(oops);
					result = enviarNotificacion();
					result.addObject("message", "error");
				}
				LOGGER.error(e);
			}
		}
		return result;
	}

	@RequestMapping(value = "confirmarCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView confirmarCitaPorTutor(@RequestParam("cita") int cita) {
		ModelAndView result;
		Cita citaBuscada = citaService.findOne(cita);
		Assert.notNull(citaBuscada);
		Assert.notNull(tutorService.findPrincipal());
		Assert.isTrue(tutorService.findPrincipal().equals(
				citaBuscada.getPadreMadreOTutor()));
		try {
			beginTransaction();
			citaService.confirmarPorTutor(citaBuscada);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			LOGGER.error(e.getMessage(), e.getCause());
			result = new ModelAndView("tutor/verCitas");
			result.addObject("message", "error");
		}
		try {
			beginTransaction();
			Notificacion noti = notificacionService.create();
			noti.setEmisor("TUTOR");
			noti.setFecha(new Date(System.currentTimeMillis()));
			noti.setPadreMadreOTutor(citaBuscada.getPadreMadreOTutor());
			noti.setProfesor(citaBuscada.getProfesor());
			SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			noti.setContenido("El Padre, Madre o Tutor "
					+ citaBuscada.getPadreMadreOTutor().getNombre()
					+ citaBuscada.getPadreMadreOTutor().getApellidos()
					+ " ha confirmado la cita para la fecha "
					+ sp.format(citaBuscada.getFecha()));
			notificacionService.save(noti);
			result = new ModelAndView("tutor/verCitas");
			result.addObject("message", "ok");
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			LOGGER.error(e.getMessage(), e.getCause());
			result = new ModelAndView("tutor/verCitas");
			result.addObject("message", "error");
		}
		return result;

	}

}
