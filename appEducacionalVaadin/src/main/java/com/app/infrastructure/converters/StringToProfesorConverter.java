/**
 * StringToAsignaturaConverter.java
 * appEducacional
 * 15/02/2014 17:43:03
 * Copyright David Romero Alcaide
 * com.app.infrastructureLayer.converters
 */
package com.app.infrastructure.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.model.types.Profesor;
import com.app.domain.repositories.ProfesorRepository;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class StringToProfesorConverter implements Converter<String, Profesor> {

	@Autowired
	ProfesorRepository profesorRepository;

	
	public Profesor convert(String text) {
		Profesor result;
		try {
			result = profesorRepository.findOne(Integer.parseInt(text));
		} catch (NumberFormatException oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
