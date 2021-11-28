package com.foodmenu.view.tableModels;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.MenuItem;

public class DayMenuTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Date", "Complexity Value", "Health Value"};
	
	private ArrayList<DayMenu> dayMenus = new ArrayList<DayMenu>();
	
	public void setDayMenus(ArrayList<DayMenu> dayMenus) {
		this.dayMenus = dayMenus;
	}
	
	public int getRowCount() {
		return dayMenus.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		DayMenu dayMenu = dayMenus.get(row);
		
		Calendar date = dayMenu.getDate();
		
		switch (column) {
			case 0:
				/** Date Formatter */
				String dateString = String.format("%d-%d-%d", 
						date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
						date.get(Calendar.DATE));
				return dateString;
			case 1:
				return dayMenu.getComplexityValue();
			case 2:
				return dayMenu.getHealthValue();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
