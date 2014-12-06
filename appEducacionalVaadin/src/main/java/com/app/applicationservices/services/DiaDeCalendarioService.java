/**
 * DiaDeCalendarioService.java
 * appEducacional
 * 27/01/2014 09:24:27
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

/**
 * imports
 */
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.model.types.Alumno;
import com.app.domain.model.types.DiaDeCalendario;
import com.app.domain.repositories.DiaDeCalendarioRepository;
import com.app.utility.FechaUtils;

@Service
@Transactional
/**
 * @author David Romero Alcaide
 *
 */
public class DiaDeCalendarioService {

	/**
	 * Constructor
	 */
	public DiaDeCalendarioService() {
		super();

	}

	@Autowired
	/**
	 * Repositorio para interactuar con la base de datos
	 */
	private DiaDeCalendarioRepository diaDeCalendarioRepository;

	// Métodos CRUD

	/**
	 * Crea el dia de hoy en el calendario
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public DiaDeCalendario create() {
		Date fechaActual = new Date(System.currentTimeMillis());
		comprobarHoyLaborable(fechaActual);
		int[] fecha = FechaUtils.getFecha(fechaActual);
		DiaDeCalendario dia = new DiaDeCalendario();
		dia.setDia(fecha[0]);
		dia.setMes(fecha[1]);
		int[] curso = new int[2];
		if (fecha[1] <= 6) {
			curso[0] = fecha[2] - 1;
			curso[1] = fecha[2];
		} else {
			curso[0] = fecha[2];
			curso[1] = fecha[2] + 1;
		}
		dia.setCurso(curso);
		return dia;
	}

	/**
	 * @author David Romero Alcaide
	 * @param fechaActual
	 */
	private void comprobarHoyLaborable(Date fechaActual) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaActual);
		Assert.isTrue(
				calendario.get(Calendar.DAY_OF_WEEK) != 1
						&& calendario.get(Calendar.DAY_OF_WEEK) != 7,
				"dia.finde");
	}

	/**
	 * Buscar todos los dias del calendario
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<DiaDeCalendario> findAll() {
		return diaDeCalendarioRepository.findAll();
	}

	/**
	 * Buscar un dia de calendario por id
	 * 
	 * @author David Romero Alcaide
	 * @param profesorId
	 * @return
	 */
	public DiaDeCalendario findOne(int diaId) {
		Assert.isTrue(diaId > 0);
		return diaDeCalendarioRepository.findOne(diaId);
	}

	/**
	 * Almacena un dia de calendario
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void save(DiaDeCalendario dia) {
		Assert.isTrue(dia.getDia() > 0);
		Assert.isTrue(dia.getMes() > 0);
		Assert.isTrue(dia.getCurso().length == 2);
		diaDeCalendarioRepository.save(dia);
	}

	/**
	 * Elimina un dia de calendario
	 * 
	 * @author David Romero Alcaide
	 * @param profesor
	 */
	public void delete(DiaDeCalendario dia) {
		Assert.notNull(dia);
		Assert.isTrue(dia.getItemsEvaluable().isEmpty());
		diaDeCalendarioRepository.delete(dia);
	}

	// Otros métodos de negocio

	/**
	 * Busca el dia de hoy entre los dias de un alumno
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public DiaDeCalendario findHoy(Alumno a) {
		return findByFecha(new Date(System.currentTimeMillis()), a);
	}

	/**
	 * @author David Romero Alcaide
	 * @param hoy
	 * @param fecha
	 * @param dia
	 * @return
	 */
	private DiaDeCalendario buscarDia(int[] fecha, DiaDeCalendario dia) {
		if (fecha[1] <= 6) {
			return buscarDiaAntesJunio(fecha, dia);
		} else {
			return buscarDiaDespuesSeptiembre(fecha, dia);
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param hoy
	 * @param fecha
	 * @param dia
	 * @return
	 */
	private DiaDeCalendario buscarDiaDespuesSeptiembre(int[] fecha,
			DiaDeCalendario dia) {
		if (dia.getCurso()[0] == fecha[2]) {
			return dia;
		} else {
			return null;
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param hoy
	 * @param fecha
	 * @param dia
	 * @return
	 */
	private DiaDeCalendario buscarDiaAntesJunio(int[] fecha, DiaDeCalendario dia) {
		if (dia.getCurso()[1] == fecha[2]) {
			return dia;
		} else {
			return null;
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param fechaParseada
	 * @param alumn
	 * @return
	 */
	public DiaDeCalendario findByFecha(Date fechaParseada, Alumno alumn) {
		DiaDeCalendario hoy = null;
		int[] fecha = FechaUtils.getFecha(fechaParseada);
		Collection<DiaDeCalendario> diasIguales = diaDeCalendarioRepository
				.findPorDiaYMes(fecha[0], fecha[1], alumn.getId());
		for (DiaDeCalendario dia : diasIguales) {
			hoy = buscarDia(fecha, dia);
		}
		return hoy;
	}

	public DiaDeCalendario create(Date fechaDelDia) {
		comprobarHoyLaborable(fechaDelDia);
		int[] fecha = FechaUtils.getFecha(fechaDelDia);
		DiaDeCalendario dia = new DiaDeCalendario();
		dia.setDia(fecha[0]);
		dia.setMes(fecha[1]);
		int[] curso = new int[2];
		if (fecha[1] <= 6) {
			curso[0] = fecha[2] - 1;
			curso[1] = fecha[2];
		} else {
			curso[0] = fecha[2];
			curso[1] = fecha[2] + 1;
		}
		dia.setCurso(curso);
		return dia;
	}

}
