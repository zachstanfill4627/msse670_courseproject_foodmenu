package com.foodmenu.model.services.userservice;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.IService;
import com.foodmenu.model.services.exceptions.UserServiceException;

public interface IUserService extends IService {

	public final String NAME = "IUserService";
	
	public boolean createUserData(User user) throws UserServiceException;
	public User retrieveUserData(String email) throws UserServiceException;
	public boolean updateUserData(User user) throws UserServiceException;
	public boolean deleteUserData(User user) throws UserServiceException;
	public boolean authenticateUserData(String email, String password) throws UserServiceException;
	
}
