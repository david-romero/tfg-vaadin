/**
 * StringToAsignaturaConverter.java
 * appEducacional
 * 15/02/2014 17:43:03
 * Copyright David Romero Alcaide
 * com.app.infrastructureLayer.converters
 */
package com.app.infrastructure.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class StringToDateConverter implements Converter<String, Date> {

	
	public Date convert(String text) {
		Date result;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");

			result = dateFormat.parse(text);
		} catch (ParseException oops) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try {
				result = dateFormat.parse(text);
			} catch (ParseException e) {

				throw new IllegalArgumentException(oops);
			}

		}

		return result;
	}

}
