package com.foodmenu.model.business.managers;

import java.util.ArrayList;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.model.services.menuitemservice.IMenuItemService;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class MenuItemManager {

	public MenuItemManager() {
	}
	
	/** 
	 * Use Case : MenuItem-200
	 * Add New Menu Item
	 */
	public boolean addNewMenuItem(MenuItem menuItem) throws ServiceLoadException, 
		MenuItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IMenuItemService menuItemSvc = (IMenuItemService)serviceFactory.getService("IMenuItemService");
		if(menuItemSvc.createMenuItemData(menuItem)) {
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * Use Case : MenuItem-210
	 * Delete Existing Menu Item
	 */
	public boolean deleteMenuItem(MenuItem menuItem) throws ServiceLoadException, 
		MenuItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IMenuItemService menuItemSvc = (IMenuItemService)serviceFactory.getService("IMenuItemService");
		if(menuItemSvc.deleteMenuItemData(menuItem)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Use Case : MenuItem-220
	 * Retrieve All Menu Items
	 * @throws FoodItemServiceException 
	 */
	public ArrayList<MenuItem> retrieveAllMenuItems() throws ServiceLoadException, 
		MenuItemServiceException, FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IMenuItemService menuItemSvc = (IMenuItemService)serviceFactory.getService("IMenuItemService");
		return menuItemSvc.retrieveAllMenuItemData();
	}
	
	/** 
	 * Use Case : MenuItem-230
	 * Retrieve Menu Item
	 * @throws FoodItemServiceException 
	 */
	public MenuItem retrieveMenuItem(String mealName) throws ServiceLoadException, 
		MenuItemServiceException, FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IMenuItemService menuItemSvc = (IMenuItemService)serviceFactory.getService("IMenuItemService");
		return menuItemSvc.retrieveMenuItemData(mealName);
	}
}
