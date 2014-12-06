/**
 * ProfesorRepository.java
 * appEducacional
 * 14/01/2014 10:26:41
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

import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.Curso;
import com.app.domain.model.types.Profesor;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

	@Query("select asign.curso from Profesor p join p.asignaturas asign  where p.id= ?1")
	/**
	 * Devuelve los cursos en los cuales imparte clase
	 * @author David Romero Alcaide
	 * @param id
	 * @return
	 */
	public Collection<Curso> getCursosDondeImparteClase(Integer id);

	@Query("select a from Curso c join c.asignaturas a where a.profesor.id=?2 and c.id=?1")
	public Asignatura getAsignaturaCursoProfesor(int cursoId, int profesorId);

	@Query("select p from Profesor p where p.userAccount.id = ?1")
	Profesor findByUserAccountId(int userAccountId);

	@Query("select p from Profesor p where p.DNI = ?1")
	Profesor findByDNI(String dni);

}
