/**
 * AsignaturaToStringConverter.java
 * appEducacional
 * 15/02/2014 17:40:12
 * Copyright David Romero Alcaide
 * com.app.infrastructureLayer.converters
 */
package com.app.infrastructure.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.model.types.PadreMadreOTutor;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class PadreMadreOTutorToStringConverter implements
		Converter<PadreMadreOTutor, String> {

	
	/**
	 * convert
	 */
	public String convert(PadreMadreOTutor padre) {
		String result;

		if (padre == null){
			result = null;
		}else{
			result = String.valueOf(padre.getId());
		}
		return result;
	}

}
