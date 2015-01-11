/**
* UploadButton.java
* appEducacionalVaadin
* 10/1/2015 19:01:59
* Copyright David
* com.app.ui.components
*/
package com.app.ui.components;

import java.io.OutputStream;

import com.app.presenter.data.PersonaProvider;
import com.app.presenter.data.PersonaProviderImpl;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class UploadButton extends Upload {

	private PersonaProvider personaProvider;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490359252222599198L;
	
	public UploadButton(){
		super();
		VerticalLayout parent = (VerticalLayout) this.getParent();
		personaProvider = new PersonaProviderImpl(parent);
		setCaption("");
		setReceiver(personaProvider);
		setImmediate(true);
		this.setButtonCaption("Change...");
	}


}
