/**
 * ProfesorAlumnoController.java
 * appEducacional
 * 26/01/2014 12:57:53
 * Copyright David Romero Alcaide
 * com.app.web.controllers.profesor
 */
package com.app.api.controllers.profesor;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.app.api.controllers.AbstractController;
import com.app.applicationservices.services.AlumnoService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Asignatura;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.app.domain.model.types.Profesor;
import com.app.utility.FileUtils;
import com.app.utility.PDFGenerator;
import com.google.common.collect.Lists;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Transactional
@RequestMapping("/profesor/alumno")
@Scope(WebApplicationContext.SCOPE_REQUEST)
/**
 * @author David Romero Alcaide
 *
 */
public class ProfesorAlumnoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	/**
	 * Lógica de negocio de profesor
	 */
	private ProfesorService profesorService;


	@Autowired
	private AlumnoService alumnoService;

	// Application context
	@Autowired
	/**
	 * Application context
	 */
	private ApplicationContext appContext;

	/**
	 * Log
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ProfesorAlumnoController.class);

	/**
	 * Constructor
	 */
	public ProfesorAlumnoController() {
		super();
	}

	@RequestMapping(value = "/alumnosProfesorJSON", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	/**
	 * Obtiene una lista en json de los alumnos pertenecientes a un profesor
	 * @author David Romero Alcaide
	 * @return
	 */
	public @ResponseBody
	List<Alumno> getAlumnosDelProfesor() {
		return Lists.newArrayList(profesorService.getTodosLosAlumnosProfesor());
	}

	@RequestMapping(value = "guardarNotaDiaria", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param tipoNota
	 * @param alum
	 * @return
	 */
	public void guardarNotaTrabajoDiaria(
			@RequestParam("tipoNota") String tipoNota,
			@RequestParam("alum") String alum,
			@RequestParam("fecha") String fecha,
			@RequestParam("calificacion") int calificacion) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaParseada = null;
		try {
			fechaParseada = df.parse(fecha);
		} catch (ParseException e1) {
			LOGGER.error(e1.getMessage(),e1);
			throw new IllegalArgumentException(e1.getMessage(),e1);
		}

		Alumno alumn = alumnoService.findOne(Integer.valueOf(alum));
		Asignatura asig = profesorService.getAsignaturaCursoProfesor(alumn
				.getCurso());

		try {
			beginTransaction();
			alumnoService.anadirDiaDiaDeCalendario(alumn, fechaParseada);
			commitTransaction();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			rollbackTransaction();
			throw new IllegalArgumentException(e);
		}

		try {
			beginTransaction();
			alumnoService.establecerItemEvaluacion(fechaParseada, alumn, asig,
					tipoNota, calificacion);
			commitTransaction();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			rollbackTransaction();
			throw new IllegalArgumentException(e);
		}

	}

	@RequestMapping("/foto")
	@ResponseBody
	public byte[] foto(HttpServletResponse response, @RequestParam int alumnoId)
			throws IOException {
		response.setContentType("image/jpeg");
		Alumno alumno = alumnoService.findOne(alumnoId);
		byte[] foto;
		if (alumno.getImagen().length > 0) {
			foto = alumno.getImagen();
		} else {
			InputStream in = this.getClass().getResourceAsStream("/alumno.png");

			foto = IOUtils.toByteArray(in);
		}
		return foto;
	}

	@RequestMapping(value = "actualizarItem", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param tipoNota
	 * @param alum
	 * @return
	 */
	public void guardarNota(@RequestParam("tipoNota") String tipoNota,
			@RequestParam("item") int item,
			@RequestParam("calificacion") double calificacion) {
		try {
			beginTransaction();
			com.app.applicationservices.services.Service servicio = (com.app.applicationservices.services.Service) appContext
					.getBean(tipoNota + "Service");
			ItemEvaluable itemEvaluable = servicio.findOne(item);
			servicio.save(itemEvaluable);
			commitTransaction();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
			rollbackTransaction();
			throw new IllegalArgumentException(e);
		}

	}

	@RequestMapping(value = "importarAlumnos", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param tipoNota
	 * @param alum
	 * @return
	 */
	public void importarAlumnos(@RequestParam("file") MultipartFile file) {
		try {
			List<String[]> alumnosImportados = FileUtils.convertCSVtoList(file);
			beginTransaction();
			alumnoService.guardarAlumnosImportados(alumnosImportados);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			LOGGER.error(e.getMessage(), e.getCause());
			throw new IllegalArgumentException(e);
		}
	}
	
	@RequestMapping(value = "/generatePDF/{alumnoId}",method = RequestMethod.GET)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param alumnoId
	 * @param response
	 * @param request
	 * @param model
	 * @throws IOException
	 */
    public void downloadPDF(@PathVariable Integer alumnoId,
                                    HttpServletResponse response,
                                    HttpServletRequest request, ModelMap model) 
                                    throws IOException {
		
		Profesor profe = profesorService.findPrincipal();
        Alumno alum = alumnoService.findOne(alumnoId);
        Assert.notNull(alum);
        Assert.notNull(profe);
        Assert.isTrue(profe.isIdentidadConfirmada());
        Collection<ItemEvaluable> itemList =  
        		profesorService.findAllItemsAlumnoProfesor(alum,profe);
        
        String url = request.getScheme() + "://" + request.getServerName()+ ":"
         + request.getServerPort() + request.getContextPath(); 
        
        Asignatura asign = profesorService.getAsignaturaCursoProfesor(alum.getCurso());
        
        List<NotaPorEvaluacion> notas = alumnoService.obtenerNotasPorEvaluacion(alum, asign);
        
       /*
        * here TaskEntry is your Domain of respective class
        */
       /*
        * here taskEntryService is your service layer from which you are accessing Data.
        */
       String orignalFileName=alum.getNombre() + alum.getApellidos() + ".pdf";

        try {
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            response.setHeader("Content-Disposition", "outline;filename=\"" +orignalFileName+ "\"");
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            document.add(PDFGenerator.createProfileTable(alum,url));
            Paragraph para = new Paragraph();
            para.add(new com.itextpdf.text.Chunk("\n"));
            // add a couple of blank lines
            document.add( para  );
            document.add( para  );
            document.add(PDFGenerator.createItemsTable(itemList));
         // add a couple of blank lines
            document.add( para  );
            document.add( para  );
            document.add(PDFGenerator.createNotasTable(notas));
            document.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
	

}
