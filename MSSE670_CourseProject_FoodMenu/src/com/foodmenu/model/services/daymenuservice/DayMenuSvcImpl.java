package com.foodmenu.model.services.daymenuservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.model.services.fooditemservice.FoodItemSvcImpl;
import com.foodmenu.model.services.menuitemservice.MenuItemSvcImpl;

public class DayMenuSvcImpl implements IDayMenuService {
	
	private String connString = "jdbc:sqlite:data/FoodMenu.db";

	public DayMenuSvcImpl() {
	}

	public boolean createDayMenuData(DayMenu dayMenu) throws DayMenuServiceException {
		
		/** Localize Variables */
		Calendar date = dayMenu.getDate();
		ArrayList<MenuItem> menuList = dayMenu.getMenuList();
		int complexValue = dayMenu.getComplexityValue();
		double healthValue = dayMenu.getHealthValue();
		
		/** Date Formatter */
		String dateString = String.format("%d-%d-%d", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
				date.get(Calendar.DATE));
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert DayMenu Record into DayMenu Table */
		strBfr.append(String.format("INSERT INTO daymenu (date, "
				+ "complexityvalue, healthvalue) VALUES (\"%s\", %d, %.2f);", 
				dateString, complexValue, healthValue));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Insert FoodList Items into MealFoodList Table */
		ArrayList<String> menuListInsert = new ArrayList<String>();  
		menuList.forEach(item -> {
			strBfr.append(String.format("INSERT INTO daymeallist "
					+ "(daymenuid, menuitemid) VALUES (%s, %s",
					"(SELECT daymenuid FROM daymenu WHERE date == \"" + dateString + "\")",
					"(SELECT menuitemid FROM menuitems WHERE mealname LIKE \"" + item.getMealName() + "\"));"));
			menuListInsert.add(strBfr.toString());
			strBfr.setLength(0);
		});
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Insert Statements - Batch Style */
			stmt.addBatch(sql1);
            menuListInsert.forEach(item -> {
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

	public DayMenu retrieveDayMenuData(Calendar date) throws DayMenuServiceException, MenuItemServiceException, FoodItemServiceException {
		/** Localize Variables */
		int dayMenuID = 0;
		ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
		int complexValue = 0;
		double healthValue = 0.0;
		
		/** Date Formatter */
		String dateString = String.format("%d-%d-%d", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
				date.get(Calendar.DATE));
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from MenuItems Table */
		strBfr.append(String.format("SELECT * FROM daymenu WHERE date == "
				+ "\"%s\"", dateString));
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
            	dayMenuID = rs.getInt("daymenuid");
            	complexValue = rs.getInt("complexityvalue");
            	healthValue = rs.getDouble("healthvalue");
            } else {
            	return null;
            }
            
            /** SQL Statement 2, Select Record from MealFoodList Table */
    		strBfr.append(String.format("SELECT * FROM daymeallist WHERE daymenuid "
    				+ "== %d;", dayMenuID));
    		query = strBfr.toString();
    		strBfr.setLength(0);
    		
    		rs = stmt.executeQuery(query);
    		MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
    		while(rs.next()) {
    			menuList.add(menuImpl.retrieveMenuItemData(rs.getInt("menuitemid")));
    		}
            
		} catch (SQLException e) {
    			/** Error Output */
    	        System.err.println(e.getMessage());
    	        return null;
    	}
		
		DayMenu dayMenu = new DayMenu (date, menuList);
		
		return dayMenu;
	}

	public boolean updateDayMenuData(DayMenu dayMenu) throws DayMenuServiceException {
		deleteDayMenuData(dayMenu);
		if(!createDayMenuData(dayMenu)) {
			return false;
		}
		
		return true;
	}

	public boolean deleteDayMenuData(DayMenu dayMenu) throws DayMenuServiceException {
		Calendar date = dayMenu.getDate();
		
		/** Date Formatter */
		String dateString = String.format("%d-%d-%d", 
				date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
				date.get(Calendar.DATE));
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Delete Record from MenuItems Table */
		strBfr.append(String.format("DELETE FROM dayMenu WHERE date == "
				+ "\"%s\";", dateString));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** Database Cleanup 
		 * BUG -- SQLite Database Table Configured to ON DELETE CASCADE, however 
		 * cascade is not properly working, therefore manual DELETE Statements
		 * complete database cleanup tasks 
		 */
		String sql2 = "DELETE FROM daymeallist WHERE daymenuid NOT IN (SELECT "
				+ "DISTINCT daymenuid FROM daymenu);";

		/** SQL Statement 3, Select Record from MealItems Table */
		strBfr.append(String.format("SELECT * FROM daymenu WHERE date == "
				+ "\"%s\";", dateString));
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
