/**
 * ProfesorAsignaturaControllerWeb.java
 * appEducacional
 * 15/05/2014 16:32:13
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.web.controllers.profesor;

/**
 * imports
 */
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.itemsevaluables.FaltaDeAsistencia;

@Controller
@Transactional
@RequestMapping("/profesor/item")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorItemControllerWeb extends AbstractController {

	// Services

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;



	// Application context
	@Autowired
	private ApplicationContext appContext;

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ProfesorItemControllerWeb.class);

	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ModelAndView guardarItem(@RequestParam("itemId") int itemId,
			@RequestParam("eventoId") int eventId,
			@RequestParam("calificacion") double calificacion,
			@RequestParam("tipo_nota") String tipoNota) {
		Assert.notNull(profesorService.findPrincipal());
		// Obtengo el tipo de evento
		com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
				.getBean(tipoNota + "Service");
		ItemEvaluable item = servicio.findOne(itemId);
		Assert.notNull(item);
		try {
			beginTransaction();
			profesorService
					.guardarCalificacion(itemId, calificacion, tipoNota);
			commitTransaction();
		} catch (Exception opps) {
			rollbackTransaction();
			LOGGER.error(opps.getMessage(), opps.getCause());
		}
		ModelAndView result = new ModelAndView(
				"redirect:../../../app/profesor/evento/evaluarItems.do?eventId="
						+ eventId);

		return result;
	}

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public ModelAndView showAllItems() {
		ModelAndView result = new ModelAndView("profesor/items");
		Collection<ItemEvaluable> items = profesorService.findAllItems();
		result.addObject("items", items);
		result.addObject("justificar", false);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView showAllItems(@RequestParam("item") int item,
			@RequestParam("tipo") String tipo) {
		Assert.notNull(profesorService.findPrincipal());
		// Obtengo el tipo de evento
		com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
				.getBean(tipo + "Service");
		ItemEvaluable itemEval = servicio.findOne(item);
		Assert.notNull(itemEval);
		ModelAndView result;
		try {
			beginTransaction();
			servicio.delete(itemEval);
			commitTransaction();
			result = new ModelAndView("redirect:showAll.do");
			result.addObject("message", "ok");
		} catch (Exception opps) {
			rollbackTransaction();
			LOGGER.error(opps.getMessage(), opps.getCause());
			result = showAllItems();
			result.addObject("message", "error");
		}
		return result;
	}

	@RequestMapping(value = "/justificarFalta", method = RequestMethod.GET)
	public ModelAndView showAllFaltas() {
		ModelAndView result = new ModelAndView("profesor/items");
		Collection<FaltaDeAsistencia> items = profesorService
				.findAllFaltaSinJustificar();
		result.addObject("items", items);
		result.addObject("justificar", true);
		return result;
	}

	@RequestMapping(value = "/justificar", method = RequestMethod.POST)
	public ModelAndView justificarFalta(@RequestParam("item") int item,
			@RequestParam("tipo") String tipo) {
		Assert.notNull(profesorService.findPrincipal());
		Assert.isTrue(profesorService.findPrincipal().isIdentidadConfirmada());
		// Obtengo el tipo de evento
		com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
				.getBean(tipo + "Service");
		ItemEvaluable itemEval = servicio.findOne(item);
		FaltaDeAsistencia falta = (FaltaDeAsistencia) itemEval;
		Assert.notNull(itemEval);
		ModelAndView result;
		try {
			beginTransaction();
			falta.setJustificada(true);
			servicio.save(falta);
			commitTransaction();
			result = new ModelAndView("redirect:justificarFalta.do");
			result.addObject("message", "ok");
		} catch (Exception opps) {
			rollbackTransaction();
			LOGGER.error(opps.getMessage(), opps.getCause());
			result = showAllItems();
			result.addObject("message", "error");
		}
		return result;
	}

}
