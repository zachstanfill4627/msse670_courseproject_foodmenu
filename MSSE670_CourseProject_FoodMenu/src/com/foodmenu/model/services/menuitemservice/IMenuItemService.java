package com.foodmenu.model.services.menuitemservice;

import com.foodmenu.model.domain.MenuItem;

public interface IMenuItemService {
	
	public boolean createMenuItemData(MenuItem menuItem);
	public MenuItem retrieveMenuItemData(String mealName);
	public MenuItem retrieveMenuItemData(int menuItemID);
	public boolean updateMenuItemData(MenuItem menuItem);
	public boolean deleteMenuItemData(MenuItem menuItem);

}
