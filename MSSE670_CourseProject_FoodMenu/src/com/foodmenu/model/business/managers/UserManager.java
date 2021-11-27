package com.foodmenu.model.business.managers;

import java.util.ArrayList;

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
	 * Add New User
	 */
	public boolean addNewUser(User user) throws ServiceLoadException, 
		UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		if(userSvc.createUserData(user)) {
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * Use Case : Users-410
	 * Delete Existing User 
	 */
	public boolean deleteUser(User user) throws ServiceLoadException, 
		UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		if(userSvc.deleteUserData(user)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Use Case : Users-420
	 * Authenticate User
	 */
	public boolean authenticateUser(String email, String password) throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		if(userSvc.authenticateUserData(email, password)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Use Case : Users-430
	 * Retrieve User
	 */
	public User retrieveUser(String email) throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		return userSvc.retrieveUserData(email);	
	}
	
	/**
	 * Use Case : Users-440
	 * Retrieve All Users
	 */
	public ArrayList<User> retrieveAllUsers() throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		return userSvc.retrieveAllUserData();	
	}
	
	/**
	 * Use Case : Users-450
	 * Reset User Password -- Trusted
	 */
	public boolean resetUserPassword(User user) throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		return userSvc.updateUserPasswordData(user);	
	}
}
