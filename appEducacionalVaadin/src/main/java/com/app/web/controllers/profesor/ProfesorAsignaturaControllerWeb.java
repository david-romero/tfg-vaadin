/**
 * ProfesorAsignaturaControllerWeb.java
 * appEducacional
 * 15/05/2014 16:32:13
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AsignaturaService;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.ItemEvaluable;

@Controller
@Transactional
@RequestMapping("/profesor/asignatura")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorAsignaturaControllerWeb extends AbstractController {

	// Services

	@Autowired
	/**
	 * 
	 */
	private AsignaturaService asignaturaService;

	@RequestMapping(value = "/establecerCriterios", method = RequestMethod.GET)
	public ModelAndView getCriterios() throws ClassNotFoundException {
		Reflections reflections = new Reflections("com.app.domain");

		Set<Class<? extends ItemEvaluable>> subTypes = reflections
				.getSubTypesOf(ItemEvaluable.class);
		List<Asignatura> asignaturas = (List<Asignatura>) asignaturaService
				.findAllByProfesor();
		ModelAndView result = new ModelAndView("profesor/establecerCriterios",
				"asignaturas", asignaturas);
		result.addObject("items", subTypes);
		return result;
	}

}
