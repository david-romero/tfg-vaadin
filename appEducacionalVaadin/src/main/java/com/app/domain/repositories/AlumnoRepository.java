/**
 * AlumnoRepository.java
 * appEducacional
 * 14/01/2014 10:33:04
 * Copyright David Romero Alcaide
 * com.app.domainLayer.repositories
 */
package com.app.domain.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.Profesor;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

	@Query("select a.profesor from Asignatura a join a.curso c join"
			+ " c.alumnos alum where alum.id=?1")
	public Collection<Profesor> getProfesores(Integer id);

	@Query("select c.asignaturas from Curso c join c.alumnos alum "
			+ "where alum.id=?1")
	public Collection<Asignatura> getAsignaturas(Integer id);

	@Query("select item from Alumno a join a.diasDelCalendario dias "
			+ "join dias.itemsEvaluable item where a.id=?1")
	public Collection<ItemEvaluable> getAllItemsEvaluables(Integer id);

	@Query("select item from Alumno a join a.diasDelCalendario dias "
			+ "join dias.itemsEvaluable item where a.id=?1 and item.asignatura.id=?2")
	public Collection<ItemEvaluable> getAllItemsEvaluablesByAsignatura(
			Integer id, Integer asignaturaId);

	@Query("select item from Alumno a join a.diasDelCalendario dias "
			+ "join dias.itemsEvaluable item where a.id=?1 and "
			+ "item.evaluacion.indicador=?2")
	public Collection<ItemEvaluable> getAllItemsEvaluablesPorEvaluacion(
			Integer id, Integer evaluacion);

	@Query("select a from Alumno a where a.curso.id = ?1 and a.fechaNacimiento = ?2")
	/**
	 * @author David Romero Alcaide
	 * @param id
	 * @param fechaNacimiento
	 * @return
	 */
	public Collection<Alumno> findByCursoYFechaNacimiento(int id,
			Date fechaNacimiento);

}
