/**
 * DiaDeCalendario.java
 * appEducacional
 * 14/01/2014 10:33:54
 * Copyright David Romero Alcaide
 * com.app.domainLayer.repositories
 */
package com.app.domain.repositories;

/**
 * imports
 */
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.domain.model.types.DiaDeCalendario;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface DiaDeCalendarioRepository extends
		JpaRepository<DiaDeCalendario, Integer> {

	@Query("select dia from DiaDeCalendario dia where dia.dia=?1 and dia.mes=?2 "
			+ "and dia.alumno.id = ?3")
	Collection<DiaDeCalendario> findPorDiaYMes(int dia, int mes, int alumnoId);

}
