package com.foodmenu.model.business.managers;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.MenuItem;
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
}
