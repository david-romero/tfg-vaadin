/**
* UploadButton.java
* appEducacionalVaadin
* 10/1/2015 19:01:59
* Copyright David
* com.app.ui.components
*/
package com.app.ui.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.app.presenter.data.PersonaProvider;
import com.app.presenter.data.PersonaProviderImpl;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David
 *
 */
public class UploadButton extends VerticalLayout implements Upload.ProgressListener,
Upload.Receiver, Upload.SucceededListener, Upload.FailedListener{

	private Upload upload;
	
	private VerticalLayout layout;
	
	private Image image;
	
	private ProgressBar progressBar;
	
	ByteArrayOutputStream os;
	
	private String filename;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490359252222599198L;
	
	public UploadButton(){
		super();
		os = new ByteArrayOutputStream(1024);
		upload = new Upload("Change...", this);
		setImmediate(true);
		upload.setImmediate(true);
		
		Resource source = new ThemeResource(
				"img/profile-pic-300px.jpg");
		image = new Image("", source);
		image.markAsDirty();
		addComponent(image);
		addComponent(upload);
		
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Upload.FailedListener#uploadFailed(com.vaadin.ui.Upload.FailedEvent)
	 */
	@Override
	public void uploadFailed(FailedEvent event) {
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.Upload.SucceededEvent)
	 */
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		Resource source = new StreamResource(
                new StreamResource.StreamSource() {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 3561230243878849854L;

					public InputStream getStream() {
                        return new ByteArrayInputStream(os.toByteArray());
                    }
                }, this.filename) {
            /**
					 * 
					 */
					private static final long serialVersionUID = -2835737445785315855L;

			@Override
            public String getMIMEType() {
                return "image/png";
            }
        };
		this.image.setSource(source);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String, java.lang.String)
	 */
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		os.reset();
		this.filename = filename;
		return os;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Upload.ProgressListener#updateProgress(long, long)
	 */
	@Override
	public void updateProgress(long readBytes, long contentLength) {
	}


}
