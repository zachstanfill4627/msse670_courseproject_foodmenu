package com.foodmenu.model.services.fooditemservice;

import com.foodmenu.model.domain.FoodItem;

public interface IFoodItemService {
	
	public final String NAME = "IFoodItemService";
	
	public boolean createFoodItemData(FoodItem foodItem);
	public FoodItem retrieveFoodItemData(String foodName);
	public FoodItem retrieveFoodItemData(int foodItemID);
	public boolean updateFoodItemData(FoodItem foodItem);
	public boolean deleteFoodItemData(FoodItem foodItem);

}
