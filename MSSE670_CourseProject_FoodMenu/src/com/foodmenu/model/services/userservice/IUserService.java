package com.foodmenu.model.services.userservice;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.IService;

public interface IUserService extends IService {

	public final String NAME = "IUserService";
	
	public boolean createUserData(User user);
	public boolean retrieveUserData();
	public boolean updateUserData(User user);
	public boolean deleteUserData(User user);
	public boolean authenticateUserData(User user);
	
}
