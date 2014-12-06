/**
 * TutorControllerWeb.java
 * appEducacional
 * 26/03/2014 17:34:33
 * Copyright David Romero Alcaide
 * com.app.web.controllers.tutor
 */
package com.app.web.controllers.alta;

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
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.domain.model.types.PadreMadreOTutor;

@Controller
@Transactional
@RequestMapping("alta/tutor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class AltaTutorControllerWeb extends AbstractController {

	private static final Logger LOGGER = Logger
			.getLogger(AltaTutorControllerWeb.class);

	// Services

	@Autowired
	private PadreMadreOTutorService tutorService;

	@RequestMapping(value = "/alta", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView create() {
		PadreMadreOTutor tutor = tutorService.create();
		Assert.notNull(tutor);
		ModelAndView result = new ModelAndView("tutor/alta", "tutorCreado",
				tutor);
		result.addObject("saveURL", "app/alta/tutor/save.do");
		return result;
	}

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
			return result;
		} else {
			try {
				beginTransaction();
				org.springframework.security.authentication.encoding.Md5PasswordEncoder encoder = 
						new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
				String hash = encoder.encodePassword(tutorCreado.getUserAccount()
						.getPassword(), null);
				tutorCreado.getUserAccount().setPassword(
						hash);
				tutorService.save(tutorCreado);
				commitTransaction();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (Exception oops) {

				result.addObject("tutorCreado", tutorCreado);
				result.addObject("message", "error");
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

}
