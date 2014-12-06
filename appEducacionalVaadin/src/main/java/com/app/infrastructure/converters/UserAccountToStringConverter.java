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

import com.app.infrastructure.security.UserAccount;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class UserAccountToStringConverter implements
		Converter<UserAccount, String> {

	
	/**
	 * convert
	 */
	public String convert(UserAccount alum) {
		String result;

		if (alum == null) {
			result = null;
		} else {
			result = String.valueOf(alum.getId());
		}
		return result;
	}

}
