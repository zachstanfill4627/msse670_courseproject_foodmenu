package com.foodmenu.model.services.userservice;

import java.sql.*;
import com.foodmenu.model.domain.User;

public class UserServiceImpl implements IUserService {

	private String connString = "jdbc:sqlite:data/FoodMenu.db";
	
	public UserServiceImpl() {
		
	}

	public boolean createUserData(User user) {
		
		// Localize Variables
		String fName = user.getFirstName();
		String lName = user.getLastName();
		String email = user.getEmailAddress();
		String recPhr = user.getRecoveryPhrase();
		int age = user.getAge();
		String role = user.getRole();
		String pass = user.getPassword();
		
		StringBuffer strBfr = new StringBuffer();
		
		
		strBfr.append(String.format("INSERT INTO info (infotext) values (\"%s\");", 
				pass));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		strBfr.append(String.format("INSERT INTO users (firstname, lastname, "
				+ "email, recoveryphrase, age, role, infoid) "
				+ "values (\"%s\", \"%s\",\"%s\", \"%s\", %d, %s, %s);",
				fName, lName, email, recPhr, age, 
				"(select roleid from roles where rolename == \"user\")", 
				"(select last_insert_rowid() from info limit 1)"));
		String sql2 = strBfr.toString();
		strBfr.setLength(0);		
		
		strBfr.append(String.format("select userid, firstname, lastname, email,"
				+ " recoveryphrase, age, rolename, infotext from ((users inner "
				+ "join info on users.infoid == info.infoid) inner join roles "
				+ "on users.role == roles.roleid) WHERE email LIKE "
				+ "\"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            stmt.executeBatch();
            conn.commit();          
            
            ResultSet rs = stmt.executeQuery(query);
            

            //System.err.println(rs.getString("firstname").equals(fName));
            

            if(rs.getInt("userid") <= 0) { return false; };
    		if(!rs.getString("firstname").equals(fName)) { return false; };
    		if(!rs.getString("lastname").equals(lName)) { return false; };
    		if(!rs.getString("email").equals(email)) { return false; };
    		if(!rs.getString("recoveryphrase").equals(recPhr)) { return false; };
    		if(rs.getInt("age") <= 0 ) { return false; };
    		if(!rs.getString("rolename").equals(role)) { return false; };
    		if(!rs.getString("infotext").equals(pass)) { return false; };
            
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
		
		return true;
	}
	
	public boolean retrieveData(User user) {
		return false;
	}
	
	public boolean updateUserData (User user) {
		return false;
	}
	
	public boolean deleteUserData (User user) {
		return false;
	}
	
	public boolean authenticateUserData (User user) {
		return false;
	}
	
	

}
