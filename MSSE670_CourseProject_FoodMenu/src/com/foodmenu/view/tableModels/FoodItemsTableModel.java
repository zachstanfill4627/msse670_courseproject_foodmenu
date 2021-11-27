package com.foodmenu.view.tableModels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.FoodItem;

public class FoodItemsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Food Name", "Category", "Health Value", "Prep Time"};
	
	private ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
	
	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}
	
	public int getRowCount() {
		return foodItems.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		FoodItem foodItem = foodItems.get(row);
		switch (column) {
			case 0:
				return foodItem.getFoodName();
			case 1:
				return foodItem.getCategory();
			case 2:
				return foodItem.getHealthValue();
			case 3:
				return foodItem.getPrepTime();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
