/**
* ImprimirUI.java
* appEducacionalVaadin
* 15/1/2015 22:04:37
* Copyright David
* com.app.ui
*/
package com.app.ui;

import com.app.ui.user.informes.InformeEditor.SortableLayout;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class ImprimirUI extends UI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8948576734479060162L;
	private SortableLayout layout;
	
	@Override
    protected void init(VaadinRequest request) {
		layout = (SortableLayout) VaadinSession.getCurrent().getAttribute("layout");
		
        // Have some content to print
        setContent(layout);
        
        // Print automatically when the window opens
        JavaScript.getCurrent().execute(
            "setTimeout(function() {" +
            "  print(); self.close();}, 0);");
    }
	
}
