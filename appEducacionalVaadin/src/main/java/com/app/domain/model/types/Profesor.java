/**
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 18:45
 */
package com.app.domain.model.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David
 * Esta clase es la encargada de simular el trabajo diario de un profesor.
 */
public class Profesor extends Persona {

	/**
	 * 
	 */
	public Profesor() {
		super();
		this.eventos = new ArrayList<Evento>();
		this.asignaturas = new ArrayList<Asignatura>();
		this.notificaciones = new ArrayList<Notificacion>();
	}

	// Atributos

	/**
	 * Este campo es utilizado para almacenar las preferencias horarias de un
	 * profesor en torno a concertar una cita
	 */
	private String preferenciasCita;
	/**
	 * Instituto en el que imparte clase el profesor
	 */
	private String instituto;

	/**
	 * @return instituto
	 */
	@NotBlank
	public String getInstituto() {
		return instituto;
	}

	/**
	 * @param instituto
	 *            the instituto to set Establecer el instituto
	 */
	public void setInstituto(String instituto) {
		this.instituto = instituto;
	}

	/**
	 * @return the preferenciasCita
	 */
	@NotBlank
	public String getPreferenciasCita() {
		return preferenciasCita;
	}

	/**
	 * @param preferenciasCita
	 *            the preferenciasCita to set
	 */
	public void setPreferenciasCita(String preferenciasCita) {
		this.preferenciasCita = preferenciasCita;
	}

	// Relaciones

	/**
	 * Eventos creados por el profesor
	 */
	private Collection<Evento> eventos;
	/**
	 * Asignaturas en las que imparte docencias el profesor
	 */
	private Collection<Asignatura> asignaturas;
	/**
	 * Notificaciones enviadas y recibidas por el profesor
	 */
	private Collection<Notificacion> notificaciones;

	@NotNull
	@OneToMany(mappedBy = "profesor")
	@LazyCollection(LazyCollectionOption.FALSE)
	/**
	 * @return the eventos
	 */
	public Collection<Evento> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos
	 *            the eventos to set
	 */
	public void setEventos(Collection<Evento> eventos) {
		this.eventos = eventos;
	}

	public void addEvento(Evento evento) {
		Assert.isTrue(this.asignaturas.contains(evento.getAsignatura()));
		this.eventos.add(evento);
		evento.setProfesor(this);
	}

	@NotNull
	@OneToMany(mappedBy = "profesor")
	@LazyCollection(LazyCollectionOption.FALSE)
	/**
	 * @return the asignaturas
	 */
	public Collection<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	/**
	 * @param asignaturas
	 *            the asignaturas to set
	 */
	public void setAsignaturas(Collection<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param asignatura
	 */
	public void addAsignaturas(Asignatura asignatura) {
		Assert.notNull(asignatura);
		Assert.isTrue(!this.asignaturas.contains(asignatura));
		Collection<String> nombresAsignaturas = getNombresAsignaturas();
		Assert.isTrue(!nombresAsignaturas.contains(asignatura.getNombre()));
		this.asignaturas.add(asignatura);
		asignatura.setProfesor(this);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param asignatura
	 * @return
	 */
	public boolean removeAsignatura(Asignatura asignatura) {
		Assert.notNull(asignatura);
		Assert.isTrue(this.asignaturas.contains(asignatura));
		asignatura.setProfesor(null);
		return this.asignaturas.remove(asignatura);
	}

	@NotNull
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "profesor")
	/**
	 * @return the notificaciones
	 */
	public Collection<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones
	 *            the notificaciones to set
	 */
	public void setNotificaciones(Collection<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param notificacion
	 */
	public void addNotificacion(Notificacion notificacion) {
		Assert.notNull(notificacion);
		this.notificaciones.add(notificacion);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param notificacion
	 */
	public boolean removeNotificacion(Notificacion notificacion) {
		Assert.notNull(notificacion);
		return this.notificaciones.remove(notificacion);
	}

	@Transient
	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	private Collection<String> getNombresAsignaturas() {
		List<String> lista = new ArrayList<String>();
		for (Asignatura asignatura : this.asignaturas) {
			lista.add(asignatura.getNombre());
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ""
				+ (getNombre() != null ? "" + getNombre() + " " : "")
				+ (getApellidos() != null ? "" + getApellidos() + ", " : "")
				+ (getTelefono() != null ? " Tlf: " + getTelefono() + ", " : "")
				+ (getEmail() != null ? " email: " + getEmail() : "") + "";
	}

}
