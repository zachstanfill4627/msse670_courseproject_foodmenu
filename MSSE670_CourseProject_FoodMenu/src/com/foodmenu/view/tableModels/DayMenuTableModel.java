package com.foodmenu.view.tableModels;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.MenuItem;

public class DayMenuTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Date", "Complexity Value", "Health Value"};
	
	private ArrayList<DayMenu> dayMenus = new ArrayList<DayMenu>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
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
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-d-yyyy");

		switch (column) {
			case 0:
				return sdf.format(date.getTime()).toString();
			case 1:
				return dayMenu.getComplexityValue();
			case 2:
				return df.format(dayMenu.getHealthValue());
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
