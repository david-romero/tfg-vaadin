/**
 * AdministradorControllerWeb.java
 * appEducacional
 * 22/06/2014 16:43:02
 * Copyright David Romero Alcaide
 * com.app.web.controllers.administrador
 */
package com.app.web.controllers.bug;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AdministradorService;
import com.app.domain.model.types.Administrador;
import com.app.infrastructure.CustomMailSender;
import com.app.infrastructure.exceptions.GeneralException;

@Controller
@Transactional
@RequestMapping("/bug")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class BugControllerWeb extends AbstractController {

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(BugControllerWeb.class);
	
	@Autowired
	/**
	 * 
	 */
	private AdministradorService adminService;

	@RequestMapping(value = "/enviar", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView loadSend() {
		ModelAndView result = new ModelAndView("bug/send");
		return result;
	}

	@RequestMapping(value = "/sendBug", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarBug(String email, String informe){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"Spring-Mail.xml");
		CustomMailSender mm = (CustomMailSender) context.getBean("mailMail");
		Collection<Administrador> admins = adminService.findAll();
		for (Administrador admin : admins) {
			if (admin.getEmail().compareTo("") != 0) {
				try {
					mm.sendMail("Informe de Bug", informe, email, admin.getEmail());
				} catch (GeneralException e) {
					LOGGER.error(e.getMessage(),e);
				}
			}
		}

		ModelAndView result = new ModelAndView("welcome/index");
		result.addObject("message", "ok");
		return result;
	}

}
