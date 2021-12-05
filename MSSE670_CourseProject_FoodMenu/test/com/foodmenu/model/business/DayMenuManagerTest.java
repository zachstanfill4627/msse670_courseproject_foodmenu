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
import com.foodmenu.model.business.exceptions.UserPrivilegesException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.business.managers.DayMenuManager;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

public class DayMenuManagerTest {
	
	private final String TestClass = "DayMenuManager";

	private DayMenuManager dayMenuManager;
	private MenuItemManager menuItemManager;
	private FoodItemManager foodItemManager;
	
	private ServiceFactory serviceFactory;
	private DayMenu dayMenu;
	private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
	private ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
	
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();
		
		ObjectInputStream adminObject = null;
		ObjectInputStream dayMenuObject = null;
		try {
			adminObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/user_testAdmin.obj"));
			dayMenuObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/dayMenu_20211106.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		User admin = (User)adminObject.readObject(); 
		
		dayMenuManager = new DayMenuManager(admin);
		foodItemManager = new FoodItemManager(admin);
		menuItemManager = new MenuItemManager(admin);
		dayMenu = (DayMenu)dayMenuObject.readObject();
		
		menuList = dayMenu.getMenuList();
		
		menuList.forEach(menu -> {
			foodList.addAll(menu.getFoodList());
		});
		
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
	public void testDayMenuManager() throws DayMenuServiceException, UserPrivilegesException {
		testDayMenuManagerAddDay();
		testDayMenuManagerDeleteDay();
	}
	
	public void testDayMenuManagerAddDay() throws DayMenuServiceException {
		
		try {
			assertTrue ("dayMenuManager addNew", dayMenuManager.addNewDayMenu(dayMenu));
			   System.out.println(TestClass + ".testDeleteDay PASSED");
		} catch (ServiceLoadException | DayMenuServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testDayMenuManagerDeleteDay() throws DayMenuServiceException, UserPrivilegesException {
		
		try {
			assertTrue ("dayMenuManager Delete", dayMenuManager.deleteDayMenu(dayMenu));
			   System.out.println(TestClass + ".testDeleteDay PASSED");
		} catch (ServiceLoadException | DayMenuServiceException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp() {
		
		menuList.forEach(menu -> {
			try {
				menuItemManager.deleteMenuItem(menu);
			} catch (ServiceLoadException | MenuItemServiceException | UserPrivilegesException e) {
				e.printStackTrace();
			}
		});
		foodList.forEach(food -> {
			try {
				foodItemManager.deleteFoodItem(food);
			} catch (ServiceLoadException | FoodItemServiceException | UserPrivilegesException e) {
				e.printStackTrace();
			}
		});
	}
}
