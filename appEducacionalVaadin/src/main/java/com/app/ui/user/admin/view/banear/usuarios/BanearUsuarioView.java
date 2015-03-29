/**
* BanearUsuarioView.java
* appEducacionalVaadin
* 21/3/2015 23:07:16
* Copyright David
* com.app.ui.user.admin.view.banear.usuarios
*/
package com.app.ui.user.admin.view.banear.usuarios;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.tepi.filtertable.FilterTable;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.Persona;
import com.app.ui.components.alumnos.AlumnosFilterGenerator;
import com.app.ui.user.admin.presenter.AdminPresenter;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


/**
 * @author David
 *
 */
public class BanearUsuarioView  extends BanearUsuarioDesign implements View{

	private AdminPresenter presenter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7560486752156416103L;
	
	public BanearUsuarioView(){
		presenter = AdminPresenter.getInstance();
	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		Table table = (Table) components.getLast();
		table.setContainerDataSource(presenter.getContainerPersonas());
		table.refreshRowCache();
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		table.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		table.setSortEnabled(true);
		table.setSelectable(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		table.setSizeFull();
		table.setVisibleColumns("imagen","nombre", "apellidos","DNI","telefono","email");
		table.setColumnHeaders("Persona","Nombre", "Apellido","D.N.I.","Teléfono","Email");

		table.setSortContainerPropertyId("nombre");
        
		table.addGeneratedColumn("imagen", getImageColumnGenerator() );
		
		table.addGeneratedColumn("buttons", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				JPAContainerItem<Persona> bean = (JPAContainerItem<Persona>) source.getItem(itemId);
				  Persona p = bean.getEntity();
				  Button b = new Button(FontAwesome.BAN);
				  b.addClickListener(presenter);
				  b.setId(p.getId()+"");
				  return b;
			}
		});
	}
	
	public ColumnGenerator getImageColumnGenerator(){
		 ColumnGenerator imageColumnGenerator = new ColumnGenerator()
		  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 8387232622265687219L;

			public Object generateCell(final Table source, final Object itemId,
		        final Object columnId)
		    {
			  @SuppressWarnings("unchecked")
			  JPAContainerItem<Persona> bean = (JPAContainerItem<Persona>) source.getItem(itemId);
			  Persona persona = bean.getEntity();
			  VerticalLayout pic = new VerticalLayout();
				pic.setSizeUndefined();
				pic.setSpacing(true);
				Resource resource;
				if (persona.getImagen() != null
						&& persona.getImagen().length > 0) {
					StreamSource source2 = new StreamResource.StreamSource() {

						private static final long serialVersionUID = -3823582436185258502L;

						public InputStream getStream() {
							InputStream reportStream = null;
							reportStream = new ByteArrayInputStream(
									persona.getImagen());
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
