package com.foodmenu.model.services.userservice;

import java.sql.*;
import java.util.ArrayList;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;

public class UserSvcImpl implements IUserService {

	private String connString = "jdbc:sqlite:data/FoodMenu.db";
	
	public UserSvcImpl() {
		
	}

	public boolean createUserData(User user, String salt) throws UserServiceException {
		
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
		
		/** SQL Statement 2, Insert Password Record into Info Table */
		strBfr.append(String.format("INSERT INTO salt (salttext) VALUES (\"%s\");", 
				salt));
		String sql2 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Insert User data into Users Table */
		strBfr.append(String.format("INSERT INTO users (firstname, lastname, "
				+ "email, recoveryphrase, age, role, infoid, saltid) "
				+ "VALUES (\"%s\", \"%s\",\"%s\", \"%s\", %d, %s, %s, %s);",
				fName, lName, email, recPhr, age, 
				"(SELECT roleid FROM roles WHERE rolename == \"" + role + "\")", 
				"(SELECT MAX(infoid) FROM info LIMIT 1)",
				"(SELECT MAX(saltid) FROM salt LIMIT 1)"));
		String sql3 = strBfr.toString();
		strBfr.setLength(0);		
		
		
		/** SQL Statement 3, Query database - Check User Data */
		strBfr.append(String.format("SELECT userid, firstname, lastname, email, recoveryphrase, age, rolename, infotext, salttext "
				+ "FROM users "
				+ "INNER JOIN info ON users.infoid == info.infoid "
				+ "INNER JOIN salt ON users.saltid == salt.saltid "
				+ "INNER JOIN roles ON users.role == roles.roleid "
				+ "WHERE email LIKE \"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
				
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Insert Statements - Batch Style */
			stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            stmt.addBatch(sql3);
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
    		if(!rs.getString("salttext").equals(salt)) { return false; };
            
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
	
	public User retrieveUserData(String email) throws UserServiceException {
		
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
            	return null;
            }
                        
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
        	return null;
        }
		
		/** Create User Object */
		User user = new User(fName, lName, email, pass, recPhr, age, role);
		
		/** Return User Object */ 
		return user;
	}
	
	public String retrieveUserSaltData(String email) {
		String salt = "";
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from Users Table */
		strBfr.append(String.format("SELECT salttext FROM users "
				+ "INNER JOIN salt ON users.saltid == salt.saltid "
				+ "WHERE email LIKE \"%s\";", email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {          
            
            /** Run SQL Query against Users Table */
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) {            	
	            /** Assign Query Return to variables */
	            salt = rs.getString("salttext");
            } else {
            	return null;
            }
                        
    		/** Close Database Connection */
            conn.close();
        } catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
        	return null;
        }
		
		return salt;
	}
	
	public ArrayList<User> retrieveAllUserData() {
		ArrayList<User> users = new ArrayList<User>();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from Users Table */
		strBfr.append(String.format("SELECT firstname, lastname, email, recoveryphrase, "
				+ "age, rolename FROM users INNER JOIN roles ON "
				+ "users.role == roles.roleid;"));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {          
            
            /** Run SQL Query against Users Table */
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
            	users.add(new User(rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("recoveryphrase"), rs.getInt("age"), rs.getString("rolename")));
            }
            
            return users;
		
		} catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
        	return users;
        }
	}
	
	public boolean updateUserData (User user) throws UserServiceException {
		
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
	
	public boolean updateUserPasswordData (User user) throws UserServiceException {
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
				+ "(SELECT MAX(infoid) FROM info LIMIT 1)"
				+ "WHERE email LIKE \"%s\";", email));
		String sql2 = strBfr.toString();
		strBfr.setLength(0);
		
		/** Database Cleanup 
		 * BUG -- SQLite Database Table Configured to ON DELETE CASCADE, however 
		 * cascade is not properly working, therefore manual DELETE Statements
		 * complete database cleanup tasks
		 */
		String sql3 = "DELETE FROM info WHERE infoid NOT IN (SELECT "
				+ "DISTINCT infoid FROM users);";
		
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
            stmt.addBatch(sql3);
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
	
	public boolean resetUserPasswordData(String email, int age, String recPhrase) throws UserServiceException {
		User user = retrieveUserData(email);
		
		if (user == null) {
			return false;
		}
		
		if (recPhrase.equals(user.getRecoveryPhrase()) && age == user.getAge()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteUserData (User user) throws UserServiceException {
		/** Localize Variables */
		String email = user.getEmailAddress();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
					
		/** SQL Statement 1, Delete User data in Users Table */
		strBfr.append(String.format("DELETE FROM users where email like \"%s\";", 
				email));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Update User data in Users Table */
		strBfr.append(String.format("SELECT * FROM users where email like \"%s\";", 
				email));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Database Cleanup 
		 * BUG -- SQLite Database Table Configured to ON DELETE CASCADE, however 
		 * cascade is not properly working, therefore manual DELETE Statements
		 * complete database cleanup tasks
		 */ 
		String sql2 = "DELETE FROM info WHERE infoid NOT IN (SELECT "
				+ "DISTINCT infoid FROM users);";
		String sql3 = "DELETE FROM salt WHERE saltid NOT IN (SELECT "
				+ "DISTINCT saltid FROM users);";
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Statements - Batch Style */
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
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
	
	public boolean authenticateUserData (String email, String password) throws UserServiceException {	
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
