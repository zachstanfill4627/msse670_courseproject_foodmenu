package com.foodmenu.model.business;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Before;
import org.junit.Test;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.exceptions.UserPrivilegesException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;

public class UserManagerTest {

	private final String TestClass = "UserManager";
	
	private ServiceFactory serviceFactory;
	private UserManager userManager;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		serviceFactory = new ServiceFactory();

		ObjectInputStream adminObject = null;
		ObjectInputStream userObject = null;
		try {
			adminObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/user_testAdmin.obj"));
			userObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/user_testUser.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		userManager = new UserManager((User)adminObject.readObject());
		user = (User)userObject.readObject();
	}
	
	@Test
	public void testUserManager() throws UserServiceException, IOException, UserPrivilegesException, ServiceLoadException {
		testUserAdd();
		testUserAuthenticate();
		testUserDelete();
	}
	
	public void testUserAdd() throws UserServiceException, IOException, ServiceLoadException {
		
		user.setPassword("UnicornLover1234!!");
		
		try {
			assertTrue ("userManager addNew", userManager.addNewUser(user));
			   System.out.println(TestClass + ".testAddNewUser PASSED");
		} catch (ServiceLoadException | UserServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testUserDelete() throws UserServiceException, UserPrivilegesException {
		
		try {
			assertTrue ("dayMenuManager Delete", userManager.deleteUser(user));
			   System.out.println(TestClass + ".testDeleteUser PASSED");
		} catch (ServiceLoadException | UserServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testUserAuthenticate() throws UserServiceException {
		
		user.setPassword("UnicornLover1234!!");
		
		try {
			assertTrue ("dayMenuManager Authenticate", userManager.authenticateUser(user.getEmailAddress(), user.getPassword()));
			   System.out.println(TestClass + ".testAuthenticateUser PASSED");
		} catch (ServiceLoadException | UserServiceException e) {
			e.printStackTrace();
		}
	}
}
