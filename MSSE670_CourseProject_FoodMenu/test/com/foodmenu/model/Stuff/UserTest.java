/**
 * 
 */
package com.foodmenu.model.Stuff;

import static org.junit.Assert.*;

import org.junit.Test;

import com.foodmenu.model.domain.User;

/**
 * @author zach
 *
 */
public class UserTest {

	private final String TestClass = "User";
	
	@Test
	public void testValidate() {
		String firstName = "John";
		String lastName = "Doe";
		String emailAddress = "johndoe1234@gmail.com";
		String password = "Password1234!@#$";
		// Recovery Question: Mothers Maiden Name
		String recoveryPhrase = "Stewart";
		int age = 29;
		// Dropdown = administrator / user
		String role = "administrator";
		
		User user1 = new User(firstName, lastName, emailAddress, password, 
				recoveryPhrase, age, role);
		
		assertTrue ("user1 validates",user1.validate());
			System.out.println(TestClass + ".testValidate PASSED");
		
	}

}
