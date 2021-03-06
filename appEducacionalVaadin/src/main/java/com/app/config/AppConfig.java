/**
 * AppConfig.java
 * appEducacionalVaadin
 * 29/11/2014 14:03:38
 * Copyright David
 * com.app.config
 */
package com.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.app.infrastructure.security.AuthManager;

@Configuration
@ComponentScan(basePackages = { "com.app.ui", "com.app.infrastructure.security",
		"com.app.applicationservices.services","com.app.domain.repositories",
		"com.app.domain.model.types","com.app.presenter.data","com.app.presenter.profesor","com.app.presenter.event"})
@ImportResource({"/WEB-INF/mvc-dispatcher-servlet.xml"})
/**
 * 
 * @author David
 *
 */
public class AppConfig {

	@Bean
	public com.app.infrastructure.security.AuthManager authManager() {
		com.app.infrastructure.security.AuthManager res = new AuthManager();
		return res;
	}

	
	
}
