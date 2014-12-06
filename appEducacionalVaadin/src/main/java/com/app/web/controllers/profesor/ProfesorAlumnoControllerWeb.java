/**
 * ProfesorAlumnoController.java
 * appEducacional
 * 26/01/2014 12:57:53
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AlumnoService;
import com.app.applicationservices.services.AsignaturaService;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.CursoService;
import com.app.applicationservices.services.NotaPorEvaluacionService;
import com.app.applicationservices.services.NotificacionService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.Notificacion;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.utility.FechaUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Controller
@Transactional
@RequestMapping("/profesor/alumno")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorAlumnoControllerWeb extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	/**
	 * Lógica de negocio de profesor
	 */
	private ProfesorService profesorService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private AlumnoService alumnoService;

	@Autowired
	private AsignaturaService asignaturaService;

	@Autowired
	private NotaPorEvaluacionService notaPorEvaluacionService;

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private CitaService citaService;

	@Autowired
	private PadreMadreOTutorService padreMadreOTutorService;

	private static final Logger LOGGER = Logger
			.getLogger(ProfesorAlumnoControllerWeb.class);

	/**
	 * Constructor
	 */
	public ProfesorAlumnoControllerWeb() {
		super();
	}

	@RequestMapping(value = "/pasarLista", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("profesor/pasarLista");
		Collection<Curso> cursos = profesorService.getCursosImparteDocencia();
		result.addObject("cursos", cursos);
		return result;
	}

	@RequestMapping(value = "/buscarAlumnosPorCurso", method = RequestMethod.GET)
	public ModelAndView buscarAlumnosPorCurso(
			@RequestParam(required = true) int cursoId) {
		ModelAndView result;
		Collection<Alumno> alumnosDelCurso = cursoService
				.getAlumnosEnCurso(cursoId);
		result = new ModelAndView("profesor/pasarLista");
		result.addObject("alumnos", alumnosDelCurso);
		Collection<Curso> cursos = cursoService.findAll();
		result.addObject("cursos", cursos);
		result.addObject("cursoId", cursoId);
		result.addObject("RequestURI", "profesor/alumno/pasarLista.do");
		return result;
	}

	@RequestMapping(value = "/noAsiste", method = RequestMethod.GET)
	public ModelAndView guardarList(@RequestParam(required = true) int cursoId,
			@RequestParam(required = true) int alumnoId) {
		ModelAndView result;
		Alumno alumno = alumnoService.findOne(alumnoId);
		Curso curso = cursoService.findOne(cursoId);
		Asignatura a = profesorService.getAsignaturaCursoProfesor(curso);
		try {
			Assert.notNull(alumno, "pasarLista.idAlumno");
			Assert.notNull(curso, "pasarLista.idCurso");
			beginTransaction();
			alumnoService.anadirDiaDiaDeCalendario(alumno,
					new Date(System.currentTimeMillis()));
			commitTransaction();
		} catch (IllegalArgumentException falloAssert) {
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", falloAssert.getMessage());
		} catch (Exception oops) {
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.error");
			try {
				rollbackTransaction();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		try {
			Assert.notNull(a, "pasarLista.error");
			beginTransaction();
			SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
			alumnoService.crearItemEvaluacion(
					new Date(System.currentTimeMillis()),
					alumno,
					a,
					"FaltaDeAsistencia",
					"Falta de Asistencia de "
							+ sp.format(new Date(System.currentTimeMillis())));
			commitTransaction();
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.ok");
		} catch (IllegalArgumentException falloAssert) {
			LOGGER.error(falloAssert);
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", falloAssert.getMessage());
		} catch (Exception oops) {
			LOGGER.error(oops);
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.error");
			try {
				rollbackTransaction();
				LOGGER.error(oops.getMessage());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		return result;
	}

	@RequestMapping(value = "/retraso", method = RequestMethod.GET)
	public ModelAndView retraso(@RequestParam(required = true) int cursoId,
			@RequestParam(required = true) int alumnoId) {
		ModelAndView result;
		Alumno alumno = alumnoService.findOne(alumnoId);
		Curso curso = cursoService.findOne(cursoId);
		Asignatura a = profesorService.getAsignaturaCursoProfesor(curso);
		try {
			Assert.notNull(alumno, "pasarLista.idAlumno");
			Assert.notNull(curso, "pasarLista.idCurso");
			beginTransaction();
			alumnoService.anadirDiaDiaDeCalendario(alumno,
					new Date(System.currentTimeMillis()));
			commitTransaction();
		} catch (IllegalArgumentException falloAssert) {
			result = buscarAlumnosPorCurso(cursoId);
			LOGGER.error(falloAssert.getMessage());
			result.addObject("message", falloAssert.getMessage());
		} catch (Exception oops) {
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.error");
			try {
				rollbackTransaction();
				LOGGER.error(oops.getMessage());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		try {
			Assert.notNull(a, "pasarLista.error");
			beginTransaction();
			alumnoService.establecerRetraso(a, alumno);
			commitTransaction();
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.ok");
		} catch (IllegalArgumentException falloAssert) {
			result = buscarAlumnosPorCurso(cursoId);
			LOGGER.error(falloAssert.getMessage());
			result.addObject("message", falloAssert.getMessage());
		} catch (Exception oops) {
			LOGGER.error(oops);
			result = buscarAlumnosPorCurso(cursoId);
			result.addObject("message", "pasarLista.error");
			try {
				rollbackTransaction();
				LOGGER.error(oops.getMessage());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		return result;
	}

	@RequestMapping(value = "/realizarEvaluacion", method = RequestMethod.GET)
	public ModelAndView realizarEvaluacion() {
		ModelAndView result;
		List<Alumno> alumnos = Lists.newArrayList(profesorService
				.getTodosLosAlumnosProfesor());
		result = new ModelAndView("profesor/realizarEvaluacion");
		result.addObject("requestURI",
				"app/profesor/alumno/realizarEvaluacion.do");
		result.addObject("url",
				"app/profesor/alumno/obtenerItemsAlumno.do?alumnoId");
		result.addObject("alumnos", alumnos);
		return result;
	}

	@RequestMapping(value = "/obtenerItemsAlumno", method = RequestMethod.GET)
	public ModelAndView obtenerItemsAlumno(@RequestParam int alumnoId,
			boolean calcularNotas) {
		ModelAndView result;
		result = new ModelAndView("profesor/realizarEvaluacionAlumno");
		Alumno alum = alumnoService.findOne(alumnoId);
		Asignatura a = profesorService.getAsignaturaCursoProfesor(alum
				.getCurso());
		List<NotaPorEvaluacion> notasActuales = null;
		try {
			beginTransaction();
			notasActuales = notaPorEvaluacionService.getNotasPorEvaluacion(
					alum, a);
			if (notasActuales.size() != 3) {
				notaPorEvaluacionService.createNotasPorEvaluacion(alum, a);
			}
			commitTransaction();
		} catch (Exception oops) {
			LOGGER.error(oops);
			rollbackTransaction();
			result = realizarEvaluacion();
			result.addObject("message", "pasarLista.error");
		}
		Assert.isTrue(a.getCriteriosDeEvaluacion().size() > 0,
				"evaluacion.criterios");
		Collection<ItemEvaluable> items = alumnoService
				.obtenerItemsAlumnoAsignatura(alum, a);
		if (calcularNotas) {
			List<NotaPorEvaluacion> notas = alumnoService
					.obtenerNotasPorEvaluacion(alum,a);
			result.addObject("nota1", notas.get(0));
			result.addObject("nota2", notas.get(1));
			result.addObject("nota3", notas.get(2));
		} else {
			try {
				result.addObject("nota1", notasActuales.get(0));
				result.addObject("nota2", notasActuales.get(1));
				result.addObject("nota3", notasActuales.get(2));
			} catch (IndexOutOfBoundsException exception) {
				List<NotaPorEvaluacion> notas = alumnoService
						.obtenerNotasPorEvaluacion(alum,a);
				result.addObject("nota1", notas.get(0));
				result.addObject("nota2", notas.get(1));
				result.addObject("nota3", notas.get(2));
			}
		}
		result.addObject("items", items);
		result.addObject("alumno", alum);
		return result;
	}

	@RequestMapping(value = "/examen/{idAlumno}/{idItem}/save", method = RequestMethod.POST)
	public ModelAndView guardarExamen(String nota,
			@PathVariable("idAlumno") int idAlumno,
			@PathVariable("idItem") int idItem) {
		ModelAndView result;
		double notaDef = 0;
		try {
			notaDef = Double.valueOf(nota);
		} catch (NumberFormatException e) {
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "item.calificacionError");
		}

		try {
			beginTransaction();
			profesorService.guardarCalificacion(idItem, notaDef, "Examen");
			commitTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
		} catch (Exception oops) {
			LOGGER.error(oops);
			rollbackTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "pasarLista.error");
		}
		return result;
	}

	@RequestMapping(value = "/actividad/{idAlumno}/{idItem}/save", method = RequestMethod.POST)
	public ModelAndView guardarActividad(
			@PathVariable("idAlumno") int idAlumno,
			@PathVariable("idItem") int idItem,
			@RequestParam("nota") double calificacion) {
		ModelAndView result;
		double notaDef = 0;
		try {
			notaDef = Double.valueOf(calificacion);
		} catch (NumberFormatException e) {
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "item.calificacionError");
		}

		try {
			beginTransaction();
			profesorService.guardarCalificacion(idItem, notaDef, "Actividad");
			commitTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
		} catch (Exception oops) {
			LOGGER.error(oops);
			rollbackTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "pasarLista.error");
		}
		return result;
	}

	@RequestMapping(value = "/trabajo/{idAlumno}/{idItem}/save", method = RequestMethod.POST)
	public ModelAndView guardarTrabajo(@RequestParam("nota") String nota,
			@PathVariable("idAlumno") int idAlumno,
			@PathVariable("idItem") int idItem) {
		ModelAndView result;
		double notaDef = 0;
		try {
			notaDef = Double.valueOf(nota);
		} catch (NumberFormatException e) {
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "item.calificacionError");
		}

		try {
			beginTransaction();
			profesorService.guardarCalificacion(idItem, notaDef, "Trabajo");
			commitTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
		} catch (Exception oops) {
			LOGGER.error(oops);
			rollbackTransaction();
			result = obtenerItemsAlumno(idAlumno, true);
			result.addObject("message", "pasarLista.error");
		}
		return result;
	}

	@RequestMapping(value = "/guardarNota", method = RequestMethod.POST, params = "save")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param nota
	 * @param bind
	 * @return
	 */
	public ModelAndView guardarNotaPorEvaluacion(@Valid NotaPorEvaluacion nota,
			BindingResult bind) {
		ModelAndView result;
		if (bind.hasErrors()) {
			result = obtenerItemsAlumno(nota.getAlumno().getId(), false);
			result.addObject("message", "item.calificacionError");
		} else {
			try {
				beginTransaction();
				notaPorEvaluacionService.save(nota);
				commitTransaction();
				result = obtenerItemsAlumno(nota.getAlumno().getId(), false);
			} catch (Exception oops) {
				rollbackTransaction();
				result = obtenerItemsAlumno(nota.getAlumno().getId(), false);
				result.addObject("message", "pasarLista.error");
			}
		}

		return result;
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
		Assert.notNull(alumno);
		HashSet<Curso> cursos = Sets.newHashSet();
		Collection<Asignatura> asignaturas = asignaturaService
				.findAllByProfesor();
		for (Asignatura asign : asignaturas) {
			cursos.add(asign.getCurso());
		}
		result.addObject("cursos", cursos);
		result.addObject("urlSave", "app/profesor/alumno/altaAlumno.do");
		result.addObject("alumno", alumno);
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
			Collection<Asignatura> asignaturas = asignaturaService
					.findAllByProfesor();
			result.addObject("asignaturas", asignaturas);
			return result;
		} else {
			try {
				byte[] foto = file.getBytes();
				beginTransaction();
				alumno.setImagen(foto);
				Curso c = alumno.getCurso();
				cursoService.find(c.getNivel(), c.getNivelEducativo(),
						c.getIdentificador(), alumno);
				alumnoService.save(alumno);
				commitTransaction();
			} catch (Exception oops) {
				rollbackTransaction();
				LOGGER.error(oops);
			}
		}
		result = seleccionarAlumnoParaVerFicha();
		return result;
	}

	@RequestMapping(value = "enviarNotificacion", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView enviarNotificacion() {
		try {
			ModelAndView result = new ModelAndView("alumno/enviarNotificacion");
			Notificacion noti = notificacionService.create();
			noti.setProfesor(profesorService.findPrincipal());
			result.addObject("notificacion", noti);
			List<Alumno> alumnos = profesorService
					.getTodosLosAlumnosProfesorConTutor();
			result.addObject("alumnos", alumnos);
			Collection<Curso> cursos = profesorService
					.getCursosImparteDocencia();
			result.addObject("cursos", cursos);
			return result;
		} catch (IllegalArgumentException e) {
			return new ModelAndView("security/login");
		}

	}

	@RequestMapping(value = "enviarNotificacionFiltrado", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView enviarNotificacion(@RequestParam int cursoId) {
		try {
			Assert.isTrue(cursoId > 0);
			Curso c = cursoService.findOne(cursoId);
			Assert.notNull(c);
			ModelAndView result = new ModelAndView("alumno/enviarNotificacion");
			Notificacion noti = notificacionService.create();
			noti.setProfesor(profesorService.findPrincipal());
			result.addObject("notificacion", noti);
			List<Alumno> alumnos = profesorService
					.getTodosLosAlumnosProfesorConTutorEnCurso(c);
			result.addObject("alumnos", alumnos);
			Collection<Curso> cursos = profesorService
					.getCursosImparteDocencia();
			result.addObject("cursos", cursos);
			return result;
		} catch (IllegalArgumentException e) {
			return new ModelAndView("security/login");
		}

	}

	@RequestMapping(value = "notificacion", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param alumnoId
	 * @return
	 */
	public ModelAndView mostrarTodosElementosEnviarNotificacion(
			@RequestParam String alumnoId) {
		int id = Integer.valueOf(alumnoId);
		ModelAndView result = enviarNotificacion();
		List<PadreMadreOTutor> tutores = alumnoService.getTutores(id);
		result.addObject("tutores", tutores);
		return result;
	}

	@RequestMapping(value = "enviarNotificacion", method = RequestMethod.POST)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param noti
	 * @param destinatario
	 * @param tipoNotificacion
	 * @param bind
	 * @return
	 */
	public ModelAndView enviarNotificacionFinProceso(Notificacion noti,
			@RequestParam("destinatario") String destinatario,
			@RequestParam("tipoNotificacion") String tipoNotificacion,
			BindingResult bind) {
		List<PadreMadreOTutor> destinatarios = new ArrayList<PadreMadreOTutor>();
		String[] idstutores = destinatario.split(",");
		for (String id : idstutores) {
			int idTutor = Integer.valueOf(id);
			destinatarios.add(padreMadreOTutorService.findOne(idTutor));
		}
		Assert.notEmpty(destinatarios);
		ModelAndView result = enviarNotificacion();
		if (bind.hasErrors()) {
			result.addObject("notificacion", noti);
			return result;
		} else {
			try {
				beginTransaction();
				noti.setContenido(tipoNotificacion + "\r" + noti.getContenido());
				noti.setPadreMadreOTutor(destinatarios.get(0));
				noti.setEmisor("PROFESOR");
				notificacionService.save(noti);
				if (destinatarios.size() > 1) {
					for (int i = 1; i < destinatarios.size(); i++) {
						noti.setPadreMadreOTutor(destinatarios.get(i));
						Notificacion nueva = noti.clone();
						notificacionService.save(nueva);
					}
				}

				commitTransaction();
				result.addObject("message", "pasarLista.ok");
			} catch (Exception e) {
				try {
					rollbackTransaction();
				} catch (NullPointerException oops) {
					LOGGER.error(oops);
					List<Alumno> tutorandos = Lists.newArrayList(destinatarios
							.get(0).getTutorandos());
					result = mostrarTodosElementosEnviarNotificacion(tutorandos
							.get(0).getId() + "");
					result.addObject("message", "pasarLists.error");
				}
				LOGGER.error(e);
			}
		}
		return result;
	}

	@RequestMapping(value = "establecerNota", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView seleccionarAlumnoParaNotaTrabajoDiario() {
		ModelAndView result;
		List<Alumno> alumnos = Lists.newArrayList(profesorService
				.getTodosLosAlumnosProfesor());
		result = new ModelAndView("profesor/realizarEvaluacion");
		result.addObject("requestURI", "app/profesor/alumno/establecerNota.do");
		result.addObject("url",
				"app/profesor/alumno/establecerNotaTrabajoDiario.do?alumnoId");
		result.addObject("alumnos", alumnos);
		return result;
	}

	@RequestMapping(value = "establecerNotaTrabajoDiario", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param alumnoId
	 * @return
	 */
	public ModelAndView mostrarTablaTrabajoDiario(@RequestParam String alumnoId) {
		ModelAndView result = new ModelAndView("alumno/establecerNotaDiaria");
		List<Date> festivos = FechaUtils.getDiasFestivos();
		Alumno a = alumnoService.findOne(Integer.valueOf(alumnoId));
		result.addObject("festivos", festivos);
		result.addObject("alumnoId", alumnoId);
		result.addObject("alumno", a);
		return result;
	}

	@RequestMapping(value = "/verFicha", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView getAlumnos(@RequestParam int alumnoId) {
		Alumno a = alumnoService.findOne(alumnoId);
		Assert.notNull(a);
		Assert.isTrue(profesorService.getTodosLosAlumnosProfesor().contains(a));
		ModelAndView mv = new ModelAndView("profesor/verFicha", "alumno", a);
		mv.addObject("items", alumnoService.obtenerItemsAlumno(a));
		mv.addObject("faltas",
				profesorService.findAllFaltaSinJustificarMiAsignaturas(a));
		mv.addObject("tutores", a.getPadresMadresOTutores());
		mv.addObject("alumnoId", alumnoId);
		return mv;
	}

	@RequestMapping(value = "verAlumnos", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView seleccionarAlumnoParaVerFicha() {
		ModelAndView result;
		List<Alumno> alumnos = Lists.newArrayList(profesorService
				.getTodosLosAlumnosProfesor());
		result = new ModelAndView("profesor/realizarEvaluacion");
		result.addObject("requestURI", "app/profesor/alumno/verAlumnos.do");
		result.addObject("url", "app/profesor/alumno/verFicha.do?alumnoId");
		result.addObject("alumnos", alumnos);
		return result;
	}

	@RequestMapping(value = "verAlumnosParaCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView seleccionarAlumnoParaConcertarCita() {
		ModelAndView result;
		List<Alumno> alumnos = Lists.newArrayList(profesorService
				.getTodosLosAlumnosProfesorConTutor());
		result = new ModelAndView("profesor/realizarEvaluacion");
		result.addObject("requestURI",
				"app/profesor/alumno/verAlumnosParaCita.do");
		result.addObject("url", "app/profesor/alumno/concertarCita.do?alumnoId");
		result.addObject("alumnos", alumnos);
		return result;
	}

	@RequestMapping(value = "concertarCita", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView concertarCita(@RequestParam int alumnoId) {
		ModelAndView result;
		Alumno alum = alumnoService.findOne(alumnoId);
		Assert.notNull(alum);
		result = new ModelAndView("profesor/concertarCita");
		if (alum.getPadresMadresOTutores().size() > 0) {
			Cita cita = citaService.create();
			cita.setEmisor("PROFESOR");
			cita.setProfesor(profesorService.findPrincipal());
			result.addObject("cita", cita);
			result.addObject("tutores",
					Lists.newArrayList(alum.getPadresMadresOTutores()));
			result.addObject("tieneTutores", true);
			result.addObject("alumnoId", alumnoId);
		} else {
			result.addObject("tieneTutores", false);
		}

		return result;
	}

	@RequestMapping(value = "/guardarCita", method = RequestMethod.POST, params = "save")
	public ModelAndView guardarCita(@RequestParam("alumnoId") int alumnoid,
			@Valid Cita cita, BindingResult bind) {
		ModelAndView result = null;
		if (bind.hasErrors()) {
			result = new ModelAndView("profesor/concertarCita");
			result.addObject("cita", cita);
			Alumno alum = alumnoService.findOne(alumnoid);
			result.addObject("tutores",
					Lists.newArrayList(alum.getPadresMadresOTutores()));
			result.addObject("tieneTutores", true);
			result.addObject("alumnoId", alumnoid);
			result.addObject("message", "pasarLista.error");
		} else {
			result = concertarCita(alumnoid);
			result.addObject("cita", cita);
			try {
				beginTransaction();
				citaService.save(cita, profesorService.findPrincipal());
				commitTransaction();
				result.addObject("message", "pasarLista.ok");
			} catch (Exception ex) {
				try {
					rollbackTransaction();
				} catch (NullPointerException oops) {
					LOGGER.error(oops);
				}
				LOGGER.error(ex);
				result.addObject("message", "pasarLista.error");

			}

		}

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

	@RequestMapping(value = "verAlumnosParaEstadisticas", method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView seleccionarAlumnoParaEstadisticas() {
		ModelAndView result;
		List<Alumno> alumnos = Lists.newArrayList(profesorService
				.getTodosLosAlumnosProfesorConTutor());
		result = new ModelAndView("profesor/realizarEvaluacion");
		result.addObject("requestURI",
				"app/profesor/alumno/verAlumnosParaEstadisticas.do");
		result.addObject("url", "app/profesor/alumno/estadisticas.do?alumnoId");
		result.addObject("alumnos", alumnos);
		return result;
	}

}
