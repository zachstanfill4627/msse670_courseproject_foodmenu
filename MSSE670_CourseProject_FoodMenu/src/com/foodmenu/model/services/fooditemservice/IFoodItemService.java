package com.foodmenu.model.services.fooditemservice;

import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;

public interface IFoodItemService {
	
	public final String NAME = "IFoodItemService";
	
	public boolean createFoodItemData(FoodItem foodItem) throws FoodItemServiceException;
	public FoodItem retrieveFoodItemData(String foodName) throws FoodItemServiceException;
	public FoodItem retrieveFoodItemData(int foodItemID) throws FoodItemServiceException;
	public boolean updateFoodItemData(FoodItem foodItem) throws FoodItemServiceException;
	public boolean deleteFoodItemData(FoodItem foodItem) throws FoodItemServiceException;

}
