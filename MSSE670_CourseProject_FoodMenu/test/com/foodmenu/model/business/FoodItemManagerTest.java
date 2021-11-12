package com.foodmenu.model.business;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Before;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;

public class FoodItemManagerTest {

	ServiceFactory serviceFactory;
	FoodItem foodItem;
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();
		
		
		ObjectInputStream foodItemObject = null;
		try {
			foodItemObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/foodItem_Meatloaf.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		foodItem = (FoodItem)foodItemObject.readObject();
	}
	
	@Test
	public void testFoodItemManager() {
		FoodItemManager foodItemManager = new FoodItemManager();
		
		try {
			foodItemManager.addNewFoodItem(foodItem);
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
	}
}
