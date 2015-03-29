/**
* AlumnoView.java
* appEducacionalVaadin
* 21/3/2015 19:59:46
* Copyright David
* com.app.ui.user.alumno.view
*/
package com.app.ui.user.alumno.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.app.domain.model.types.Alumno;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Image;
import com.app.ui.user.alumnos.presenter.*;

/**
 * @author David
 *
 */
public class AlumnoView extends AlumnoPerfilView implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3783320242503387597L;
	
	protected Alumno alumno;
	
	protected AlumnosPresenter presenter;
	
	public AlumnoView(){
		presenter = AlumnosPresenter.getInstance();
	}
	

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// split at "/", add each part as a label
        String[] msgs = event.getParameters().split("/");
        if ( msgs == null || msgs.length != 1 ){
        	throw new IllegalArgumentException();
        }else{
        	Integer idAlumno = new Integer(msgs[0]);
        	this.alumno = presenter.getAlumno(idAlumno);
        }
		generateImage();
		generateTableFaltas();
		
	}

	/**
	 * @author David
	 */
	private void generateTableFaltas() {
		table.setContainerDataSource(presenter.getFaltasAsistenciaAlumno(alumno));
		/*table.setVisibleColumns("asignatura","fecha");
		table.setColumnHeaders("Asignatura","Fecha");*/
		table.refreshRowCache();
	}

	/**
	 * @author David
	 */
	private void generateImage() {
		Resource resource;
		if (alumno.getImagen() != null
				&& alumno.getImagen().length > 0) {
			StreamSource source2 = new StreamResource.StreamSource() {

				private static final long serialVersionUID = -3823582436185258502L;

				public InputStream getStream() {
					InputStream reportStream = null;
					reportStream = new ByteArrayInputStream(
							alumno.getImagen());
					return reportStream;
				}
			};
			resource = new StreamResource(source2, "profile-picture.png");
		} else {
			resource = new ThemeResource(
					"img/profile-pic-300px.jpg");
		}
		image = new Image("", resource);
		image.setWidth(40.0f, Unit.PIXELS);
		image.markAsDirty();
	}

}
