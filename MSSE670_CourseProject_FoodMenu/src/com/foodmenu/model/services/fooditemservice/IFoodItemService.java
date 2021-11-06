package com.foodmenu.model.services.fooditemservice;

import com.foodmenu.model.domain.FoodItem;

public interface IFoodItemService {
	
	public final String NAME = "IFoodItemService";
	
	public boolean createFoodItemData(FoodItem foodItem);
	public FoodItem retrieveFoodItemData(String foodName);
	public boolean updateFoodItemData();
	public boolean deleteFoodItemData();

}
