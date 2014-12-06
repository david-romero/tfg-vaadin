/**
 * TutorAlumnoControllerWeb.java
 * appEducacional
 * 01/04/2014 18:07:42
 * Copyright David Romero Alcaide
 * com.app.web.controllers.tutor
 */
package com.app.web.controllers.tutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AlumnoService;
import com.app.applicationservices.services.CursoService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Profesor;
import com.app.domain.model.types.itemsevaluables.FaltaDeAsistencia;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Controller
@Transactional
@RequestMapping("/tutor/alumno")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class TutorAlumnoControllerWeb extends AbstractController {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger
			.getLogger(TutorControllerWeb.class);

	// Services

	@Autowired
	private PadreMadreOTutorService tutorService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private AlumnoService alumnoService;

	@Autowired
	/**
	 * 
	 */
	private NotificacionService notificacionService;

	// Application context
	@Autowired
	private ApplicationContext appContext;

	@RequestMapping(value = "/verFicha", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView getAlumnos(@RequestParam int alumnoId) {
		Alumno a = alumnoService.findOne(alumnoId);
		Assert.notNull(a);
		Assert.isTrue(tutorService.findPrincipal().getTutorandos().contains(a));
		ModelAndView mv = new ModelAndView("tutor/ficha", "alumno", a);
		mv.addObject("items", alumnoService.obtenerItemsAlumno(a));
		mv.addObject("tutores",a.getPadresMadresOTutores());
		return mv;
	}

	@RequestMapping(value = "/seleccionaTutorando", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView seleccionaTutorando(@RequestParam String accion) {
		List<Alumno> tutorandos = Lists.newArrayList(tutorService
				.findPrincipal().getTutorandos());
		Assert.notNull(tutorandos);
		ModelAndView mv = new ModelAndView("tutor/selecciona", "alumnos",
				tutorandos);
		mv.addObject("accion", accion);
		mv.addObject("tipo", "alumno");
		mv.addObject("requestURI",
				"app/tutor/alumno/seleccionaTutorando.do?accion=verFicha");
		return mv;
	}

	@RequestMapping(value = "/verProfesoresParaCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView getProfesoresMisTutorandos() {

		List<Profesor> alumnos = Lists.newArrayList(tutorService
				.getTodosProfesores());

		ModelAndView mv = new ModelAndView("tutor/selecciona", "alumnos",
				alumnos);
		mv.addObject("accion", "concertarCita");
		mv.addObject("tipo", "profesor");
		mv.addObject("requestURI", "app/tutor/alumno/verProfesoresParaCita.do");
		return mv;
	}

	@RequestMapping(value = "alta", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView altaAlumno() {
		ModelAndView result = new ModelAndView("alumno/alta");
		Alumno alumno = alumnoService.create();
		Assert.notNull(tutorService.findPrincipal());
		Assert.notNull(alumno);
		result.addObject("alumno", alumno);
		HashSet<Curso> cursos = Sets.newHashSet();
		Collection<Curso> allCursos = cursoService.findAll();
		cursos.addAll(allCursos);
		result.addObject("cursos", cursos);
		result.addObject("urlSave", "app/tutor/alumno/altaAlumno.do");
		return result;
	}

	@RequestMapping(value = "altaAlumno", method = RequestMethod.POST, params = "save")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param alumno
	 * @param file
	 * @param bind
	 * @return
	 */
	public ModelAndView guadarAlumno(@RequestParam("foto") MultipartFile file,
			@Valid Alumno alumno, BindingResult bind) {
		ModelAndView result = new ModelAndView("alumno/alta");
		if (bind.hasErrors()) {
			result.addObject("alumno", alumno);
			return result;
		} else {
			PadreMadreOTutor tutor = tutorService.findPrincipal();
			Curso c = alumno.getCurso();
			try {
				byte[] foto = file.getBytes();
				beginTransaction();
				alumno.setImagen(foto);
				Assert.notNull(tutorService.findPrincipal());
				alumno.addPadreMadreOTutor(tutor);
				cursoService.find(c.getNivel(), c.getNivelEducativo(),
						c.getIdentificador(), alumno);
				alumnoService.save(alumno);
				tutorService.save(tutor);
				txStatus.flush();
				commitTransaction();
			} catch (Exception oops) {
				rollbackTransaction();
				LOGGER.error(oops);
			}
		}
		result = altaAlumno();
		result.addObject("alumno", alumno);
		result.addObject("message", "ok");
		return result;
	}

	@RequestMapping(value = "vincular", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView vincular() {
		ModelAndView result = new ModelAndView("alumno/vincular");
		Assert.notNull(tutorService.findPrincipal());
		HashSet<Curso> cursos = Sets.newHashSet();
		Collection<Curso> allCursos = cursoService.findAll();
		cursos.addAll(allCursos);
		result.addObject("cursos", cursos);
		return result;
	}

	@RequestMapping(value = "buscarVinculacion", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView buscarVinculacion(String nombre, String apellidos,
			Curso c, String fechaNacimiento) throws ParseException {
		ModelAndView result;
		SimpleDateFormat sp = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sp.parse(fechaNacimiento);
		if ((nombre == null || nombre.length() < 2)
				|| (apellidos == null || apellidos.length() < 2) || (c == null)
				|| (date == null || date.after(new Date()))) {
			result = vincular();
			result.addObject("message", "error");

		} else {
			Alumno a = alumnoService.findAlumno(nombre, apellidos, c, date);
			if (a == null) {
				result = vincular();
				result.addObject("message", "error1");
			} else {
				result = new ModelAndView("alumno/confirmarVinculacion");
				result.addObject("alumno", a);
			}
		}
		return result;
	}

	@RequestMapping(value = "guardarVinculacion", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView guardarVinculacion(Alumno alum) throws ParseException {

		PadreMadreOTutor tutor = tutorService.findPrincipal();
		try {
			beginTransaction();
			alumnoService.vincularConProfesor(alum, tutor);
			commitTransaction();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
			try {
				rollbackTransaction();
			} catch (NullPointerException e2) {
				LOGGER.error(e2.getMessage(), e2.getCause());
			}
		}
		ModelAndView result = new ModelAndView("welcome/index");
		result.addObject("message", "ok");

		return result;
	}

	@RequestMapping(value = "justificarFalta", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView obtenerFaltas(int alumnoId) {
		Alumno alum = alumnoService.findOne(alumnoId);
		Assert.notNull(alum);
		Assert.isTrue(alum.getPadresMadresOTutores().contains(
				tutorService.findPrincipal()));
		ModelAndView result = new ModelAndView("alumno/verFaltas");
		Assert.notNull(tutorService.findPrincipal());
		Assert.isTrue(tutorService.findPrincipal().isIdentidadConfirmada());
		Collection<FaltaDeAsistencia> faltasAlumno = alumnoService
				.getFaltasAlumno(alum);
		result.addObject("faltasAlumno", faltasAlumno);
		result.addObject("alumnoId", alumnoId);
		return result;
	}

	@RequestMapping(value = "justificarFalta", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView justificarFalta(@RequestParam("alumnId") int alumnId,
			@RequestParam("falta") int falta,
			@RequestParam("motivo") String motivo) {
		ModelAndView result;
		// Obtengo el tipo de evento
		com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
				.getBean("FaltaDeAsistenciaService");
		FaltaDeAsistencia faltaAsistencia = (FaltaDeAsistencia) servicio
				.findOne(falta);
		PadreMadreOTutor padre = tutorService.findPrincipal();
		Alumno alum = alumnoService.findOne(alumnId);
		try {
			beginTransaction();
			Notificacion noti = notificacionService.create();
			noti.setEmisor("TUTOR");
			SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
			noti.setContenido("El Padre, Madre o Tutor " + padre.getNombre()
					+ " " + padre.getApellidos()
					+ " justifica la falta de asistencia del alumno "
					+ alum.getNombre() + " " + alum.getApellidos()
					+ " de la fecha " + sp.format(faltaAsistencia.getFecha())
					+ " con el siguiente motivo: " + motivo);
			noti.setFecha(new Date());
			noti.setPadreMadreOTutor(padre);
			noti.setProfesor(faltaAsistencia.getAsignatura().getProfesor());
			notificacionService.save(noti);
			commitTransaction();
		} catch (Exception o) {
			rollbackTransaction();
		}
		// /////////////////////////////////////////////////////////////////////////////////////
		result = obtenerFaltas(alumnId);
		result.addObject("message", "ok");
		return result;
	}

	@RequestMapping(value = "/estadisticas", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView verEstadisticas(@RequestParam int alumnoId) {
		ModelAndView result = new ModelAndView("alumno/estadisticas");
		Alumno a = alumnoService.findOne(alumnoId);
		result.addObject("alumno", a);
		return result;
	}

}
