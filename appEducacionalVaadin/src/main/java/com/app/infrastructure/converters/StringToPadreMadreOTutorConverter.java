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

import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.repositories.PadreMadreOTutorRepository;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class StringToPadreMadreOTutorConverter implements
		Converter<String, PadreMadreOTutor> {

	@Autowired
	PadreMadreOTutorRepository padreMadreOTutorRepository;

	
	public PadreMadreOTutor convert(String text) {
		PadreMadreOTutor result;

		try {
			result = padreMadreOTutorRepository.findOne(Integer.parseInt(text));
		} catch (NumberFormatException oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
