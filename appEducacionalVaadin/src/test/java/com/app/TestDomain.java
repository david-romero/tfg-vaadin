/**
 * 
 */
package com.app;

import org.junit.Test;

import com.app.domain.model.types.DiaDeCalendario;

/**
 * @author David
 * 
 */
public class TestDomain {
	@Test
	public void testingCursos() {
		int curso1 = 2014;
		int curso2 = 2015;
		int curso3 = 2020;
		int curso4 = 2016;
		int curso5 = 2030;
		int curso6 = 2031;

		DiaDeCalendario dia = new DiaDeCalendario();
		int curso[] = new int[2];

		curso[0] = curso1;
		curso[1] = curso2;
		dia.setCurso(curso);

		curso[0] = curso3;
		curso[1] = curso4;
		dia.setCurso(curso);

		curso[0] = curso3;
		curso[1] = curso5;
		dia.setCurso(curso);

		curso[0] = curso4;
		curso[1] = curso5;
		dia.setCurso(curso);

		curso[0] = curso5;
		curso[1] = curso6;
		dia.setCurso(curso);
	}

}
