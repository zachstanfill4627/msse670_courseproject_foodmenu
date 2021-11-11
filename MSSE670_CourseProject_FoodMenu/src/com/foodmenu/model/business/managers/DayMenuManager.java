package com.foodmenu.model.business.managers;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.services.daymenuservice.IDayMenuService;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;

public class DayMenuManager {

	public DayMenuManager() {
	}
	
	/** 
	 * Use Case : Users-400
	 * Add New User Item
	 */
	public boolean DayMenuManager(DayMenu dayMenu) throws ServiceLoadException, 
		DayMenuServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IDayMenuService dayMenuSvc = (IDayMenuService)serviceFactory.getService("IDayMenuService");
		if(dayMenuSvc.createDayMenuData(dayMenu)) {
			return true;
		} else {
			return false;
		}
	}

}
