package com.foodmenu.model.business.managers;

import java.util.ArrayList;
import java.util.Calendar;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.exceptions.UserPrivilegesException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.daymenuservice.IDayMenuService;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class DayMenuManager {

	private User user;
	
	public DayMenuManager() {
	}
	
	public DayMenuManager(User user) {
		this.user = user;
	}
	
	/** 
	 * Use Case : DayMenu-300
	 * Add New Day Menu
	 */
	public boolean addNewDayMenu(DayMenu dayMenu) throws ServiceLoadException, 
		DayMenuServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");
		if(dayMenuSvc.createDayMenuData(dayMenu)) {
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * Use Case : DayMenu-310
	 * Delete Existing Day Menu
	 * @throws UserPrivilegesException 
	 */
	public boolean deleteDayMenu(DayMenu dayMenu) throws ServiceLoadException, 
		DayMenuServiceException, UserPrivilegesException {
		
		if(this.user.getRole().equals("admin")) {
			ServiceFactory serviceFactory = new ServiceFactory();
			IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");
			if(dayMenuSvc.deleteDayMenuData(dayMenu)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new UserPrivilegesException(String.format("User %s isn't an admin, and therefore does not have the \nappropriate privileges to perform delete task!", user.getEmailAddress()));
		}
	}
	
	/** 
	 * Use Case : DayMenu-320
	 * Retrieve All Day Menus
	 */
	public ArrayList<DayMenu> retrieveAllDayMenus() throws ServiceLoadException, 
		DayMenuServiceException, MenuItemServiceException, FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");		
		return dayMenuSvc.retrieveAllDayMenuData();
	}
	
	/** 
	 * Use Case : DayMenu-330
	 * Retrieve Day Menu
	 */
	public DayMenu retrieveDayMenu(Calendar date) throws ServiceLoadException, 
		DayMenuServiceException, MenuItemServiceException, FoodItemServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");
		return dayMenuSvc.retrieveDayMenuData(date);
	}
}
