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
import com.foodmenu.model.business.managers.DayMenuManager;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

public class DayMenuManagerTest {
	
	private final String TestClass = "DayMenuManager";

	private ServiceFactory serviceFactory;
	private DayMenu dayMenu;
	private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
	private ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
	
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();
		
		ObjectInputStream dayMenuObject = null;
		try {
			dayMenuObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/dayMenu_20211106.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dayMenu = (DayMenu)dayMenuObject.readObject();
		
		menuList = dayMenu.getMenuList();
		
		menuList.forEach(menu -> {
			foodList.addAll(menu.getFoodList());
		});
			
		
		FoodItemManager foodItemManager = new FoodItemManager();
		MenuItemManager menuItemManager = new MenuItemManager();
		
		foodList.forEach(food -> {
			try {
				foodItemManager.addNewFoodItem(food);
			} catch (ServiceLoadException | FoodItemServiceException e) {
				e.printStackTrace();
			}
		});
		menuList.forEach(menu -> {
			try {
				menuItemManager.addNewMenuItem(menu);
			} catch (ServiceLoadException | MenuItemServiceException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	@Test
	public void testDayMenuManager() throws DayMenuServiceException {
		testDayMenuManagerAddDay();
		testDayMenuManagerDeleteDay();
	}
	
	public void testDayMenuManagerAddDay() throws DayMenuServiceException {
		DayMenuManager dayMenuManager = new DayMenuManager();
		
		try {
			assertTrue ("dayMenuManager addNew", dayMenuManager.addNewDayMenu(dayMenu));
			   System.out.println(TestClass + ".testDeleteDay PASSED");
		} catch (ServiceLoadException | DayMenuServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testDayMenuManagerDeleteDay() throws DayMenuServiceException {
		DayMenuManager dayMenuManager = new DayMenuManager();
		
		try {
			assertTrue ("dayMenuManager Delete", dayMenuManager.deleteDayMenu(dayMenu));
			   System.out.println(TestClass + ".testDeleteDay PASSED");
		} catch (ServiceLoadException | DayMenuServiceException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp() {
		FoodItemManager foodItemManager = new FoodItemManager();
		MenuItemManager menuItemManager = new MenuItemManager();
		
		menuList.forEach(menu -> {
			try {
				menuItemManager.deleteMenuItem(menu);
			} catch (ServiceLoadException | MenuItemServiceException e) {
				e.printStackTrace();
			}
		});
		foodList.forEach(food -> {
			try {
				foodItemManager.deleteFoodItem(food);
			} catch (ServiceLoadException | FoodItemServiceException e) {
				e.printStackTrace();
			}
		});
	}
}
