/**
 * AuthorityToStringConverter.java
 * appEducacional
 * 20/01/2014 18:02:16
 * Copyright David Romero Alcaide
 * com.app.infrastructureLayer.converters
 */
package com.app.infrastructure.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.infrastructure.security.Authority;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class AuthorityToStringConverter implements Converter<Authority, String> {

	
	/**
	 * convert
	 */
	public String convert(Authority authority) {
		String result;

		if (authority == null){
			result = null;
		}else{
			result = authority.getAuthority();
		}
		return result;
	}

}
