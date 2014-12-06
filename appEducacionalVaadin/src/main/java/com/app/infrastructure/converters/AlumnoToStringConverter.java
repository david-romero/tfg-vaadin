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

import com.app.domain.model.types.Alumno;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class AlumnoToStringConverter implements Converter<Alumno, String> {

	
	/**
	 * convert
	 */
	public String convert(Alumno alum) {
		String result;

		if (alum == null){
			result = null;
		}else{
			result = String.valueOf(alum.getId());
		}

		return result;
	}

}
