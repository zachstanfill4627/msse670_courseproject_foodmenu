package com.foodmenu.model.business.managers;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.model.services.userservice.IUserService;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class UserManager {

	public UserManager() {
	}
	
	/** 
	 * Use Case : Users-400
	 * Add New User Item
	 */
	public boolean UserManager(User user) throws ServiceLoadException, 
		UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		if(userSvc.createUserData(user)) {
			return true;
		} else {
			return false;
		}
	}

}
