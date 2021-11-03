package com.foodmenu.model.services.userservice;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.IService;

public interface IUserService extends IService {

	public final String NAME = "IUserService";
	
	public boolean createUserData(User user);
}