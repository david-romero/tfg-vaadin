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

import com.app.domain.model.types.Profesor;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorToStringConverter implements Converter<Profesor, String> {

	
	/**
	 * convert
	 */
	public String convert(Profesor profe) {
		String result;

		if (profe == null){
			result = null;
		}else{
			result = String.valueOf(profe.getId());
		}

		return result;
	}

}
