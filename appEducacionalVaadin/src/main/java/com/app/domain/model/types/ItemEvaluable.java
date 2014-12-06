/**
 * ItemEvaluable.java
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 17:50
 */
package com.app.domain.model.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import com.app.domain.model.DomainEntity;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David
 * Clase abstracta que representa el concepto de un item evaluable
 */
public abstract class ItemEvaluable extends DomainEntity {

	/**
	 * Constuctor vacío
	 */
	public ItemEvaluable() {
		super();
		this.diasDelCalendario = new ArrayList<DiaDeCalendario>();
	}

	/**
	 * Calificación del item
	 */
	private double calificacion;
	/**
	 * Título del item
	 */
	private String titulo;
	/**
	 * Fecha en la que ha ocurrido el item
	 */
	private Date fecha;

	@Min(0)
	/**
	 * @return the calificacion
	 */
	public double getCalificacion() {
		return calificacion;
	}

	/**
	 * @param calificacion
	 *            the calificacion to set
	 */
	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

	@NotBlank
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = new Date(fecha.getTime());
	}

	// Relaciones

	/**
	 * Evaluación a la cual pertenece el item
	 */
	private Evaluacion evaluacion;
	/**
	 * Asignatura a la cual pertenece el item
	 */
	private Asignatura asignatura;
	/**
	 * Día de calendario en el que ocurrió el item
	 */
	private Collection<DiaDeCalendario> diasDelCalendario;

	@Valid
	@NotNull
	@ManyToOne
	/**
	 * @return the evaluacion
	 */
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	/**
	 * @param evaluacion
	 *            the evaluacion to set
	 */
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	@Valid
	@NotNull
	@ManyToOne
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

	@ManyToMany
	@NotNull
	/**
	 * @return the diasDelCalendario
	 */
	public Collection<DiaDeCalendario> getDiasDelCalendario() {
		return diasDelCalendario;
	}

	/**
	 * @param diaDeCalendario
	 *            the diaDeCalendario to set
	 */
	public void setDiasDelCalendario(
			Collection<DiaDeCalendario> diasDelCalendario) {
		this.diasDelCalendario = diasDelCalendario;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param diaDeCalendario
	 */
	public void addDiaDeCalendario(DiaDeCalendario diaDeCalendario) {
		this.diasDelCalendario.add(diaDeCalendario);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param diaDeCalendario
	 * @return
	 */
	public boolean removeDiaDeCalendario(DiaDeCalendario diaDeCalendario) {
		Assert.notNull(diaDeCalendario);
		Assert.isTrue(this.diasDelCalendario.contains(diaDeCalendario));
		return this.diasDelCalendario.remove(diaDeCalendario);
	}

}
