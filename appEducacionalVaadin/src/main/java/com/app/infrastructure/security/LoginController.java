/* LoginController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla 
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.app.infrastructure.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;

@Controller
@Transactional
@RequestMapping("/security")
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	/**
	 * 
	 */
	LoginService service;

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 * Constructor
	 */
	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param credentials
	 * @param bindingResult
	 * @param showError
	 * @return
	 */
	public ModelAndView login(@Valid @ModelAttribute Credentials credentials,
			BindingResult bindingResult,
			@RequestParam(required = false) boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		return result;
	}

}