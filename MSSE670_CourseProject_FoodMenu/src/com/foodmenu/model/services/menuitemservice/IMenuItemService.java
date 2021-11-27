package com.foodmenu.model.services.menuitemservice;

import java.util.ArrayList;

import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.IService;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

public interface IMenuItemService extends IService {
	
	public boolean createMenuItemData(MenuItem menuItem) throws MenuItemServiceException;
	public MenuItem retrieveMenuItemData(String mealName) throws MenuItemServiceException, FoodItemServiceException;
	public MenuItem retrieveMenuItemData(int menuItemID) throws MenuItemServiceException, FoodItemServiceException;
	public ArrayList<MenuItem> retrieveAllMenuItemData() throws MenuItemServiceException, FoodItemServiceException;
	public boolean updateMenuItemData(MenuItem menuItem) throws MenuItemServiceException;
	public boolean deleteMenuItemData(MenuItem menuItem) throws MenuItemServiceException;
}
