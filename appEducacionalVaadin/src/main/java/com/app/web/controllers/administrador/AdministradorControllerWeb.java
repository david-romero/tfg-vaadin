/**
 * AdministradorControllerWeb.java
 * appEducacional
 * 22/06/2014 16:43:02
 * Copyright David Romero Alcaide
 * com.app.web.controllers.administrador
 */
package com.app.web.controllers.administrador;

import java.util.Collection;

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
import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Authority;
import com.google.common.collect.Lists;

@Controller
@Transactional
@RequestMapping("/admin")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class AdministradorControllerWeb extends AbstractController {

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(AdministradorControllerWeb.class);

	@Autowired
	/**
	 * 
	 */
	private AdministradorService adminService;

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

	@Autowired
	/**
	 * 
	 */
	private PadreMadreOTutorService padreService;

	@RequestMapping(value = "/banear", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView ban() {

		Collection<Profesor> allProfesores = adminService.getAllProfesores();
		Collection<PadreMadreOTutor> alltutores = adminService.getAllTutores();
		Assert.notNull(allProfesores);
		Assert.notNull(alltutores);
		ModelAndView result = new ModelAndView("admin/ban");
		result.addObject("profesores", allProfesores);
		result.addObject("tutores", alltutores);
		return result;
	}

	@RequestMapping(value = "/saveBaneo", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarBaneo(String personaId, String tipo) {

		if (tipo.equals("TUTOR")) {
			PadreMadreOTutor temp = padreService.findOne(Integer
					.valueOf(personaId));
			temp.getUserAccount().setAuthorities(null);
			padreService.save(temp);
		} else {
			Profesor p = profesorService.findOne(Integer.valueOf(personaId));
			p.getUserAccount().setAuthorities(null);
			profesorService.save(p);
		}

		ModelAndView result = ban();
		result.addObject("message", "ok");
		return result;
	}

	@RequestMapping(value = "/unLock", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView quitarBaneo(String personaId, String tipo) {
		ModelAndView result = ban();
		try {
			beginTransaction();
			if (tipo.equals("TUTOR")) {
				PadreMadreOTutor temp = padreService.findOne(Integer
						.valueOf(personaId));
				Collection<Authority> authorities = Lists.newArrayList();
				Authority auth = new Authority();
				auth.setAuthority("TUTOR");
				authorities.add(auth);
				temp.getUserAccount().setAuthorities(authorities);
				padreService.save(temp);
			} else {
				Profesor p = profesorService
						.findOne(Integer.valueOf(personaId));
				Collection<Authority> authorities = Lists.newArrayList();
				Authority auth = new Authority();
				auth.setAuthority("PROFESOR");
				authorities.add(auth);
				p.getUserAccount().setAuthorities(authorities);
				profesorService.save(p);
			}
			commitTransaction();
			result.addObject("message", "ok");
		} catch (Exception opps) {
			rollbackTransaction();
			LOGGER.error(opps.getMessage(),opps);
			result.addObject("message", "error");
		}
		return result;
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView addAdministrador() {
		Administrador administrador = adminService.create();
		ModelAndView result = new ModelAndView("admin/create");
		result.addObject("admin", administrador);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView saveAdministrador(@Valid Administrador admin,
			BindingResult bind) {
		ModelAndView result = new ModelAndView("admin/create");
		if (bind.hasErrors()) {
			result.addObject("admin", admin);
			result.addObject("message", "error");
		} else {
			try {
				beginTransaction();
				org.springframework.security.authentication.encoding.Md5PasswordEncoder encoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
				String hash = encoder.encodePassword(admin.getUserAccount()
						.getPassword(), null);
				admin.getUserAccount().setPassword(hash);
				adminService.save(admin);
				commitTransaction();
				Administrador admin2 = adminService.create();
				result.addObject("admin", admin2);
				result.addObject("message", "ok");
			} catch (Exception exp) {
				LOGGER.error(exp.getMessage(),exp);
				rollbackTransaction();
				result.addObject("admin", admin);
				result.addObject("message", "error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/confirmUser", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView getPersonas() {

		Collection<Profesor> allProfesores = adminService
				.getAllProfesoresSinIdentidad();
		Collection<PadreMadreOTutor> alltutores = adminService
				.getAllTutoresSinIdentidad();
		Assert.notNull(allProfesores);
		Assert.notNull(alltutores);
		ModelAndView result = new ModelAndView("admin/confirm");
		result.addObject("profesores", allProfesores);
		result.addObject("tutores", alltutores);
		return result;
	}

	@RequestMapping(value = "/saveIdentidad", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarIdentidad(String personaId, String tipo) {

		if (tipo.equals("TUTOR")) {
			PadreMadreOTutor temp = padreService.findOne(Integer
					.valueOf(personaId));
			temp.setIdentidadConfirmada(true);
			padreService.save(temp);
		} else {
			Profesor p = profesorService.findOne(Integer.valueOf(personaId));
			p.setIdentidadConfirmada(true);
			profesorService.save(p);
		}

		ModelAndView result = getPersonas();
		result.addObject("message", "ok");
		return result;
	}

}
