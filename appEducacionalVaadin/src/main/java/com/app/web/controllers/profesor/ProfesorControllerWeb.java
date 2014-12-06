/**
 * ProfesorControllerWeb.java
 * appEducacional
 * 18/03/2014 16:33:49
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Profesor;

@Controller
@Transactional
@RequestMapping("/profesor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorControllerWeb extends AbstractController {

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ProfesorControllerWeb.class);

	// Services

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

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

	@RequestMapping(value = "/altaProfesor", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Profesor p, BindingResult bind) {
		ModelAndView result;

		if (bind.hasErrors()) {
			result = new ModelAndView("profesor/crear", "profesorCreado", p);
			result.addObject("message", "error");
			result.addObject("edit", true);
			result.addObject("saveURL", "app/profesor/altaProfesor.do");
		} else {
			try {
				beginTransaction();
				profesorService.save(p);
				commitTransaction();
				result = new ModelAndView("profesor/crear", "profesorCreado", p);
				result.addObject("message", "ok");
				result.addObject("edit", true);
				result.addObject("saveURL", "app/profesor/altaProfesor.do");
			} catch (IllegalArgumentException assertError) {

				rollbackTransaction();
				result = new ModelAndView("profesor/crear", "profesorCreado", p);
				result.addObject("saveURL", "app/profesor/altaProfesor.do");
				return result;
			} catch (Exception ex) {
				LOGGER.error(ex);
				rollbackTransaction();
				result = new ModelAndView("profesor/crear", "profesorCreado", p);
				result.addObject("message", "pasarLista.error");
				result.addObject("edit", true);
				result.addObject("saveURL", "app/profesor/altaProfesor.do");
				return result;
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

		Profesor profesorCreado = profesorService.findPrincipal();
		Assert.notNull(profesorCreado);
		ModelAndView result = new ModelAndView("profesor/crear",
				"profesorCreado", profesorCreado);
		result.addObject("edit", true);
		result.addObject("saveURL", "app/profesor/altaProfesor.do");
		return result;
	}

	@RequestMapping(value = "/verNotificaciones", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView misNotificaciones() {
		Profesor tutor = profesorService.findPrincipal();

		Assert.notNull(tutor);
		ModelAndView result = new ModelAndView("profesor/verNotificaciones");
		result.addObject("requestURI", "app/profesor/verNotificaciones.do");
		result.addObject("notificacionesEmitidas",
				notificacionService.findProfesorEmitidas(tutor));
		result.addObject("notificacionesRecibidas",
				notificacionService.findProfesorRecibidas(tutor));
		return result;
	}

	@RequestMapping(value = "/verCitas", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView misCitas() {
		Profesor tutor = profesorService.findPrincipal();

		Assert.notNull(tutor);

		ModelAndView result = new ModelAndView("profesor/verCitas");
		result.addObject("requestURI", "app/profesor/verCitas.do");
		result.addObject("notificacionesEmitidas",
				citaService.findProfesorEmitidas(tutor));
		result.addObject("notificacionesRecibidas",
				citaService.findProfesorRecibidas(tutor));
		return result;
	}

	@RequestMapping(value = "/cambiarClave", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView cambiarPassword() {
		Profesor profe = profesorService.findPrincipal();
		Assert.notNull(profe);
		ModelAndView result = new ModelAndView("profesor/cambiarClave");
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
			result = new ModelAndView("profesor/cambiarClave");
			result.addObject("message", "ok");
			try {
				beginTransaction();
				profesorService.updatePassword(hash1);
				commitTransaction();
			} catch (Exception e) {
				rollbackTransaction();
				result = new ModelAndView("profesor/cambiarClave");
				result.addObject("message", "error");
			}
		} else {
			result = new ModelAndView("profesor/cambiarClave");
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

		Profesor profesorCreado = profesorService.findPrincipal();
		Assert.notNull(profesorCreado);
		ModelAndView result = new ModelAndView("profesor/calendario");
		return result;
	}
}
