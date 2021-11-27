package com.foodmenu.model.services.menuitemservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.model.services.fooditemservice.FoodItemSvcImpl;

public class MenuItemSvcImpl implements IMenuItemService {
	
	private String connString = "jdbc:sqlite:data/FoodMenu.db";

	public MenuItemSvcImpl()  {
	}

	public boolean createMenuItemData(MenuItem menuItem) throws MenuItemServiceException {
		
		/** Localize Variables */
		String mealName = menuItem.getMealName();
		ArrayList<FoodItem> foodList = menuItem.getFoodList();
		int complexValue = menuItem.getComplexityValue();
		double healthValue = menuItem.getHealthValue();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert MenuItem Record into MenuItems Table */
		strBfr.append(String.format("INSERT INTO menuitems (mealname, "
				+ "complexityvalue, healthvalue) VALUES (\"%s\", %d, %.2f);", 
				mealName, complexValue, healthValue));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Insert FoodList Items into MealFoodList Table */
		ArrayList<String> foodListInsert = new ArrayList<String>();  
		foodList.forEach(item -> {
			strBfr.append(String.format("INSERT INTO mealfoodlist "
					+ "(menuitemid, fooditemid) VALUES (%s, %s",
					"(SELECT menuitemid FROM menuitems WHERE mealname LIKE \"" + mealName + "\")",
					"(SELECT fooditemid FROM fooditems WHERE foodname LIKE \"" + item.getFoodName() + "\"));"));
			foodListInsert.add(strBfr.toString());
			strBfr.setLength(0);
		});
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Insert Statements - Batch Style */
			stmt.addBatch(sql1);
            foodListInsert.forEach(item -> {
					try {
						stmt.addBatch(item);
					} catch (SQLException e) {
						e.printStackTrace();
					}
			});
            stmt.executeBatch();
            
            /** Commit Changes */ 
            conn.commit();
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

	public MenuItem retrieveMenuItemData(String mealName) throws MenuItemServiceException, FoodItemServiceException {
		/** Localize Variables */
		int menuItemID = 0;
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		int complexValue = 0;
		double healthValue = 0.0;
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from MenuItems Table */
		strBfr.append(String.format("SELECT * FROM menuitems WHERE mealname like "
				+ "\"%s\"", mealName));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
			
			/** Run SQL Query against FoodItems Table */
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) {
            	/** Assign Query Return to Variables */
            	menuItemID = rs.getInt("menuitemid");
            	mealName = rs.getString("mealname");
            	complexValue = rs.getInt("complexityvalue");
            	healthValue = rs.getDouble("healthvalue");
            } else {
            	return null;
            }
            
            /** SQL Statement 2, Select Record from MealFoodList Table */
    		strBfr.append(String.format("SELECT * FROM mealfoodlist WHERE menuitemid "
    				+ "== %d;", menuItemID));
    		query = strBfr.toString();
    		strBfr.setLength(0);
    		
    		rs = stmt.executeQuery(query);
    		FoodItemSvcImpl foodImpl = new FoodItemSvcImpl();
    		while(rs.next()) {
    			foodList.add(foodImpl.retrieveFoodItemData(rs.getInt("fooditemid")));
    		}
            
		} catch (SQLException e) {
    			/** Error Output */
    	        System.err.println(e.getMessage());
    	        return null;
    	}
		
		MenuItem menuItem = new MenuItem (mealName, foodList, complexValue);
		
		return menuItem;
	}
	
	public MenuItem retrieveMenuItemData(int menuItemID) throws MenuItemServiceException, FoodItemServiceException {
		/** Localize Variables */
		String mealName = "";
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from FoodItems Table */
		strBfr.append(String.format("SELECT * FROM menuitems WHERE menuitemid "
				+ "== %d", menuItemID));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
			
			/** Run SQL Query against FoodItems Table */
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) {
            	/** Assign Query Return to Variables */
            	mealName = rs.getString("mealname");
            } else {
            	return null;
            }
            
		} catch (SQLException e) {
			/** Error Output */
	        System.err.println(e.getMessage());
	        return null;
	    }
		
		MenuItem menuItem = retrieveMenuItemData(mealName);
				
		/** If Successful, Return True */
		return menuItem;
	}
	
	public ArrayList<MenuItem> retrieveAllMenuItemData () throws MenuItemServiceException, FoodItemServiceException {
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from FoodItems Table */
		strBfr.append(String.format("SELECT mealName FROM menuitems;"));
		String query = strBfr.toString();
		strBfr.setLength(0);
		
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {          
            
            /** Run SQL Query against Users Table */
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
            	menuItems.add(retrieveMenuItemData(rs.getString("mealname")));
            }            
            return menuItems;
            
		} catch (SQLException e) {
        	/** Error Output */
        	System.err.println(e.getMessage());
        	return null;
        }
	}

	public boolean updateMenuItemData(MenuItem menuItem) throws MenuItemServiceException {
		deleteMenuItemData(menuItem);
		if(!createMenuItemData(menuItem)) {
			return false;
		}
		
		return true;
	}

	public boolean deleteMenuItemData(MenuItem menuItem) throws MenuItemServiceException {
		String mealName = menuItem.getMealName();
	
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Delete Record from MenuItems Table */
		strBfr.append(String.format("DELETE FROM menuitems WHERE mealname like "
				+ "\"%s\";", mealName));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** Database Cleanup 
		 * BUG -- SQLite Database Table Configured to ON DELETE CASCADE, however 
		 * cascade is not properly working, therefore manual DELETE Statements
		 * complete database cleanup tasks 
		 */
		String sql2 = "DELETE FROM mealfoodlist WHERE menuitemid NOT IN (SELECT "
				+ "DISTINCT menuitemid FROM menuitems);";

		/** SQL Statement 3, Select Record from MealItems Table */
		strBfr.append(String.format("SELECT * FROM menuitems WHERE mealname like "
				+ "\"%s\";", mealName));
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
}
