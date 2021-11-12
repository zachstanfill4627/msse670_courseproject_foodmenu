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

	private final String TestClass = "FoodItemManager";
	
	private ServiceFactory serviceFactory;
	private FoodItem foodItem;
	
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
		testAddFood();
		testDeleteFood();
	}
	
	public void testAddFood() {
		FoodItemManager foodItemManager = new FoodItemManager();
		
		try {
			assertTrue ("foodItemManager addNew", foodItemManager.addNewFoodItem(foodItem));
			   System.out.println(TestClass + ".testAddFood PASSED");
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteFood() {
		FoodItemManager foodItemManager = new FoodItemManager();
		
		try {	
			assertTrue ("foodItemManager Delete", foodItemManager.deleteFoodItem(foodItem));
			   System.out.println(TestClass + ".testDeleteFood PASSED");
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
	}
}
