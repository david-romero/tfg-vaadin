/**
 * AsignaturaRepository.java
 * appEducacional
 * 14/01/2014 10:43:26
 * Copyright David Romero Alcaide
 * com.app.domainLayer.repositories
 */
package com.app.domain.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.domain.model.types.Asignatura;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface AsignaturaRepository extends
		JpaRepository<Asignatura, Integer> {

	@Query("select a from Asignatura a where a.profesor.id=?1")
	Collection<Asignatura> findAsignaturasDeProfesor(int profesorId);

	@Query("select a from Asignatura a where a.nombre=?1")
	Collection<Asignatura> findAsignaturasPorNombre(String nombre);

}
