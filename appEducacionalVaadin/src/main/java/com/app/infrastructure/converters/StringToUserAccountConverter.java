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

import com.app.infrastructure.security.UserAccount;
import com.app.infrastructure.security.UserAccountRepository;

@Component
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class StringToUserAccountConverter implements
		Converter<String, UserAccount> {

	@Autowired
	/**
	 * 
	 */
	UserAccountRepository userAccounRepository;

	/**
	 * 
	 */
	public UserAccount convert(String text) {
		UserAccount result;

		try {
			result = userAccounRepository.findOne(Integer.parseInt(text));
		} catch (NumberFormatException oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
