package com.foodmenu.model.services.userservice;

import java.sql.*;
import com.foodmenu.model.domain.User;

public class UserSvcImpl implements IUserService {

	private String connString = "jdbc:sqlite:data/FoodMenu.db";
	
	public UserSvcImpl() {
		
	}

	public boolean createUserData(User user) {
		
		/** Localize Variables */
		String fName = user.getFirstName();
		String lName = user.getLastName();
		String email = user.getEmailAddress();
		String recPhr = user.getRecoveryPhrase();
		int age = user.getAge();
		String role = user.getRole();
		String pass = user.getPassword();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert Password Record into Info Table */
		strBfr.append(String.format("INSERT INTO info (infotext) VALUES (\"%s\");", 
				pass));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Insert User data into Users Table */
		strBfr.append(String.format("INSERT INTO users (firstname, lastname, "
				+ "email, recoveryphrase, age, role, infoid) "
				+ "VALUES (\"%s\", \"%s\",\"%s\", \"%s\", %d, %s, %s);",
				fName, lName, email, recPhr, age, 
				"(select roleid from roles where rolename == \"" + role + "\")", 
				"(select last_insert_rowid() from info limit 1)"));
		String sql2 = strBfr.toString();
		strBfr.setLength(0);		
		
		/** SQL Statement 3, Query database - Check User Data */
		strBfr.append(String.format("SELECT userid, firstname, lastname, email,"
				+ " recoveryphrase, age, rolename, infotext FROM ((users INNER "
				+ "JOIN info ON users.infoid == info.infoid) INNER JOIN roles "
				+ "ON users.role == roles.roleid) WHERE email LIKE "
				+ "\"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Insert Statements - Batch Style */
			stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            stmt.executeBatch();
            
            /** Commit Changes */ 
            conn.commit();          
            
            /** Run SQL Query against newly added record */
            ResultSet rs = stmt.executeQuery(query);

            /** Verify Userdata in database matches User Object */
            if(rs.getInt("userid") <= 0) { return false; };
    		if(!rs.getString("firstname").equals(fName)) { return false; };
    		if(!rs.getString("lastname").equals(lName)) { return false; };
    		if(!rs.getString("email").equals(email)) { return false; };
    		if(!rs.getString("recoveryphrase").equals(recPhr)) { return false; };
    		if(rs.getInt("age") <= 0 ) { return false; };
    		if(!rs.getString("rolename").equals(role)) { return false; };
    		if(!rs.getString("infotext").equals(pass)) { return false; };
            
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
            return false;
        }
		
		/** If Successful, Return True */
		return true;
	}
	
	public User retrieveUserData(String email) {
		
		/** Localize Variables */
		String fName = "";
		String lName = "";
		String recPhr = "";
		int age = 0;
		String role = ""; 
		String pass = "";
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from Users Table */
		strBfr.append(String.format("SELECT userid, firstname, lastname, email,"
				+ " recoveryphrase, age, rolename, infotext FROM ((users INNER "
				+ "JOIN info ON users.infoid == info.infoid) INNER JOIN roles "
				+ "ON users.role == roles.roleid) WHERE email LIKE "
				+ "\"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {          
            
            /** Run SQL Query against Users Table */
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) {            	
	            /** Assign Query Return to variables */
	            fName = rs.getString("firstname");
	            lName = rs.getString("lastname");
	            recPhr = rs.getString("recoveryphrase");
	            age = rs.getInt("age");
	            role = rs.getString("rolename"); 
	            pass = rs.getString("infotext");
            } else {
            	User user = new User();
            	return user;
            }
                        
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
        
        	/** Create Default User (Blank) Object */
        	User user = new User();
        
        	return user;
        }
		
		/** Create User Object */
		User user = new User(fName, lName, email, pass, recPhr, age, role);
		
		/** Return User Object */ 
		return user;
	}
	
	public boolean updateUserData (User user) {
		
		/** Localize Variables */
		String fName = user.getFirstName();
		String lName = user.getLastName();
		String email = user.getEmailAddress();
		String recPhr = user.getRecoveryPhrase();
		int age = user.getAge();
		String role = user.getRole();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
					
		/** SQL Statement 1, Update User data in Users Table */
		strBfr.append(String.format("UPDATE users SET firstname = '%s', "
				+ "lastname = '%s', recoveryphrase = '%s', age = %d, role = %s"
				+ "WHERE email = \"%s\";",
				fName, lName, recPhr, age,
				"(SELECT roleid FROM roles WHERE rolename == \"" + role + "\")",
				email));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);		
		
		/** SQL Statement 2, Query database - Check User Data */
		strBfr.append(String.format("SELECT userid, firstname, lastname, email,"
				+ " recoveryphrase, age, rolename FROM (users INNER "
				+ "JOIN roles ON users.role == roles.roleid) WHERE email LIKE "
				+ "\"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Update Statements - Batch Style */
			stmt.addBatch(sql1);
            stmt.executeBatch();
            
            /** Commit Changes */ 
            conn.commit();          
            
            /** Run SQL Query against modified record */
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()) {
	            /** Verify Userdata in database matches User Object */
	            if(rs.getInt("userid") <= 0) { return false; };
	    		if(!rs.getString("firstname").equals(fName)) { return false; };
	    		if(!rs.getString("lastname").equals(lName)) { return false; };
	    		if(!rs.getString("email").equals(email)) { return false; };
	    		if(!rs.getString("recoveryphrase").equals(recPhr)) { return false; };
	    		if(rs.getInt("age") <= 0 ) { return false; };
	    		if(!rs.getString("rolename").equals(role)) { return false; };
            } else {
            	return false;
            }
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
            return false;
        }
		
		/** If Successful, Return True */
		return true;
	}
	
	public boolean updateUserPasswordData (User user) {
		/** Localize Variables */
		String email = user.getEmailAddress();
		String pass = user.getPassword();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert Password Record into Info Table */
		strBfr.append(String.format("INSERT INTO info (infotext) VALUES (\"%s\");", 
				pass));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Update User data in Users Table */
		strBfr.append(String.format("UPDATE users SET infoid = "
				+ "(SELECT last_insert_rowid() from info limit 1)"
				+ "WHERE email LIKE \"%s\"", email));
		String sql2 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 3, Query database - Check User Data */
		strBfr.append(String.format("SELECT email, infotext FROM users INNER "
				+ "JOIN info ON users.infoid == info.infoid WHERE email LIKE "
				+ "\"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Statements - Batch Style */
			stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            stmt.executeBatch();
            
            /** Commit Changes */ 
            conn.commit();          
            
            /** Run SQL Query against newly added record */
            ResultSet rs = stmt.executeQuery(query);

            /** Verify Userdata in database matches User Object */
    		if(!rs.getString("email").equals(email)) { return false; };
    		if(!rs.getString("infotext").equals(pass)) { return false; };
            
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
            return false;
        }
		
		/** If Successful, Return True */
		return true;
	}
	
	public boolean deleteUserData (User user) {
		/** Localize Variables */
		String email = user.getEmailAddress();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
					
		/** SQL Statement 1, Delete User data in Users Table */
		strBfr.append(String.format("DELETE FROM users where email like \"%s\"", 
				email));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 1, Update User data in Users Table */
		strBfr.append(String.format("SELECT * FROM users where email like \"%s\"", 
				email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Statements - Batch Style */
			stmt.addBatch(sql1);
            stmt.executeBatch();
            
            /** Commit Changes */ 
            conn.commit();          
            
            /** Run SQL Query against record */
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) { return false; };
            
            /** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
            return false;
        }
		
		/** If Successful, Return True */
		return true;
	}
	
	public boolean authenticateUserData (String email, String password) {
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert Password Record into Info Table */
		strBfr.append(String.format("SELECT infotext FROM users INNER JOIN info"
				+ " ON users.infoid == info.infoid WHERE email like \"%s\";", 
				email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
            
            /** Run SQL Query against modified record */
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
            	/** Verify Userdata in database matches User Object */
            	if(!rs.getString("infotext").equals(password)) { 
            		return false; 
            	};
            } else { 
            	return false;
            }
            
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
            return false;
        }
		
		/** If Successful, Return True */
		return true;
	}

}
