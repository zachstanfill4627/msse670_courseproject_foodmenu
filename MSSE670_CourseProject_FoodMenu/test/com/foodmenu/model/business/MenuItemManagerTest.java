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
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

public class MenuItemManagerTest {

	ServiceFactory serviceFactory;
	MenuItem menuItem;
	
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
		
		System.out.println(menuItem.toString());
		
	}
	
	@Test
	public void testFoodItemManager() throws MenuItemServiceException {
		MenuItemManager menuItemManager = new MenuItemManager();
		
		try {
			menuItemManager.addNewMenuItem(menuItem);
		} catch (ServiceLoadException | MenuItemServiceException e) {
			e.printStackTrace();
		}
	}
}
