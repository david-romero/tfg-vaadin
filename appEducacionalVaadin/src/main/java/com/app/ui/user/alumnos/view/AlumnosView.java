/**
* AlumnosView.java
* appEducacionalVaadin
* 21/3/2015 11:23:47
* Copyright David
* com.app.ui.user.alumnos.view
*/
package com.app.ui.user.alumnos.view;

import java.io.ByteArrayInputStream;
import com.app.ui.user.alumno.view.*;
import java.io.InputStream;

import org.tepi.filtertable.FilterTable;

import com.app.domain.model.types.Alumno;
import com.app.ui.components.alumnos.AlumnosFilterGenerator;
import com.app.ui.components.alumnos.AlumnosTable;
import com.app.ui.user.alumnos.presenter.AlumnosPresenter;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.CustomTable.ColumnGenerator;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class AlumnosView extends VerticalLayout implements View{

	private FilterTable tabla;
	
	private AlumnosPresenter presenter;
	
	public AlumnosView(){
		presenter = AlumnosPresenter.getInstance();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5541326861259632180L;

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		setCaption("Mis Alumnos");
		tabla = new FilterTable();
		tabla.setContainerDataSource(presenter.getAlumnosContainerEnCurso());
		tabla.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tabla.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tabla.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tabla.setSortEnabled(true);
		AlumnosFilterGenerator filterGenerator = new AlumnosFilterGenerator();
		tabla.setFilterDecorator(filterGenerator);
		tabla.setFilterGenerator(filterGenerator);
		tabla.setFilterBarVisible(true);
		tabla.setFilterFieldVisible("imagen", false);
		tabla.setSelectable(true);
		tabla.setImmediate(true);
		tabla.setRowHeaderMode(RowHeaderMode.INDEX);
		tabla.setSizeFull();
		tabla.setVisibleColumns("imagen","nombre", "apellidos","fechaNacimiento","curso");
		tabla.setColumnHeaders("Alumno","Nombre", "Apellido","Fecha de Nacimiento","Curso");

		tabla.setSortContainerPropertyId("nombre");
        
		tabla.addGeneratedColumn("imagen", getImageColumnGenerator() );
		tabla.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Alumno a = (Alumno) event.getProperty().getValue();
				AlumnoView perfil = new AlumnoView(a);
				removeAllComponents();
				addComponent(perfil);
			}
		});
		addComponent(tabla);
	}
	
	public ColumnGenerator getImageColumnGenerator(){
		 ColumnGenerator imageColumnGenerator = new ColumnGenerator()
		  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 8387232622265687219L;

			public Object generateCell(final CustomTable source, final Object itemId,
		        final Object columnId)
		    {
			  @SuppressWarnings("unchecked")
			  JPAContainerItem<Alumno> alumnoBean = (JPAContainerItem<Alumno>) source.getItem(itemId);
			  Alumno alumno = alumnoBean.getEntity();
			  VerticalLayout pic = new VerticalLayout();
				pic.setSizeUndefined();
				pic.setSpacing(true);
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
				Image profilePic = new Image("", resource);
				profilePic.setWidth(40.0f, Unit.PIXELS);
				profilePic.markAsDirty();
				pic.addComponent(profilePic);
		      return pic;
		    }
		  };
		  return imageColumnGenerator;
		}

}
