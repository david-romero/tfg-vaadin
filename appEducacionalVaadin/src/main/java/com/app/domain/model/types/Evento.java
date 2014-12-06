/**
 * Evento.java
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 17:40
 */
package com.app.domain.model.types;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import com.app.domain.model.DomainEntity;
import com.app.domain.model.types.itemsevaluables.Actividad;
import com.app.domain.model.types.itemsevaluables.Cuaderno;
import com.app.domain.model.types.itemsevaluables.EjerciciosEntregados;
import com.app.domain.model.types.itemsevaluables.Examen;
import com.app.domain.model.types.itemsevaluables.Trabajo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Access(AccessType.PROPERTY)
@JsonIgnoreProperties({ "profesor", "asignatura" })
/**
 * @author David Romero Alcaide
 * Clase que representa al un posible evento creado por un profesor
 * Los eventos pueden ser del tipo examen para el día tal, entrega 
 * de trabajo para el día tal, etc...
 * 
 */
public class Evento extends DomainEntity {

	/**
	 * Constructor vacio
	 */
	public Evento() {
		super();

	}

	/**
	 * Debe ser siempre en futuro
	 */
	private Date fecha;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		if (fecha != null) {
			return (Date) fecha.clone();
		} else {
			return new Date();
		}
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 *            Future!
	 */
	public void setFecha(Date fecha) {
		this.fecha = new Date(fecha.getTime());
	}

	// Relaciones

	/**
	 * Profesor que crea el evento
	 */
	private Profesor profesor;
	/**
	 * Asignatura a la que está vinculada el evento
	 */
	private Asignatura asignatura;
	/**
	 * Item evaluable relacionado con el evento
	 */
	private ItemEvaluable itemEvaluable;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	/**
	 * @return the profesor
	 */
	public Profesor getProfesor() {
		return profesor;
	}

	/**
	 * @param profesor
	 *            the profesor to set
	 */
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	/**
	 * @return the asignatura
	 */
	public Asignatura getAsignatura() {
		return asignatura;
	}

	/**
	 * @param asignatura
	 *            the asignatura to set
	 */
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	/**
	 * @return the itemEvaluable
	 */
	public ItemEvaluable getItemEvaluable() {
		return itemEvaluable;
	}

	/**
	 * @param itemEvaluable
	 *            the itemEvaluable to set Solo se podrán marcar los exámenes,
	 *            trabajos, cuardernos, actividades o ejercicios entregados
	 */
	public void setItemEvaluable(ItemEvaluable itemEvaluable) {
		Assert.isTrue(itemEvaluable instanceof Examen
				|| itemEvaluable instanceof Trabajo
				|| itemEvaluable instanceof Cuaderno
				|| itemEvaluable instanceof Actividad || itemEvaluable instanceof EjerciciosEntregados);
		this.itemEvaluable = itemEvaluable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Evento clone() throws CloneNotSupportedException {
		Evento clone = new Evento();
		clone.setAsignatura(asignatura);
		clone.setFecha(fecha);
		clone.setItemEvaluable(itemEvaluable);
		clone.setProfesor(profesor);
		return clone;
	}

}
