/**
* PDFGenerator.java
* appEducacional
* 30/07/2014 17:08:59
* Copyright David Romero Alcaide
* com.app.utility
*/
package com.app.utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.ItemEvaluable;
import com.app.domain.model.types.NotaPorEvaluacion;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author David Romero Alcaide
 *
 */
public class PDFGenerator {

	/**
	 * @author David Romero Alcaide
	 * @param alum
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	public static PdfPTable createProfileTable(Alumno alum, String url) throws BadElementException, MalformedURLException, IOException {
		/*
         *  a table with three columns
         */
        PdfPTable table = new PdfPTable(3);
        
        PdfPCell cellImage = new PdfPCell();
        
        Image image2 = 
        		Image.getInstance(
        				new URL(url + "/rest/profesor/alumno/foto.do?alumnoId="+
        		alum.getId()));
        
        cellImage.setRowspan(3);
        cellImage.setImage(image2);
        
        table.addCell(cellImage);
        
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Nombre"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(alum.getNombre()));
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Apellidos"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(alum.getApellidos()));
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Curso"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(alum.getCurso().toString()));
        table.addCell(cell);
        return table;
	}

	/**
	 * @author David Romero Alcaide
	 * @param itemList
	 * @return
	 */
	public static PdfPTable createItemsTable(Collection<ItemEvaluable> itemList) {
		/*
         *  a table with three columns
         */
        PdfPTable table = new PdfPTable(3);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Tipo de Item"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Fecha"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Calificacion"));
        table.addCell(cell);

        
       for(ItemEvaluable item : itemList)  {
            cell = new PdfPCell(new Phrase(item.getClass().getSimpleName()));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(sdf.format(item.getFecha())));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(item.getCalificacion() + ""));
            table.addCell(cell);
        }
       return table;
	}

	/**
	 * @author David Romero Alcaide
	 * @param notas
	 * @return
	 */
	public static Element createNotasTable(List<NotaPorEvaluacion> notas) {
		PdfPTable table = new PdfPTable(3);
        
        PdfPCell cell;
		cell = new PdfPCell(new Phrase("Curso Academico"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Evaluacion"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Calificacion"));
        table.addCell(cell);
        
        for(NotaPorEvaluacion nota : notas)  {
        	
             cell = new PdfPCell(new Phrase(
            		 nota.getAlumno().getCurso().getCursoAcademico().getDenominacion()));
             table.addCell(cell);
             cell = new PdfPCell(new Phrase(nota.getEvaluacion().getIndicador() + " ª Evaluacion"));
             table.addCell(cell);
             cell = new PdfPCell(new Phrase(nota.getNotaFinal() + ""));
             table.addCell(cell);
         }
        
        return table;
	}

}
