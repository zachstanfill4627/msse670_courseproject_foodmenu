package com.foodmenu.model.business;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Before;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.exceptions.UserPrivilegesException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;

public class FoodItemManagerTest {

	private final String TestClass = "FoodItemManager";
	
	private FoodItemManager foodItemManager;
	
	private ServiceFactory serviceFactory;
	private FoodItem foodItem;
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();
		
		ObjectInputStream adminObject = null;
		ObjectInputStream foodItemObject = null;
		try {
			adminObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/user_testAdmin.obj"));
			foodItemObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/foodItem_Meatloaf.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		foodItemManager = new FoodItemManager((User)adminObject.readObject());
		foodItem = (FoodItem)foodItemObject.readObject();
	}
	
	@Test
	public void testFoodItemManager() throws UserPrivilegesException {
		testAddFood();
		testDeleteFood();
	}
	
	public void testAddFood() {
		
		
		try {
			assertTrue ("foodItemManager addNew", foodItemManager.addNewFoodItem(foodItem));
			   System.out.println(TestClass + ".testAddFood PASSED");
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteFood() throws UserPrivilegesException {
		
		try {	
			assertTrue ("foodItemManager Delete", foodItemManager.deleteFoodItem(foodItem));
			   System.out.println(TestClass + ".testDeleteFood PASSED");
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
	}
}
