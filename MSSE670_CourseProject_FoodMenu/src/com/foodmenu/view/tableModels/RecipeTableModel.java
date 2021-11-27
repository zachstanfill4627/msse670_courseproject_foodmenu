package com.foodmenu.view.tableModels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.FoodItem;

public class RecipeTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Recipe"};
	
	private FoodItem foodItem = new FoodItem();
	private ArrayList<String> steps = new ArrayList<String>();
	
	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
		this.steps = foodItem.getRecipe();
	}
	
	public int getRowCount() {
		return steps.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		String step = steps.get(row);
		switch (column) {
			case 0:
				return step;
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
