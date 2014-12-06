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
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Notificacion;

@Controller
@Transactional
@RequestMapping("/profesor/tutor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorTutorControllerWeb extends AbstractController {

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
	private CitaService citaService;

	@Autowired
	/**
	 * 
	 */
	private NotificacionService notificacionService;

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ProfesorTutorControllerWeb.class);

	// Constructors -----------------------------------------------------------

	/**
	 * empty constructor Constructor
	 */
	public ProfesorTutorControllerWeb() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "confirmarCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView confirmarCitaPorProfesor(@RequestParam("cita") int cita) {
		ModelAndView result;
		Cita citaBuscada = citaService.findOne(cita);
		Assert.notNull(citaBuscada);
		Assert.notNull(profesorService.findPrincipal());
		Assert.isTrue(profesorService.findPrincipal().equals(
				citaBuscada.getProfesor()));
		try {
			beginTransaction();
			citaService.confirmarPorProfesor(citaBuscada);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			LOGGER.error(e.getMessage(), e.getCause());
			result = new ModelAndView("profesor/verCitas");
			result.addObject("message", "error");
		}
		try {
			beginTransaction();
			Notificacion noti = notificacionService.create();
			noti.setEmisor("PROFESOR");
			noti.setFecha(new Date(System.currentTimeMillis()));
			noti.setPadreMadreOTutor(citaBuscada.getPadreMadreOTutor());
			noti.setProfesor(citaBuscada.getProfesor());
			SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			noti.setContenido("El Profesor "
					+ citaBuscada.getProfesor().getNombre() + " "
					+ citaBuscada.getProfesor().getApellidos()
					+ " ha confirmado la cita para la fecha "
					+ sp.format(citaBuscada.getFecha()));
			notificacionService.save(noti);
			result = new ModelAndView("profesor/verCitas");
			result.addObject("message", "ok");
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			LOGGER.error(e.getMessage(), e.getCause());
			result = new ModelAndView("profesor/verCitas");
			result.addObject("message", "error");
		}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

}
