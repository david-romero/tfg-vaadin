/**
 * PersonaProviderImpl.java
 * appEducacionalVaadin
 * 10/1/2015 19:07:02
 * Copyright David
 * com.app.presenter.data
 */
package com.app.presenter.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.CitaService;
import com.app.applicationservices.services.PadreMadreOTutorService;
import com.app.applicationservices.services.ProfesorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Cita;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Upload.SucceededEvent;

/**
 * @author David
 *
 */
public class PersonaProviderImpl implements PersonaProvider {

	public ByteArrayOutputStream outputStream;

	public byte[] file;

	private String fileName;

	public VerticalLayout viewContainerUpload;

	ProgressBar progressBar;

	private ApplicationContext applicationContext;

	private AdministradorService adminService;

	private ProfesorService profesorService;

	private PadreMadreOTutorService tutorService;

	public PersonaProviderImpl(VerticalLayout imageContainer) {
		this.viewContainerUpload = imageContainer;
		progressBar = new ProgressBar();
		loadBeans();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6110406325173825411L;

	/**
	 * @author David
	 */
	private void loadBeans() {
		applicationContext = ((AppUI) UI.getCurrent()).getApplicationContext();
		profesorService = applicationContext.getBean(ProfesorService.class);
		adminService = applicationContext.getBean(AdministradorService.class);
		tutorService = applicationContext
				.getBean(PadreMadreOTutorService.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		this.fileName = filename;
		outputStream = new ByteArrayOutputStream();
		return outputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.app.presenter.data.PersonaProvider#getPersona()
	 */
	@Override
	public Persona getPersona() {
		UserAccount account = AppUI.getCurrentUser();
		switch (Lists.newArrayList(account.getAuthorities()).get(0)
				.getAuthority()) {
		case Authority.PROFESOR:
			return  profesorService.findByUserAccount(account);
		case Authority.TUTOR:
			return tutorService.findByUserAccount(account);
		case Authority.ADMINISTRADOR:
			return adminService.findByUserAccount(account);

		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Upload.ProgressListener#updateProgress(long, long)
	 */
	@Override
	public void updateProgress(long readBytes, long contentLength) {
		progressBar.setValue(new Float(readBytes / (float) contentLength));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.ui.Upload.SucceededListener#uploadSucceeded(com.vaadin.ui.
	 * Upload.SucceededEvent)
	 */
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		file = outputStream.toByteArray();
		Image image = (Image) viewContainerUpload.getComponent(0);
		StreamResource resource = new StreamResource(
				(StreamSource) getStream(), this.fileName);
		image.setSource(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.ui.Upload.StartedListener#uploadStarted(com.vaadin.ui.Upload
	 * .StartedEvent)
	 */
	@Override
	public void uploadStarted(StartedEvent event) {
		viewContainerUpload.addComponent(progressBar);
		progressBar.setValue(0f);
		progressBar.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.server.StreamResource.StreamSource#getStream()
	 */
	@Override
	public InputStream getStream() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
		return inputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.ui.Upload.FinishedListener#uploadFinished(com.vaadin.ui.Upload
	 * .FinishedEvent)
	 */
	@Override
	public void uploadFinished(FinishedEvent event) {
		Persona persona = getPersona();
		persona.setImagen(file);
		switch (Lists.newArrayList(persona.getUserAccount().getAuthorities()).get(0)
				.getAuthority()) {
		case Authority.PROFESOR:
			Profesor profesor = (Profesor) persona;
			profesorService.save(profesor);
			break;
		case Authority.TUTOR:
			PadreMadreOTutor tutor = (PadreMadreOTutor) persona;
			tutorService.save(tutor);
			break;
		case Authority.ADMINISTRADOR:
			Administrador admin = (Administrador) persona;
			adminService.save(admin);
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Upload.FailedListener#uploadFailed(com.vaadin.ui.Upload.FailedEvent)
	 */
	@Override
	public void uploadFailed(FailedEvent event) {
		System.err.println(event.getReason());
		Notification.show("Error subiendo fichero",
				Type.ERROR_MESSAGE);
	}

}
