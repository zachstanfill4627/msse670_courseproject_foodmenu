package com.foodmenu.model.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author Zach Stanfill
 * Domain DayMenu Class
 */
public class DayMenu implements Serializable {
	
	private static final long serialVersionUID = 1234567L;
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	/** Specified date for the given DayMenu */
	private Calendar date;
	
	/** List of Menu Items for the given DayMenu Object */ 
	private ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
	
	/** User Specified Complexity Value for DayMenu Object */
	private int complexityValue = 0;
	
	/** Calculated Health Value of the DayMenu Object based on Menu Item
	 * Health Values 
	 */
	private double healthValue = 0.0;
	
	/** Default DayMenu Object constructor */
	public DayMenu() {
		
	}	
	
	/**
	 * Overloaded DayMenu Object Constructor
	 * @param menuList
	 */
	public DayMenu(Calendar date, ArrayList<MenuItem> menuList) {
		super();
		this.date = date;
		this.menuList.addAll(menuList);
		menuList.forEach(item -> {
			this.complexityValue += item.getComplexityValue();
			this.healthValue = Double.sum(this.healthValue, item.getHealthValue());
		});
		this.complexityValue = this.complexityValue / menuList.size();
		this.healthValue = this.healthValue	/ menuList.size();
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}
	
	public String getDateString() {
		String dateString = String.format("%d-%d-%d", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH)+1, 
				date.get(Calendar.DATE));
		
		return dateString;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * @return the menuList
	 */
	public ArrayList<MenuItem> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(ArrayList<MenuItem> menuList) {
		this.menuList = menuList;
	}
	
	/**
	 * @return the complexityValue
	 */
	public int getComplexityValue() {
		return complexityValue;
	}

	/**
	 * @param complexityValue the complexityValue to set
	 */
	public void setComplexityValue(int complexityValue) {
		this.complexityValue = complexityValue;
	}

	/**
	 * @return the healthValue
	 */
	public double getHealthValue() {
		return healthValue;
	}

	/**
	 * @param healthValue the healthValue to set
	 */
	public void setHealthValue() {
		this.healthValue = 0.0;
		menuList.forEach(item -> this.healthValue+=item.getHealthValue());
		this.healthValue = this.healthValue / menuList.size();
	}

	/** 
	 * toString() method
	 * @return Formatted String listing all variables for the DayMenu Object
	 */
	public String toString() {
		StringBuffer strBfr = new StringBuffer();
		strBfr.append(String.format("Scheduled Day: %d-%d-%d\n", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
				date.get(Calendar.DATE)));
		strBfr.append("Day Complexity Value: " + complexityValue);
		strBfr.append("\nDay Health Value: " + df.format(healthValue));
		menuList.forEach(item -> { 
			strBfr.append("\n****************************************\n"); 
			strBfr.append(item.toSummaryString());
		});
				
		return strBfr.toString(); 
	}
	
	/** 
	 * toSummaryString() method
	 * @return Formatted String listing some variables for the DayMenu Object
	 */
	public String toSummaryString() {
		StringBuffer strBfr = new StringBuffer();
		strBfr.append(String.format("Scheduled Day: %d-%d-%d\n", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
				date.get(Calendar.DATE)));
		strBfr.append("Day Complexity Value: " + complexityValue);
		strBfr.append("\nDay Health Value: " + df.format(healthValue));
		menuList.forEach(item -> { 
			strBfr.append("\n****************************************\n");
			strBfr.append("Menu Item: " + item.getMealName());
			item.getFoodList().forEach(food -> {
				strBfr.append("\n\t" + food.getFoodName());
			});
		});
				
		return strBfr.toString(); 
	}
	
	/**
	 * validate() method
	 * @return boolean if all fields for DayMenu object is valid
	 */
	 public boolean validate() {
		 if(date == null) return false;
		 if(menuList == null) return false;
		 if(complexityValue < 0) return false;
		 if(healthValue < 0) return false;
		 		
		 return true;
	 }
	
}