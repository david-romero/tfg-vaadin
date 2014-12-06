/** WelcomeController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package com.app.web.controllers.welcome;

/**
 * 
 */
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;

@Controller
@RequestMapping("/welcome")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	/**
	 * 
	 * Constructor
	 */
	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		result = new ModelAndView("welcome/index");
		return result;
	}
}