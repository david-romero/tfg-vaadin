/**
 * ProfesorControllerWeb.java
 * appEducacional
 * 18/03/2014 16:33:49
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AsignaturaService;
import com.app.applicationservices.services.CursoService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Credentials;

@Controller
@Transactional
@RequestMapping("/alta/profesor")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class AltaProfesorControllerWeb extends AbstractController {

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
	private AsignaturaService asignaturaService;

	@Autowired
	/**
	 * 
	 */
	private CursoService cursoService;

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(AltaProfesorControllerWeb.class);

	@RequestMapping(value = "/alta", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView create() {

		Profesor profesorCreado = profesorService.create();
		Assert.notNull(profesorCreado);
		ModelAndView result = new ModelAndView("profesor/crear",
				"profesorCreado", profesorCreado);
		result.addObject("saveURL", "app/alta/profesor/altaProfesor.do");
		result.addObject("edit", false);
		return result;
	}

	@RequestMapping(value = "/altaProfesor", method = RequestMethod.POST, params = "save")
	public ModelAndView alta(String[] cursosCombo, @Valid Profesor p,
			BindingResult bind) {
		ModelAndView result;

		if (bind.hasErrors()) {
			result = new ModelAndView("profesor/crear", "profesorCreado", p);
			result.addObject("saveURL", "app/alta/profesor/altaProfesor.do");
			result.addObject("edit", false);
		} else {
			try {
				beginTransaction();
				org.springframework.security.authentication.encoding.Md5PasswordEncoder encoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
				String hash = encoder.encodePassword(p.getUserAccount()
						.getPassword(), null);
				p.getUserAccount().setPassword(hash);
				profesorService.save(p);
				commitTransaction();
			} catch (IllegalArgumentException assertError) {
				LOGGER.error(assertError.getMessage(),assertError);
				rollbackTransaction();
				result = new ModelAndView("profesor/crear", "profesorCreado", p);
				result.addObject("message", assertError.getMessage());
				result.addObject("saveURL", "app/alta/profesor/altaProfesor.do");
				result.addObject("edit", false);
				return result;
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(),ex);
				rollbackTransaction();
				result = new ModelAndView("profesor/crear", "profesorCreado", p);
				result.addObject("message", "pasarLista.error");
				result.addObject("saveURL", "app/alta/profesor/altaProfesor.do");
				result.addObject("edit", false);
				return result;
			}
			if (cursosCombo != null)
				for (String a : cursosCombo) {
					String nombreCurso = a.split("/")[0];
					String nombreAsignatura = a.split("/")[1];
					Asignatura asign = null;
					Curso curso = null;
					try {
						beginTransaction();
						curso = cursoService.findOrCreate(Integer
								.valueOf(nombreCurso.split(" ")[0].replace("º",
										"")), nombreCurso.split(" ")[1],
								nombreCurso.split(" ")[2].charAt(0));
						asign = asignaturaService.findByName(nombreAsignatura);
						commitTransaction();
					} catch (IllegalArgumentException assertError) {
						LOGGER.error(assertError.getMessage(),assertError);
						rollbackTransaction();
						result = new ModelAndView("profesor/crear",
								"profesorCreado", p);
						result.addObject("message", assertError.getMessage());
						result.addObject("saveURL",
								"app/alta/profesor/altaProfesor.do");
						result.addObject("edit", false);
						return result;
					} catch (Exception oops) {
						LOGGER.error(oops.getMessage(),oops);
						rollbackTransaction();
						result = new ModelAndView("profesor/crear",
								"profesorCreado", p);
						result.addObject("message", "pasarLista.error");
						result.addObject("saveURL",
								"app/alta/profesor/altaProfesor.do");
						result.addObject("edit", false);
						return result;
					}
					if (asign == null) {
						asign = asignaturaService.create();
						asign.setNombre(nombreAsignatura);

					}
					try {
						beginTransaction();
						txStatus.flush();
						curso = cursoService.findOrCreate(Integer
								.valueOf(nombreCurso.split(" ")[0].replace("º",
										"")), nombreCurso.split(" ")[1],
								nombreCurso.split(" ")[2].charAt(0));
						asign.setCurso(curso);
						p = profesorService.findByDNI(p.getDNI());
						asign.setProfesor(p);
						asignaturaService.save(asign);
						commitTransaction();
					} catch (IllegalArgumentException assertError) {
						LOGGER.error(assertError.getMessage(),assertError);
						rollbackTransaction();
						result = new ModelAndView("profesor/crear",
								"profesorCreado", p);
						result.addObject("message", assertError.getMessage());
						result.addObject("saveURL",
								"app/alta/profesor/altaProfesor.do");
						result.addObject("edit", false);
						return result;
					} catch (Exception oops) {
						LOGGER.error(oops.getMessage(),oops);
						rollbackTransaction();
						result = new ModelAndView("profesor/crear",
								"profesorCreado", p);
						result.addObject("message", "pasarLista.error");
						result.addObject("saveURL",
								"app/alta/profesor/altaProfesor.do");
						result.addObject("edit", false);
						return result;
					}
					try {
						beginTransaction();
						txStatus.flush();
						asign = asignaturaService.findByProfesorCurso(p, curso,
								asign.getNombre());
						curso.addAsignatura(asign);
						cursoService.save(curso);
						commitTransaction();
					} catch (Exception oops) {
						LOGGER.error(oops.getMessage(),oops);
						rollbackTransaction();
						result = new ModelAndView("profesor/crear",
								"profesorCreado", p);
						result.addObject("message", "pasarLista.error");
						result.addObject("saveURL",
								"app/alta/profesor/altaProfesor.do");
						result.addObject("edit", false);
						return result;
					}
				}

			/*
			 *  Vuelvo a la pagina de login si todo ha ido bien
			 */
			result = new ModelAndView("security/login");
			Credentials credenciales = new Credentials();
			credenciales.setJ_username("");
			credenciales.setPassword("");
			result.addObject("credentials", credenciales);
		}
		return result;
	}

}
