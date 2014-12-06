/**
 * Event.java
 * appEducacional
 * 02/04/2014 13:25:00
 * Copyright David Romero Alcaide
 * com.app.utility
 */
package com.app.utility;

/**
 * @author David Romero Alcaide Esta clase solo se utiliza para el caso de uso
 *         Ver Ficha para obtener el Calendario de Jquery
 */
public class EventCalendar {

	/**
	 * Constructor
	 */
	public EventCalendar() {
		super();

	}

	private int id;

	private String title;

	private String url;

	private long start;

	private long end;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set Establecer el id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set Establecer el title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set Establecer el url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set Establecer el start
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * @return end
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set Establecer el end
	 */
	public void setEnd(long end) {
		this.end = end;
	}

}
