package com.foodmenu.view.tableModels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import com.foodmenu.model.domain.FoodItem;

public class IngredientsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Ingredient"};
	
	private FoodItem foodItem = new FoodItem();
	private ArrayList<String> ingredients = new ArrayList<String>();
	
	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
		this.ingredients = foodItem.getIngredients();
	}
	
	public void setFoodItem(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public int getRowCount() {
		return ingredients.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		String ingredient = ingredients.get(row);
		switch (column) {
			case 0:
				return ingredient;
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
