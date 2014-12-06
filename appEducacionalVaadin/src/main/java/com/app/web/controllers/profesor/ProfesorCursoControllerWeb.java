/**
 * ProfesorAsignaturaControllerWeb.java
 * appEducacional
 * 15/05/2014 16:32:13
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Curso;

@Controller
@Transactional
@RequestMapping("/profesor/curso")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorCursoControllerWeb extends AbstractController {

	// Services

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

	@RequestMapping(value = "/estadisticasCurso", method = RequestMethod.GET)
	public ModelAndView showEstadisticas() {

		Collection<Curso> cursos = profesorService.getCursosImparteDocencia();
		ModelAndView result = new ModelAndView("profesor/estadisticas",
				"cursos", cursos);
		return result;
	}

}
