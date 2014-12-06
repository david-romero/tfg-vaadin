/**
 * StringToAuthorityConverter.java
 * appEducacional
 * 15/02/2014 17:38:24
 * Copyright David Romero Alcaide
 * com.app.infrastructureLayer.converters
 */
package com.app.infrastructure.converters;

import com.app.infrastructure.security.Authority;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class StringToAuthorityConverter implements Converter<String, Authority> {

	
	public Authority convert(String text) {
		Authority result;
		try {
			result = new Authority();
			result.setAuthority(text);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
