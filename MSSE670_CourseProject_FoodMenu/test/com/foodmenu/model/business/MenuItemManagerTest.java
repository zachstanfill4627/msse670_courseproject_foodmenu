package com.foodmenu.model.business;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

public class MenuItemManagerTest {
	
	private final String TestClass = "MenuItemManager";

	private ServiceFactory serviceFactory;
	private MenuItem menuItem;
	
	private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>(); 
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();
			
		ObjectInputStream menuItemObject = null;
		try {
			menuItemObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/menuItem_BaconEggsToast.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		menuItem = (MenuItem)menuItemObject.readObject();
		
		foodList = menuItem.getFoodList();
		
		FoodItemManager foodItemManager = new FoodItemManager();
		
		foodList.forEach(food -> {
			try {
				foodItemManager.addNewFoodItem(food);
			} catch (ServiceLoadException | FoodItemServiceException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void testMenuItemManager() throws MenuItemServiceException {
		testAddMenu();
		testDeleteMenu();
	}
	
	public void testAddMenu() throws MenuItemServiceException {
		MenuItemManager menuItemManager = new MenuItemManager();
		
		try {
			assertTrue ("menuItemManager addNew", menuItemManager.addNewMenuItem(menuItem));
			   System.out.println(TestClass + ".testAddMenu PASSED");
		} catch (ServiceLoadException | MenuItemServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteMenu() throws MenuItemServiceException {
		MenuItemManager menuItemManager = new MenuItemManager();
		
		try {
			assertTrue ("menuItemManager Delete", menuItemManager.deleteMenuItem(menuItem));
			   System.out.println(TestClass + ".testDeleteMenu PASSED");
		} catch (ServiceLoadException | MenuItemServiceException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp() {
		FoodItemManager foodItemManager = new FoodItemManager();
		
		foodList.forEach(food -> {
			try {
				foodItemManager.deleteFoodItem(food);
			} catch (ServiceLoadException | FoodItemServiceException e) {
				e.printStackTrace();
			}
		});
	}
}
