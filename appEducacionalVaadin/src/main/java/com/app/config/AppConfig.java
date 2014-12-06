/**
 * AppConfig.java
 * appEducacionalVaadin
 * 29/11/2014 14:03:38
 * Copyright David
 * com.app.config
 */
package com.app.config;


import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.LoginService;
import com.app.infrastructure.security.UserAccountService;
import com.app.ui.LoginFormListener;
import com.app.ui.UserView;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = { "com.app.ui", "com.app.infrastructure.security",
		"com.app.applicationservices.services" })
@ImportResource({"/WEB-INF/dispatcher2-servlet.xml","/WEB-INF/mvc-dispatcher-servlet.xml"})
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

	@Bean
	public UserAccountService userService() {
		UserAccountService res = new UserAccountService();
		return res;
	}

	@Bean
	public LoginFormListener loginFormListener() {
		return new LoginFormListener();
	}
}