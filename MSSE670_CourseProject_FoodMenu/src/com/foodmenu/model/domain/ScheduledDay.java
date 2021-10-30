package com.foodmenu.model.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Zach Stanfill
 */
public class ScheduledDay implements Serializable {

	private static final long serialVersionUID = 1234567L;
	
	/** Specified date for the given DayMenu */
	private Calendar date;
	
	/** DayMenu object */
	private DayMenu dayMenu;

	/** Default ScheduledDay Object constructor */
	public ScheduledDay() {

	}
	
	/**
	 * Overloaded ScheduledDay Constructor
	 * @param date
	 * @param dayMenu
	 */
	public ScheduledDay(Calendar date, DayMenu dayMenu) {
		super();
		this.date = date;
		this.dayMenu = dayMenu;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the dayMenu
	 */
	public DayMenu getDayMenu() {
		return dayMenu;
	}

	/**
	 * @param dayMenu the dayMenu to set
	 */
	public void setDayMenu(DayMenu dayMenu) {
		this.dayMenu = dayMenu;
	}
	
	/**
	 * validate() method
	 * @return boolean if all fields for ScheduledDay object is valid
	 */
	 public boolean validate() {
		 if(date == null) return false;
		 if(dayMenu == null) return false;
		 		 		
		 return true;
	 }
}
