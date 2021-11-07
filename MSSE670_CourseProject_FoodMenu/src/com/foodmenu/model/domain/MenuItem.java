package com.foodmenu.model.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * 
 * @author Zach Stanfill
 * Domain MenuItem Class
 */
public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1234567L;
	
	private final DecimalFormat df = new DecimalFormat("#.##");

	/** Common Known Meal Name */
	private String mealName; 
	
	/** List of Food Items for the given Menu Object */ 
	private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
	
	/** User Specified Complexity Value for Menu Object */
	private int complexityValue = 0;
	
	/** Calculated Health Value of the Menu Object based on Food 
	 * Item Health Values 
	 */
	private double healthValue = 0.0;
	
	/** Default Menu Object constructor */
	public MenuItem() {
		
	}
	
	/** 
	 * Overloaded Menu Object Constructor
	 * @param mealName
	 * @param foodList
	 * @param complexityValue
	 * 
	 * Note: Health Value is calculated from Individual FoodItems 
	 */
	public MenuItem(String mealName, ArrayList<FoodItem> foodList, 
			int complexityValue) {
		super();
		this.mealName = mealName;
		this.foodList.addAll(foodList);
		this.complexityValue = complexityValue;
		foodList.forEach(item -> this.healthValue+=item.getHealthValue());
		this.healthValue = this.healthValue / foodList.size();
	}

	/**
	 * @return the mealName
	 */
	public String getMealName() {
		return mealName;
	}

	/**
	 * @param mealName the mealName to set
	 */
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	/**
	 * @return the foodList
	 */
	public ArrayList<FoodItem> getFoodList() {
		return foodList;
	}

	/**
	 * @param foodList the foodList to set
	 */
	public void setFoodList(ArrayList<FoodItem> foodList) {
		this.foodList = foodList;
		setHealthValue();
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
	 * Generate healthValue to set
	 */
	public void setHealthValue() {
		this.healthValue = 0.0;
		foodList.forEach(item -> this.healthValue += item.getHealthValue());
		this.healthValue = healthValue / foodList.size();
	}
	
	/**
	 * Generate Shopping List Generates a list of items based on the ingredients
	 * of each of the FoodItems within the MenuItem Object
	 * @return String(Menu Object Overview & Itemized Shopping List)
	 */
	public String generateShoppingList() {
		ArrayList<String> shoppingList = new ArrayList<String>();
		foodList.forEach(item -> {
			shoppingList.addAll(item.getIngredients());
		});
		HashSet<String> hset = new HashSet<String>(shoppingList);
		ArrayList<String> shoppingList2 = new ArrayList<String>(hset);
		Collections.sort(shoppingList2);
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("MealName: " + mealName);
		strBfr.append("\nMeal Complexity Value: " + df.format(complexityValue));
		strBfr.append("\nMeal Health Value: " + healthValue);
		strBfr.append("\nFood Item List: ");
		foodList.forEach(item -> {
			strBfr.append("\n\t" + item.getFoodName());
		});
		strBfr.append("\nShopping List: ");
		shoppingList2.forEach(food -> {
			strBfr.append("\n\t" + food);
		});
		
		return 	strBfr.toString();
	}

	/** 
	 * toString() method
	 * @return Formatted String listing all variables for the MenuItem Object
	 */
	public String toString() {
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("MealName: " + mealName);
		strBfr.append("\nMeal Complexity Value: " + complexityValue);
		strBfr.append("\nMeal Health Value: " + df.format(healthValue));
		foodList.forEach(item -> { 
			strBfr.append("\n****************************************\n"); 
			strBfr.append(item.toString());
		});
				
		return strBfr.toString(); 
	}
	
	/** 
	 * toSummaryString() method
	 * @return Formatted String listing some variables for the MenuItem Object
	 */
	public String toSummaryString() {
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("MealName: " + mealName);
		strBfr.append("\nMeal Complexity Value: " + complexityValue);
		strBfr.append("\nMeal Health Value: " + df.format(healthValue));
		foodList.forEach(item -> { 
			strBfr.append("\n****************************************\n"); 
			strBfr.append(item.toSummaryString());
		});
				
		return strBfr.toString(); 
	}
	
	/**
	 * validate() method
	 * @return boolean if all fields for MenuItem object is valid
	 */
	 public boolean validate() {
		 if(mealName == null) return false;
		 if(foodList == null) return false;
		 if(complexityValue < 0) return false;
		 if(healthValue < 0) return false;
		 		
		 return true;
	 }
	
}