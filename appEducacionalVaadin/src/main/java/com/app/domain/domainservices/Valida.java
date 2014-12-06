/**
 * Valida.java
 * 08/01/2014
 * Copyright David
 */
package com.app.domain.domainservices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * @author David Romero Alcaide
 * 
 */
public class Valida {

	private Valida(){
		
	}
	
	/**
	 *  Obtener una instancia de logger denominada com.app"
	 */
	private static final Logger LOGGER = Logger.getLogger(Valida.class);
	
	/**
	 * @param dNI
	 * @return
	 */
	public static boolean validaDNI(String dni) {
		String dniCopia = dni;
		
		/*
		 * Establecemos el nivel del logger aunque esto se suele hacer por
		 * ficheros de configuracion
		 */
		dniCopia = extractDNIfromNIF(dniCopia);
		Matcher m = crearPatronYBuscador(dniCopia);
		if (m.matches()) {
			/*
			 *  Letra recibida
			 */
			String letra = m.group(2);
			/*
			 *  Extraer letra del DNI
			 */
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int numdni = extraerModuloNumeroDNI(m);
			String reference = letras.substring(numdni, numdni + 1);
			return comprobarLetraObtenidaConRecibida(letra, reference);
		} else{
			return false;
		}
			
	}

	/**
	 * @author David Romero Alcaide
	 * @param DNI
	 * @return
	 */
	private static Matcher crearPatronYBuscador(String dni) {
		Pattern dniPattern = Pattern
				.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		return dniPattern.matcher(dni);
	}

	/**
	 * @author David Romero Alcaide
	 * @param logger
	 * @param letra
	 * @param reference
	 * @return
	 */
	private static boolean comprobarLetraObtenidaConRecibida(String letra,
			String reference) {
		if (reference.equalsIgnoreCase(letra)) {
			LOGGER.debug("son iguales. Es DNI. " + letra + " " + reference);
			return true;
		} else {
			LOGGER.debug("NO son iguales. NO es DNI. " + letra + " "
					+ reference);
			return false;
		}
	}

	/**
	 * @author David Romero Alcaide
	 * @param m
	 * @return
	 */
	private static int extraerModuloNumeroDNI(Matcher m) {
		int dni = Integer.parseInt(m.group(1));
		dni = dni % 23;
		return dni;
	}

	/**
	 * @author David Romero Alcaide
	 * @param DNI
	 * @param logger
	 * @return
	 */
	private static String extractDNIfromNIF(String dni) {
		LOGGER.info("NIF " + dni);
		String dniCopia = dni;
		/*
		 *  si es NIE, eliminar la x,y,z inicial para tratarlo como nif
		 */
		if (dni.toUpperCase().startsWith("X")
				|| dni.toUpperCase().startsWith("Y")
				|| dni.toUpperCase().startsWith("Z")) {
			dniCopia = dni.substring(1);
		}
		return dniCopia;
	}

}
