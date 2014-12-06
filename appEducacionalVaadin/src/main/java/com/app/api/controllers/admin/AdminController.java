/**
 * AdminController.java
 * appEducacional
 * 21/07/2014 17:12:26
 * Copyright David Romero Alcaide
 * com.app.api.controllers.admin
 */
package com.app.api.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AdministradorService;
import com.app.domain.model.types.Administrador;

/**
 * @author David Romero Alcaide
 * 
 */
@Controller
@Transactional
@RequestMapping("/admin")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AdminController extends AbstractController {

	@Autowired
	private AdministradorService adminService;

	@RequestMapping(value = "/numeroPersonasConfirmar", method = RequestMethod.GET,produces="application/json")
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	int getNumeroPersonasConfirmar() {
		Administrador p = adminService.findPrincipal();
		Assert.notNull(p);
		int numeroNotificacionesRecibidas = adminService
				.findPersonasSinConfirmar(p);
		return numeroNotificacionesRecibidas;
	}

}
