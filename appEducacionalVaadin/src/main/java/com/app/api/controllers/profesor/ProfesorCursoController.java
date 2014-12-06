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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AsignaturaService;
import com.app.applicationservices.services.CursoService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@Transactional
@RequestMapping("/profesor/curso")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorCursoController extends AbstractController {

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

	@RequestMapping(value = "/estadisticasPorCurso", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Double> getEstadisticas(@RequestParam("curso") int curso,
			@RequestParam("evaluacion") int evaluacion) {

		Map<String, Double> map = Maps.newHashMap();
		Curso c = cursoService.findOne(curso);
		Assert.isTrue(profesorService.getCursosImparteDocencia().contains(c));
		Asignatura asign = findAsignatura(c);
		List<Double> alumnos = Lists.newArrayList();
		iniciarContadores(alumnos);
		double totalAlumnos = c.getAlumnos().size();
		for (Alumno a : c.getAlumnos()) {
			calcularNotaPorAlumno(evaluacion, asign, alumnos, a);
		}
		map.put("Suspensos", alumnos.get(0) / totalAlumnos * 100);
		map.put("Aprobados", alumnos.get(1) / totalAlumnos * 100);
		map.put("Notables", alumnos.get(2) / totalAlumnos * 100);
		map.put("Sobresalientes", alumnos.get(3) / totalAlumnos * 100);
		return map;
	}

	/**
	 * @author David Romero Alcaide
	 * @param evaluacion
	 * @param asign
	 * @param alumnos
	 * @param a
	 */
	private void calcularNotaPorAlumno(int evaluacion, Asignatura asign,
			List<Double> alumnos, Alumno a) {
		for (NotaPorEvaluacion nota : a.getNotasPorEvaluacion()) {
			if (nota.getAsignatura().equals(asign)
					&& nota.getEvaluacion().getIndicador() == evaluacion) {
				aniadirNotasCalculadas(alumnos, nota);
			}
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param alumnos
	 * @param nota
	 */
	private void aniadirNotasCalculadas(List<Double> alumnos,
			NotaPorEvaluacion nota) {
		if (nota.getNotaFinal() < 5) {
			alumnos.set(0, alumnos.get(0) + 1);
		} else if (nota.getNotaFinal() >= 5
				&& nota.getNotaFinal() < 7) {
			alumnos.set(1, alumnos.get(1) + 1);
		} else if (nota.getNotaFinal() >= 7
				&& nota.getNotaFinal() < 9) {
			alumnos.set(2, alumnos.get(2) + 1);
		} else if (nota.getNotaFinal() >= 9
				&& nota.getNotaFinal() <= 10) {
			alumnos.set(3, alumnos.get(3) + 1);
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param alumnos
	 */
	private void iniciarContadores(List<Double> alumnos) {
		final int numeroParticionesEnNotas = 4;
		for(int i = 0; i < numeroParticionesEnNotas; i++){
			alumnos.add(0.0);
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param c
	 * @param asign
	 * @return
	 */
	private Asignatura findAsignatura(Curso c) {
		Asignatura asign = null;
		for (Asignatura asignatura : asignaturaService.findAllByProfesor()) {
			if (asignatura.getCurso().equals(c)) {
				asign = asignatura;
			}
		}
		Assert.notNull(asign);
		return asign;
	}

}
