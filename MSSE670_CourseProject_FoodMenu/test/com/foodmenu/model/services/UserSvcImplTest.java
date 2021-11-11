package com.foodmenu.model.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.OrderWith;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.model.services.userservice.*;

public class UserSvcImplTest {

	private final String TestClass = "UserService";
	
	@Test
	public void testUserCRUD() throws UserServiceException {
		testUserCreate();
		testUserRetrieve();
		testUserUpdate();
		testUserPassUpdate();
		testUserAuthenticate();
		testUserDelete();
	}
	
	
	public void testUserCreate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String fName = "Gwendolyn";
		String lName = "Stanfill";
		String email = "gwendolyn@gmail.com";
		String recPhr = "idon'tknow";
		int age = 4;
		String role = "user";
		String pass = "Unicorns";
		
		User testUser = new User(fName, lName, email, pass, recPhr, age, role);
		
		assertTrue ("testUser created", impl.createUserData(testUser));
		   System.out.println(TestClass + ".testUserCreate PASSED");
		
	}
	
	 
	public void testUserRetrieve() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String email = "gwendolyn@gmail.com";
		
		assertTrue ("testUser retrieved", impl.retrieveUserData(email).validate());
		   System.out.println(TestClass + ".testUserRetrieve PASSED");
		
	}
	
	
	public void testUserUpdate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String email = "gwendolyn@gmail.com";
		
		User user = impl.retrieveUserData(email);
		
		user.setFirstName("Gwen");
		user.setLastName("Stanfill");
		user.setRecoveryPhrase("Puppy Dog");
						
		assertTrue ("testUser Updated", impl.updateUserData(user));
		   System.out.println(TestClass + ".testUserUpdate PASSED");
	}


	public void testUserPassUpdate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String email = "gwendolyn@gmail.com";
		
		User user = impl.retrieveUserData(email);
		
		user.setPassword("ComplexPassword1234");
						
		assertTrue ("testUser Updated Password", impl.updateUserPasswordData(user));
		   System.out.println(TestClass + ".testUserPassUpdate PASSED");
	}
	

	public void testUserDelete() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String email = "gwendolyn@gmail.com";
		
		User user = impl.retrieveUserData(email);		
		
		assertTrue ("testUser Deleted", impl.deleteUserData(user));
		   System.out.println(TestClass + ".testUserDelete PASSED");
	}
	

	public void testUserAuthenticate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		String email = "gwendolyn@gmail.com";
		String password = "ComplexPassword1234";
		
		assertTrue ("testUser Authenticate", impl.authenticateUserData(email, password));
		   System.out.println(TestClass + ".testUserAuthenticate PASSED");
	}
}
