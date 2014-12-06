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

import com.app.domain.model.types.Asignatura;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class AsignaturaToStringConverter implements
		Converter<Asignatura, String> {

	
	/**
	 * convert
	 */
	public String convert(Asignatura asign) {
		String result;

		if (asign == null){
			result = null;
		}else		{
			result = String.valueOf(asign.getId());
		}
		return result;
	}

}
