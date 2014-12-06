/**
 * Asignatura.java
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 16:30
 */
package com.app.domain.model.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.junit.Assert;

import com.app.domain.model.DomainEntity;
import com.google.common.collect.Maps;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David Romero Alcaide
 * Clase que representa una asignatura impartida por un profesor perteneciente
 * a un curso
 */
public class Asignatura extends DomainEntity {

	/**
	 * 
	 * Constructor para inicializar colecciones y mapas
	 */
	public Asignatura() {
		super();
		this.itemsEvaluables = new ArrayList<ItemEvaluable>();
		this.criteriosDeEvaluacion = Maps.newHashMap();
	}

	/**
	 * Mapa para almacenar los criterios de evaluación. La clave será un item
	 * evaluable El integer representa el tanto por ciento (0 - 100)
	 */
	private Map<String, Integer> criteriosDeEvaluacion;
	/**
	 * Nombre o título de la asignatura
	 */
	private String nombre;

	/**
	 * @return the criteriosDeEvaluacion Cuando se añada un criterio vigilar que
	 *         el entero oscile entre 0 y 100
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	public Map<String, Integer> getCriteriosDeEvaluacion() {
		return criteriosDeEvaluacion;
	}

	/**
	 * @param criteriosDeEvaluacion
	 *            the criteriosDeEvaluacion to set
	 */
	public void setCriteriosDeEvaluacion(
			Map<String, Integer> criteriosDeEvaluacion) {
		this.criteriosDeEvaluacion = criteriosDeEvaluacion;
	}

	/**
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set Establecer el nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Relaciones

	/**
	 * Curso al que pertenece la asignatura
	 */
	private Curso curso;
	/**
	 * Colección de items evaluables pertenecientes a una asignatura
	 */
	private Collection<ItemEvaluable> itemsEvaluables;
	/**
	 * Profesor que imparte la asignatura
	 */
	private Profesor profesor;

	/**
	 * @return the curso
	 */
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Curso getCurso() {
		return curso;
	}

	/**
	 * @param curso
	 *            the curso to set
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	/**
	 * @return the itemsEvaluables
	 */
	@NotNull
	@OneToMany(mappedBy = "asignatura")
	public Collection<ItemEvaluable> getItemsEvaluables() {
		return itemsEvaluables;
	}

	/**
	 * @param itemsEvaluables
	 *            the itemsEvaluables to set
	 */
	public void setItemsEvaluables(Collection<ItemEvaluable> itemsEvaluables) {
		this.itemsEvaluables = itemsEvaluables;
	}

	public void addItemEvaluable(ItemEvaluable item) {
		this.itemsEvaluables.add(item);
		item.setAsignatura(this);
	}

	public boolean removeItemEvaluable(ItemEvaluable item) {
		item.setAsignatura(null);
		return this.itemsEvaluables.remove(item);
	}

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

	/**
	 * Añade un criterio de evaluación a la asignatura
	 * 
	 * @author David Romero Alcaide
	 * @param item
	 * @param porcentaje
	 */
	public void addCriterioDeEvaluacion(String item, int porcentaje) {
		final int barrera1 = 101;
		final int barrera2 = 100;
		final int barrera3 = -1;
		Assert.assertTrue(porcentaje > barrera3 && porcentaje < barrera1);
		Assert.assertFalse(obtenerSumaPorcentajes() + porcentaje > barrera2);
		this.criteriosDeEvaluacion.put(item, porcentaje);
	}

	/**
	 * Elimina un criterio de evaluación
	 * 
	 * @author David Romero Alcaide
	 * @param item
	 */
	public void removeCriterioDeEvaluacion(String item) {
		Assert.assertTrue(this.criteriosDeEvaluacion.containsKey(item));
		this.criteriosDeEvaluacion.remove(item);
	}

	/**
	 * Obtiene la suma de los porcentajes de los criterios de evaluación
	 */
	private int obtenerSumaPorcentajes() {
		int sumaPorcentajes = 0;
		for (Integer porcentajes : this.criteriosDeEvaluacion.values()) {
			sumaPorcentajes = sumaPorcentajes + porcentajes;
		}
		return sumaPorcentajes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Asignatura other = (Asignatura) obj;
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		if (profesor == null) {
			if (other.profesor != null) {
				return false;
			}
		} else if (!profesor.equals(other.profesor)) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		return true;
	}
}
