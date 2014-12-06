/**
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 17:10
 */
package com.app.domain.model.types;

/**
 * imports
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;

import com.app.domain.model.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David
 * 
 */
public class DiaDeCalendario extends DomainEntity implements
		Comparable<DiaDeCalendario> {

	/**
	 * Constructor for collections
	 */
	public DiaDeCalendario() {
		super();
		this.itemsEvaluable = new ArrayList<ItemEvaluable>();
	}

	/**
	 * Dia del mes
	 */
	private int dia;
	/**
	 * mes del curso
	 */
	private Integer mes;
	/**
	 * Curso. Corresponde a dos años consecutivos.
	 */
	private int[] curso;

	@Range(min = 1, max = 31)
	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	@Range(min = 1, max = 12)
	@NotNull
	/**
	 * @return the mes
	 */
	public Integer getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            the mes to set
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}

	@Size(min = 2, max = 2)
	/**
	 * @return the curso
	 */
	public int[] getCurso() {
		return curso;
	}

	/**
	 * @param curso
	 *            the curso to set
	 */
	public void setCurso(int[] newcurso) {
		if (newcurso == null) {
			this.curso = new int[0];
		} else {
			Assert.isTrue(newcurso.length == 2
					|| newcurso[1] - newcurso[0] == 1);
			GregorianCalendar calendario = new GregorianCalendar();
			/*
			 *  Menos uno porque si nos encontramos ya en el segundo año
			 *
			 *	actualmente daría error aunque estuvieramos en el mismo curso
			 */
			Assert.isTrue(newcurso[0] >= calendario.get(Calendar.YEAR) - 1);
			this.curso = Arrays.copyOf(newcurso, newcurso.length);
		}
	}

	// Relaciones

	/**
	 * Alumno al que pertenece el día del calendario
	 */
	private Alumno alumno;
	/**
	 * Colección con los items evaluables.
	 */
	private Collection<ItemEvaluable> itemsEvaluable;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	/**
	 * @return the alumno
	 */
	public Alumno getAlumno() {
		return alumno;
	}

	/**
	 * @param alumno
	 *            the alumno to set
	 */
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	@ManyToMany(mappedBy = "diasDelCalendario")
	@NotNull
	/**
	 * @return the itemsEvaluable
	 */
	public Collection<ItemEvaluable> getItemsEvaluable() {
		return itemsEvaluable;
	}

	/**
	 * @param itemsEvaluable
	 *            the itemsEvaluable to set
	 */
	public void setItemsEvaluable(Collection<ItemEvaluable> itemsEvaluable) {
		this.itemsEvaluable = itemsEvaluable;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param itemEvaluable
	 */
	public void addItemEvaluable(ItemEvaluable itemEvaluable) {
		itemEvaluable.addDiaDeCalendario(this);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param itemEvaluable
	 * @return
	 */
	public boolean removeItemEvaluable(ItemEvaluable itemEvaluable) {
		Assert.notNull(itemEvaluable);
		Assert.isTrue(this.itemsEvaluable.contains(itemEvaluable));
		return this.itemsEvaluable.remove(itemEvaluable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(DiaDeCalendario arg0) {
		return this.mes.compareTo(arg0.mes);
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
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + Arrays.hashCode(curso);
		result = prime * result + dia;
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DiaDeCalendario)) {
			return false;
		}
		DiaDeCalendario other = (DiaDeCalendario) obj;
		if (alumno == null) {
			if (other.alumno != null) {
				return false;
			}
		} else if (!alumno.equals(other.alumno)) {
			return false;
		}
		if (!Arrays.equals(curso, other.curso)) {
			return false;
		}
		if (dia != other.dia) {
			return false;
		}
		if (mes == null) {
			if (other.mes != null) {
				return false;
			}
		} else if (!mes.equals(other.mes)) {
			return false;
		}
		return true;
	}

}
