package com.foodmenu.model.business.managers;

import java.util.ArrayList;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.fooditemservice.IFoodItemService;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class FoodItemManager {

	public FoodItemManager() {
	}
	
	/** 
	 * Use Case : FoodItem-100
	 * Add New Food Item
	 */
	public boolean addNewFoodItem(FoodItem foodItem) throws ServiceLoadException, 
		FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IFoodItemService foodItemSvc = (IFoodItemService)serviceFactory.getService("IFoodItemService");
		if(foodItemSvc.createFoodItemData(foodItem)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Use Case : FoodItem-110
	 * Delete Existing Food Item
	 */
	public boolean deleteFoodItem(FoodItem foodItem) throws ServiceLoadException, 
		FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IFoodItemService foodItemSvc = (IFoodItemService)serviceFactory.getService("IFoodItemService");
		if(foodItemSvc.deleteFoodItemData(foodItem)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Use Case : FoodItem-120
	 * Retrieve All Food Item
	 */
	public ArrayList<FoodItem> retrieveAllFoodItems() throws ServiceLoadException, 
		FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IFoodItemService foodItemSvc = (IFoodItemService)serviceFactory.getService("IFoodItemService");
		return foodItemSvc.retrieveAllFoodItemData();
	}	
	
	/** 
	 * Use Case : FoodItem-130
	 * Retrieve Food Item
	 */
	public FoodItem retrieveFoodItem(String foodName) throws ServiceLoadException, 
		FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IFoodItemService foodItemSvc = (IFoodItemService)serviceFactory.getService("IFoodItemService");
		return foodItemSvc.retrieveFoodItemData(foodName);
	}	

}
