package com.foodmenu.model.business.managers;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.services.daymenuservice.IDayMenuService;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class DayMenuManager {

	public DayMenuManager() {
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
	 */
	public boolean deleteDayMenu(DayMenu dayMenu) throws ServiceLoadException, 
		DayMenuServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");
		if(dayMenuSvc.deleteDayMenuData(dayMenu)) {
			return true;
		} else {
			return false;
		}
	}
}
