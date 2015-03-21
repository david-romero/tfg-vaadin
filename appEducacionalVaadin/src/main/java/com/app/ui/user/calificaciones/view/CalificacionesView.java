/**
* CalificacionesView.java
* appEducacionalVaadin
* 13/3/2015 23:42:46
* Copyright David
* com.app.ui.user.calificaciones
*/
package com.app.ui.user.calificaciones.view;

import java.util.List;

import com.app.ui.user.calendario.presenter.CalendarioPresenter;
import com.app.ui.user.calificaciones.presenter.CalificacionesPresenter;
import com.app.utility.Pair;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class CalificacionesView extends Panel implements View{

	TabSheet tabSheet;
	
	List<Pair<Component,String>> tabsCaption;
	
	CalificacionesPresenter presenter;
	
	private VerticalLayout root;
	
	public CalificacionesView(){
		this.tabSheet = new TabSheet();
		root = new VerticalLayout();
		presenter = CalificacionesPresenter.getInstance();
		tabsCaption = presenter.getTabs();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6028162736172827235L;

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.AbstractComponent#attach()
	 */
	@Override
	public void attach() {
		setSizeFull();
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		super.attach();
		setContent(root);
		tabSheet.addStyleName("right-aligned-tabs");
		root.addComponent(tabSheet);
		for ( Pair<Component,String> pair : tabsCaption ){
			tabSheet.addTab(pair.getFirst()).setCaption(pair.getSecond());
			/*addComponent(pair.getFirst());
			break;*/
		}
	}

	
}
