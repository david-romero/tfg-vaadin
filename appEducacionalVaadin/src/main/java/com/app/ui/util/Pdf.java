/**
* Pdf.java
* appEducacionalVaadin
* 15/1/2015 22:08:56
* Copyright David
* com.app.ui.util
*/
package com.app.ui.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;

/**
 * @author David
 *
 */
public class Pdf implements StreamSource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5293068752868238964L;
	private final ByteArrayOutputStream os = new ByteArrayOutputStream();

    public Pdf() {
        Document document = null;

        try {
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, os);
            document.open();

            document.add(new Paragraph("This is some content for the sample PDF!"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    @Override
    public InputStream getStream() {
        // Here we return the pdf contents as a byte-array
        return new ByteArrayInputStream(os.toByteArray());
    }
	
}
