/**
 * ProfesorService.java
 * appEducacional
 * 16/01/2014 11:36:24
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.domainservices.Valida;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.DiaDeCalendario;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.domain.model.types.itemsevaluables.Actividad;
import com.app.domain.model.types.itemsevaluables.EjerciciosEntregados;
import com.app.domain.model.types.itemsevaluables.Examen;
import com.app.domain.model.types.itemsevaluables.FaltaDeAsistencia;
import com.app.domain.model.types.itemsevaluables.Trabajo;
import com.app.domain.repositories.ProfesorRepository;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.LoginService;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.data.PersonaProvider;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;

@Service
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorService implements Serializable{

	// Repositorios gestionados

	/**
	 * 
	 */
	private static final long serialVersionUID = -928342560252536918L;

	@Autowired
	/**
	 * 
	 */
	private ProfesorRepository profesorRepositorio;

	// Servicios gestionados

	@Autowired
	private ExamenService examenService;

	@Autowired
	private TrabajoService trabajoService;

	@Autowired
	private ActividadService actividadService;

	@Autowired
	private EjerciciosEntregadosService ejerciciosEntregadosService;

	/**
	 * Constructor
	 */
	public ProfesorService() {
		super();

	}

	// Métodos CRUD
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Profesor create() {
		Profesor p = new Profesor();
		p.setIdentidadConfirmada(false);
		UserAccount account = new UserAccount();
		List<Authority> authorities = Lists.newArrayList();
		Authority auth = new Authority();
		auth.setAuthority(Authority.PROFESOR);
		authorities.add(auth);
		account.setAuthorities(authorities);
		p.setUserAccount(account);

		return p;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Profesor> findAll() {
		List<Profesor> ite;
		ite = profesorRepositorio.findAll();

		return ite;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesorId
	 * @return
	 */
	public Profesor findOne(int profesorId) {
		Assert.isTrue(profesorId > 0);
		return profesorRepositorio.findOne(profesorId);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void save(Profesor profesor) {
		Assert.notNull(profesor);
		Assert.isTrue(Valida.validaDNI(profesor.getDNI()), "pasarLista.error");
		Assert.isTrue(profesor.getApellidos().length() >= 4, "pasarLista.error");
		Assert.isTrue(profesor.getNombre().length() > 2, "pasarLista.error");
		profesorRepositorio.save(profesor);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void delete(Profesor profesor) {
		Assert.notNull(profesor);
		Assert.isTrue(profesor.getId()> 0);
		profesorRepositorio.delete(profesor);
	}

	// Otros métodos de negocio

	/**
	 * Obtener los cursos en los que imparte docencia un profesor
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 * @return
	 */
	public Collection<Curso> getCursosImparteDocencia() {
		Profesor profesor = findPrincipal();
		Assert.notNull(profesor);
		Assert.isTrue(profesor.getId() > 0);
		Assert.isTrue(profesor.isIdentidadConfirmada());
		Collection<Curso> cursos;
		cursos = profesorRepositorio.getCursosDondeImparteClase(profesor
				.getId());
		return cursos;
	}

	/**
	 * Obtiene la asignatura vinculada a un curso y a un profesor
	 * 
	 * @author David Romero Alcaide
	 * @param c
	 * @param p
	 * @return
	 */
	public Asignatura getAsignaturaCursoProfesor(Curso c) {
		Assert.notNull(c);
		Profesor profesor = this.findPrincipal();
		Assert.notNull(profesor, "pasarLista.profesor");
		Assert.isTrue(profesor.isIdentidadConfirmada());
		return profesorRepositorio.getAsignaturaCursoProfesor(c.getId(),
				profesor.getId());
	}
	
	/**
	 * Obtiene la asignatura vinculada a un curso y a un profesor
	 * 
	 * @author David Romero Alcaide
	 * @param c
	 * @param p
	 * @return
	 */
	public Asignatura getAsignaturaCursoProfesor(Curso c,Profesor profesor) {
		Assert.notNull(c);
		Assert.notNull(profesor, "pasarLista.profesor");
		Assert.isTrue(profesor.isIdentidadConfirmada());
		return profesorRepositorio.getAsignaturaCursoProfesor(c.getId(),
				profesor.getId());
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Profesor findPrincipal() {
		Profesor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Profesor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Profesor result;

		result = profesorRepositorio.findByUserAccountId(userAccount.getId());

		return result;
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Alumno> getTodosLosAlumnosProfesor() {
		List<Alumno> lista = Lists.newArrayList();
		Profesor p = findPrincipal();
		for (Asignatura a : p.getAsignaturas()) {
			lista.addAll(a.getCurso().getAlumnos());
		}
		return lista;
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Alumno> getTodosLosAlumnosProfesor(Profesor profe) {
		List<Alumno> lista = Lists.newArrayList();
		for (Asignatura a : profe.getAsignaturas()) {
			lista.addAll(a.getCurso().getAlumnos());
		}
		return lista;
	}

	/**
	 * @author David Romero Alcaide
	 * @param item
	 */
	public void guardarCalificacion(int idItem, double calificacion,
			String className) {

		if (className.equals("Examen")) {
			Examen exam = examenService.findOne(idItem);
			exam.setCalificacion(calificacion);
			examenService.save(exam);
		} else if (className.equals("Trabajo")) {
			Trabajo trabajo = trabajoService.findOne(idItem);
			trabajo.setCalificacion(calificacion);
			trabajoService.save(trabajo);
		} else if (className.equals("Actividad")) {
			Actividad acti = actividadService.findOne(idItem);
			acti.setCalificacion(calificacion);
			actividadService.save(acti);
		} else if (className.equals("EjerciciosEntregados")) {
			EjerciciosEntregados ejer = (EjerciciosEntregados) ejerciciosEntregadosService
					.findOne(idItem);
			ejer.setCalificacion(calificacion);
			ejerciciosEntregadosService.save(ejer);
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public List<Alumno> getTodosLosAlumnosProfesorConTutor() {
		List<Alumno> alumnosTotales = (List<Alumno>) getTodosLosAlumnosProfesor();
		Iterable<Alumno> filtrado = Iterables.filter(alumnosTotales,
				new Predicate<Alumno>() {
					
					public boolean apply(Alumno p) {
						return p.getPadresMadresOTutores().size() > 0;
					}
				});
		return Lists.newArrayList(filtrado);
	}

	/**
	 * @author David Romero Alcaide
	 * @param c
	 * @return
	 */
	public List<Alumno> getTodosLosAlumnosProfesorConTutorEnCurso(final Curso c) {
		List<Alumno> alumnosConTutor = getTodosLosAlumnosProfesorConTutor();
		Iterable<Alumno> filtrado = Iterables.filter(alumnosConTutor,
				new Predicate<Alumno>() {
					
					public boolean apply(Alumno p) {
						return p.getCurso().equals(c);
					}
				});
		return Lists.newArrayList(filtrado);
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public List<Alumno> getTodosLosAlumnosProfesorConTutor(Profesor profe,
			final PadreMadreOTutor tutor) {
		List<Alumno> alumnosTotales = (List<Alumno>) getTodosLosAlumnosProfesor(profe);
		Iterable<Alumno> filtrado = Iterables.filter(alumnosTotales,
				new Predicate<Alumno>() {
					
					public boolean apply(Alumno p) {
						return p.getPadresMadresOTutores().contains(tutor);
					}
				});
		return Lists.newArrayList(filtrado);
	}

	/**
	 * @author David Romero Alcaide
	 * @param hash1
	 */
	public void updatePassword(String hash1) {
		Assert.notNull(hash1);
		Assert.isTrue(hash1.length() > 5);
		Profesor p = findPrincipal();
		Assert.notNull(p);
		p.getUserAccount().setPassword(hash1);
		save(p);
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<ItemEvaluable> findAllItems() {
		Profesor p = findPrincipal();
		Assert.notNull(p);
		Assert.isTrue(p.isIdentidadConfirmada());
		List<ItemEvaluable> items = Lists.newArrayList();
		for (Asignatura a : p.getAsignaturas()) {
			items.addAll(a.getItemsEvaluables());
		}
		return items;
	}
	
	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<ItemEvaluable> findAllItems(Profesor p) {
		Assert.notNull(p);
		Assert.isTrue(p.isIdentidadConfirmada());
		List<ItemEvaluable> items = Lists.newArrayList();
		for (Asignatura a : p.getAsignaturas()) {
			items.addAll(a.getItemsEvaluables());
		}
		return items;
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<FaltaDeAsistencia> findAllFaltaSinJustificar() {
		Collection<ItemEvaluable> items = findAllItems();
		List<FaltaDeAsistencia> faltasList = Lists.newArrayList();
		for (ItemEvaluable item : items) {
			if (item instanceof FaltaDeAsistencia) {
				FaltaDeAsistencia falta = (FaltaDeAsistencia) item;
				if (!falta.isJustificada()) {
					faltasList.add(falta);
				}
			}
		}
		return faltasList;
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<FaltaDeAsistencia> findAllFaltaSinJustificar(
			Asignatura asign) {
		Collection<ItemEvaluable> items = findAllItems();
		List<FaltaDeAsistencia> faltasList = Lists.newArrayList();
		for (ItemEvaluable item : items) {
			if (item instanceof FaltaDeAsistencia
					&& item.getAsignatura().equals(asign)) {
				FaltaDeAsistencia falta = (FaltaDeAsistencia) item;
				if (!falta.isJustificada()) {
					faltasList.add(falta);
				}
			}
		}
		return faltasList;
	}

	/**
	 * @author David Romero Alcaide
	 * @param a
	 * @return
	 */
	public Collection<FaltaDeAsistencia> findAllFaltaSinJustificarMiAsignaturas(
			final Alumno a) {
		Assert.notNull(a);
		Assert.notNull(findPrincipal());
		Assert.isTrue(findPrincipal().isIdentidadConfirmada());
		Asignatura asign = getAsignaturaCursoProfesor(a.getCurso());
		Collection<FaltaDeAsistencia> faltas = findAllFaltaSinJustificar(asign);
		Iterable<FaltaDeAsistencia> faltasFiltradas = Iterables.filter(faltas,
				new Predicate<FaltaDeAsistencia>() {

					
					public boolean apply(FaltaDeAsistencia input) {
						boolean bandera = false;
						if (!input.isJustificada()) {
							for (DiaDeCalendario d : input
									.getDiasDelCalendario()) {
								if (d.getAlumno().equals(a)) {
									bandera = true;
								}
							}
						}
						return bandera;
					}
				});
		return Lists.newArrayList(faltasFiltradas);
	}

	/**
	 * @author David Romero Alcaide
	 * @param dni
	 * @return
	 */
	public Profesor findByDNI(String dni) {
		Assert.isTrue(dni.length() == 9);
		Profesor p = profesorRepositorio.findByDNI(dni);
		Assert.notNull(p);
		return p;
	}

	/**
	 * @author David Romero Alcaide
	 * @param alum
	 * @param profe
	 * @return
	 */
	public Collection<ItemEvaluable> findAllItemsAlumnoProfesor(final Alumno alum,
			Profesor profe) {
		Assert.notNull(profe);
		Assert.isTrue(profe.isIdentidadConfirmada());
		Collection<ItemEvaluable> itemsProfesor = findAllItems(profe);
		Iterable<ItemEvaluable> itemsProfesorAlumno = Iterables.filter(itemsProfesor,
				new Predicate<ItemEvaluable>() {

					
					public boolean apply(ItemEvaluable input) {
						return checkItemEvaluablePerteneceAAlumno(input,alum);
					}

					
				});
		List<ItemEvaluable> itemsProfesorAlumnoList = Lists.newArrayList(itemsProfesorAlumno);
		return itemsProfesorAlumnoList;
	}

	private boolean checkItemEvaluablePerteneceAAlumno(
			ItemEvaluable item,Alumno alum) {
		boolean bandera = false;
		for ( DiaDeCalendario dia : item.getDiasDelCalendario() ){
			bandera = dia.getAlumno().equals(alum);
		}
		return bandera;
	}

	
	
	
}
