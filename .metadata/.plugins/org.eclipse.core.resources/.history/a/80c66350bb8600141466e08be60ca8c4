/**
 * TutorControllerWeb.java
 * appEducacional
 * 26/03/2014 17:34:33
 * Copyright David Romero Alcaide
 * com.app.web.controllers.tutor
 */
package com.app.web.controllers.tutor;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.domain.model.types.PadreMadreOTutor;

@Controller
@Transactional
@RequestMapping("/tutor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class TutorControllerWeb extends AbstractController {

	private static final Logger LOGGER = Logger
			.getLogger(TutorControllerWeb.class);

	// Services

	@Autowired
	/**
	 * 
	 */
	private PadreMadreOTutorService tutorService;



	@Autowired
	/**
	 * 
	 */
	private NotificacionService notificacionService;

	@Autowired
	/**
	 * 
	 */
	private CitaService citaService;

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param tutor
	 * @param bind
	 * @return
	 */
	public ModelAndView save(
			@Valid @ModelAttribute PadreMadreOTutor tutorCreado,
			BindingResult bind) {
		ModelAndView result = new ModelAndView("tutor/alta");
		if (bind.hasErrors()) {
			result.addObject("tutorCreado", tutorCreado);
			result.addObject("edit", true);
			result.addObject("message", "error");
			result.addObject("saveURL", "app/tutor/save.do");
		} else {
			try {
				beginTransaction();
				txStatus.flush();
				tutorService.save(tutorCreado);
				txStatus.flush();
				commitTransaction();
				result = new ModelAndView("tutor/alta");
				result.addObject("tutorCreado", tutorCreado);
				result.addObject("edit", true);
				result.addObject("saveURL", "app/tutor/save.do");
				result.addObject("message", "ok");
			} catch (Exception oops) {
				result.addObject("tutorCreado", tutorCreado);
				result.addObject("message", "error");
				result.addObject("edit", true);
				result.addObject("saveURL", "app/tutor/save.do");
				try {
					rollbackTransaction();
				} catch (NullPointerException e) {
					LOGGER.error(e);
				}

				LOGGER.error(oops);
			}
		}
		return result;
	}

	@RequestMapping(value = "/verMiPerfil", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView edit() {
		PadreMadreOTutor tutor = tutorService.findPrincipal();
		Assert.notNull(tutor);
		ModelAndView result = new ModelAndView("tutor/alta", "tutorCreado",
				tutor);
		result.addObject("saveURL", "app/tutor/save.do");
		result.addObject("edit", true);
		return result;
	}

	@RequestMapping(value = "/verNotificaciones", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView misNotificaciones() {
		PadreMadreOTutor tutor = tutorService.findPrincipal();

		Assert.notNull(tutor);

		ModelAndView result = new ModelAndView("tutor/verNotificaciones");
		result.addObject("requestURI", "app/tutor/verNotificaciones.do");
		result.addObject("notificacionesEmitidas",
				notificacionService.findTutorEmitidas(tutor));
		result.addObject("notificacionesRecibidas",
				notificacionService.findTutorRecibidas(tutor));
		return result;
	}

	@RequestMapping(value = "/verCitas", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView misCitas() {
		PadreMadreOTutor tutor = tutorService.findPrincipal();

		Assert.notNull(tutor);

		ModelAndView result = new ModelAndView("tutor/verCitas");
		result.addObject("requestURI", "app/tutor/verCitas.do");
		result.addObject("notificacionesEmitidas",
				citaService.findTutorEmitidas(tutor));
		result.addObject("notificacionesRecibidas",
				citaService.findTutorRecibidas(tutor));
		return result;
	}

	@RequestMapping(value = "/cambiarClave", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView cambiarPassword() {
		PadreMadreOTutor tutor = tutorService.findPrincipal();
		Assert.notNull(tutor);
		ModelAndView result = new ModelAndView("tutor/cambiarClave");
		return result;
	}

	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarPassword(String password, String passwordConfirm) {
		org.springframework.security.authentication.encoding.Md5PasswordEncoder encoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
		ModelAndView result;
		String hash1 = encoder.encodePassword(password, null);
		String hash2 = encoder.encodePassword(passwordConfirm, null);
		if (hash1.compareTo(hash2) == 0 && hash1.equals(hash2)
				&& password.length() > 4) {
			result = new ModelAndView("tutor/cambiarClave");
			result.addObject("message", "ok");
			try {
				beginTransaction();
				tutorService.updatePassword(hash1);
				commitTransaction();
			} catch (Exception e) {
				rollbackTransaction();
				result = new ModelAndView("tutor/cambiarClave");
				result.addObject("message", "error");
			}
		} else {
			result = new ModelAndView("tutor/cambiarClave");
			result.addObject("message", "error");
		}

		return result;
	}

	@RequestMapping(value = "/verCalendario", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView mostrarCalendario() {

		PadreMadreOTutor profesorCreado = tutorService.findPrincipal();
		Assert.notNull(profesorCreado);
		ModelAndView result = new ModelAndView("tutor/calendario");
		return result;
	}

}
