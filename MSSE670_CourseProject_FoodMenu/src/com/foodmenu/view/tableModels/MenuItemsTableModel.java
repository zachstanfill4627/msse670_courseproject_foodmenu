package com.foodmenu.view.tableModels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.MenuItem;

public class MenuItemsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Meal Name", "Complexity Value", "Health Value"};
	
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	public int getRowCount() {
		return menuItems.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		MenuItem menuItem = menuItems.get(row);
		switch (column) {
			case 0:
				return menuItem.getMealName();
			case 1:
				return df.format(menuItem.getComplexityValue());
			case 2:
				return menuItem.getHealthValue();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
