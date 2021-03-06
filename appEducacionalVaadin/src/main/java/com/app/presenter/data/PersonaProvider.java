/**
* PersonaProvider.java
* appEducacionalVaadin
* 29/12/2014 21:26:04
* Copyright David
* com.app.presenter.data
*/
package com.app.presenter.data;

import com.app.domain.model.types.Persona;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Upload.FinishedListener;

/**
 * @author David
 *
 */
public interface PersonaProvider extends Receiver,ProgressListener,SucceededListener,StartedListener,
											StreamSource,FinishedListener,FailedListener{

	public Persona getPersona();
	
}
