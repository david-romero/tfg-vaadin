/**
 * FechaUtils.java
 * appEducacional
 * 20/01/2014 19:25:50
 * Copyright David Romero Alcaide
 * com.app.utility
 */
package com.app.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

/**
 * @author David Romero Alcaide
 * 
 */
public class FechaUtils {

	private static final Logger LOGGER = Logger.getLogger(FechaUtils.class);

	public static int[] getFecha(Date fechaDate) {
		int[] fecha = new int[3];
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaDate);
		fecha[0] = cal.get(Calendar.DAY_OF_MONTH);
		// Los meses empiezan por 0
		fecha[1] = cal.get(Calendar.MONTH) + 1; 
		fecha[2] = cal.get(Calendar.YEAR);
		return fecha;
	}

	public static List<Date> getDiasFestivos() {
		List<Date> festivos = Lists.newArrayList();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		int anioActual = getFecha(new Date(System.currentTimeMillis()))[2];
		try {
			String fecha = "01-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "02-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "03-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "04-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "05-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "06-01-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "28-02-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "29-02-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "30-02-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-02-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-04-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "01-05-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-06-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-09-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "12-10-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "01-11-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-11-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "06-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "08-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "23-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "24-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "25-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "26-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "27-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "28-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "29-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "30-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
			fecha = "31-12-";
			festivos.add(df.parse(fecha + anioActual));
			festivos.add(df.parse(fecha + (anioActual - 1)));
			festivos.add(df.parse(fecha + (anioActual + 1)));
		} catch (ParseException e) {

			LOGGER.error(e);
		}
		return festivos;
	}

}
