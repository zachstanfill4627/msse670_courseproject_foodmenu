package com.foodmenu.model.services;

import static org.junit.Assert.*;

import org.junit.Test;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.userservice.*;

public class UserServicesImpl {

	private final String TestClass = "UserService";
	
	@Test
	public void testUserCreate() {
		UserServiceImpl impl = new UserServiceImpl();
		
		String fName = "Gwendolyn";
		String lName = "Stanfill";
		String email = "abcd@efgh.com";
		String recPhr = "idon'tknow";
		int age = 4;
		String role = "user";
		String pass = "Unicorns";
		
		User testUser = new User(fName, lName, email, pass, recPhr, age, role);
		
		assertTrue ("testUser created", impl.createUserData(testUser));
		   System.out.println(TestClass + ".testUserCreate PASSED");
		
	}

}
