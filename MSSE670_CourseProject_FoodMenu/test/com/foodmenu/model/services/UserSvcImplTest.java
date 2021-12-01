package com.foodmenu.model.services;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.OrderWith;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.model.services.userservice.*;

public class UserSvcImplTest {

	private final String TestClass = "UserService";
	
	private String fName = "Gwendolyn";
	private String lName = "Stanfill";
	private String email = "gwendolyn@gmail.com";
	private String recPhr = "idon'tknow";
	private int age = 4;
	private String role = "user";
	private String pass = "0293e267fbffc1fe3960c1278f03bb3ab411f12ed2b016d639d8975bdaaf6b5a720235f63ed6d941a18e86ef717d95db7ce63e8b1d841161b4cbe857cf52433b";
	private String salt = "6VJwLqDgByaikTOx0RNZLwKODZHaYR4J";
	
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
		
		User testUser = new User(fName, lName, email, pass, recPhr, age, role);

		/**
		//Serialize User data into new user_testUser.obj		
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data/testObjectFiles/user_testUser.obj"));
			os.writeObject(testUser);
			os.close();
		} catch (IOException ex) {
		  ex.printStackTrace();
		}
		*/  
		
		assertTrue ("testUser created", impl.createUserData(testUser, salt));
		   System.out.println(TestClass + ".testUserCreate PASSED");
		
	}
	
	 
	public void testUserRetrieve() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		assertTrue ("testUser retrieved", impl.retrieveUserData(email).validate());
		   System.out.println(TestClass + ".testUserRetrieve PASSED");
	}
	
	public void testUserSaltRetrieve() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		assertTrue ("testUser salt key  Retrieved", impl.retrieveUserSaltData(email) == salt);
		   System.out.println(TestClass + ".testUserRetrieveSalt PASSED");
	}
	
	public void testUserRetrieveAll() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();

		ArrayList<User> users = impl.retrieveAllUserData();
		
		users.forEach(user -> System.out.println(user.getEmailAddress()));
		
		assertTrue ("testUser Retrieve All Users", true);
		   System.out.println(TestClass + ".testUserRetrieveAll PASSED");
		
	}
	
	public void testUserUpdate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		User user = impl.retrieveUserData(email);
		
		user.setFirstName("Gwen");
		user.setLastName("Stanfill");
		user.setRecoveryPhrase("Puppy Dog");
						
		assertTrue ("testUser Updated", impl.updateUserData(user));
		   System.out.println(TestClass + ".testUserUpdate PASSED");
	}


	public void testUserPassUpdate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		User user = impl.retrieveUserData(email);
		
		user.setPassword("1c47d8fc71d7e2762ba54d0691c53e268b04202f2807f0fb5f6e9b8f8bebe62175452d32eba1b048136f4c85ac4db4a4a68935daf029db2390f9b168b58c000d");
						
		assertTrue ("testUser Updated Password", impl.updateUserPasswordData(user));
		   System.out.println(TestClass + ".testUserPassUpdate PASSED");
	}
	

	public void testUserDelete() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		User user = impl.retrieveUserData(email);		
		
		assertTrue ("testUser Deleted", impl.deleteUserData(user));
		   System.out.println(TestClass + ".testUserDelete PASSED");
	}
	

	public void testUserAuthenticate() throws UserServiceException {
		UserSvcImpl impl = new UserSvcImpl();
		
		assertFalse ("testUser Authenticate (Wrong PW)", impl.authenticateUserData(email, pass));
		   System.out.println(TestClass + ".testUserAuthenticate PASSED");
		
		String password = "1c47d8fc71d7e2762ba54d0691c53e268b04202f2807f0fb5f6e9b8f8bebe62175452d32eba1b048136f4c85ac4db4a4a68935daf029db2390f9b168b58c000d";
		
		assertTrue ("testUser Authenticate (Correct PW)", impl.authenticateUserData(email, password));
		   System.out.println(TestClass + ".testUserAuthenticate PASSED");
	} 
}
