/**
 * ProfesorAsignaturaControllerWeb.java
 * appEducacional
 * 15/05/2014 16:32:13
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.api.controllers.profesor;

/**
 * imports
 */
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AsignaturaService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Asignatura;

@Controller
@Transactional
@RequestMapping("/profesor/asignatura")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorAsignaturaController extends AbstractController {

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

	@RequestMapping(value = "guardarCriterios", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void setCriterios(String asignatura, int examen, int actitud,
			int falta, int retraso, int cuaderno, int trabajo, int actividad,
			int ejercicio) {
		Assert.notNull(profesorService.findPrincipal());
		Asignatura asign = asignaturaService.findOne(Integer
				.valueOf(asignatura));
		Map<String, Integer> criterios = asign.getCriteriosDeEvaluacion();
		criterios.put("Examen", examen);
		criterios.put("Actitud", actitud);
		criterios.put("FaltaDeAsistencia", falta);
		criterios.put("Retraso", retraso);
		criterios.put("Cuaderno", cuaderno);
		criterios.put("Trabajo", trabajo);
		criterios.put("Actividad", actividad);
		criterios.put("Ejercicio", ejercicio);
		asign.setCriteriosDeEvaluacion(criterios);
		asignaturaService.save(asign);
	}

}
