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

import com.app.domain.model.types.Evaluacion;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class EvaluacionToStringConverter implements
		Converter<Evaluacion, String> {

	
	/**
	 * convert
	 */
	public String convert(Evaluacion ev) {
		String result;

		if (ev == null){
			result = null;
		}else{
			result = String.valueOf(ev.getId());
		}
		return result;
	}

}
