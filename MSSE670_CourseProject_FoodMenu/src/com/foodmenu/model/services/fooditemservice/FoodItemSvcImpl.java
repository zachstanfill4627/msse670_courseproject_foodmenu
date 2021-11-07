package com.foodmenu.model.services.fooditemservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodmenu.model.domain.FoodItem;

public class FoodItemSvcImpl implements IFoodItemService {
	
	private String connString = "jdbc:sqlite:data/FoodMenu.db";

	public FoodItemSvcImpl() {		
	}

	public boolean createFoodItemData(FoodItem foodItem) {
		
		/** Localize Variables */
		String foodName = foodItem.getFoodName();
		String category = foodItem.getCategory();
		int healthValue = foodItem.getHealthValue();
		int prepTime = foodItem.getPrepTime();
		ArrayList<String> recipe = foodItem.getRecipe();
		ArrayList<String> ingredients = foodItem.getIngredients();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Insert FoodItem Record into FoodItems Table */
		strBfr.append(String.format("INSERT INTO fooditems (foodname, category, "
				+ "healthvalue, preptime) VALUES (\"%s\", \"%s\", %d, %d);", 
				foodName, category, healthValue, prepTime));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** SQL Statement 2, Insert Recipe Step into Recipe Table */
		ArrayList<String> recipeInsert = new ArrayList<String>();  
		recipe.forEach(step -> {
			strBfr.append(String.format("INSERT INTO recipe (fooditemid, "
				+ "steptext) VALUES (%s, \"%s\");\n", 
				"(SELECT fooditemid FROM fooditems WHERE foodname LIKE \"" + foodName 
				+ "\" ORDER BY fooditemid DESC)",
				step)) ; 
				recipeInsert.add(strBfr.toString());
				strBfr.setLength(0);
		});
		
		/** SQL Statement 3, Insert Ingredients into Ingredients Table */
		ArrayList<String> ingredientsInsert = new ArrayList<String>();
		ingredients.forEach(ingredient -> {
			strBfr.append(String.format("INSERT "
				+ "INTO ingredients (fooditemid, ingredient) VALUES "
				+ "(%s, \"%s\");\n",
				"(SELECT fooditemid FROM fooditems WHERE foodname LIKE \"" + foodName 
				+ "\" ORDER BY fooditemid DESC)",
				ingredient));
			ingredientsInsert.add(strBfr.toString());
			strBfr.setLength(0);	
		});
		
		/** Connect to Database & Execute SQL Statements & Check Accuracy */
		try (Connection conn = DriverManager.getConnection(connString);
                Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);
            
			/** Execute SQL Insert Statements - Batch Style */
			stmt.addBatch(sql1);
            recipeInsert.forEach(step -> {
					try {
						stmt.addBatch(step);
					} catch (SQLException e) {
						e.printStackTrace();
					}
			});
            ingredientsInsert.forEach(ingredient -> {
				try {
					stmt.addBatch(ingredient);
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

	public FoodItem retrieveFoodItemData(String foodName) {
		
		/** Localize Variables */
		int foodItemID = 0;
		String category = "";
		int healthValue = 0;
		int prepTime = 0;
		ArrayList<String> recipe = new ArrayList<String>();
		ArrayList<String> ingredients = new ArrayList<String>();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from FoodItems Table */
		strBfr.append(String.format("SELECT * FROM fooditems WHERE foodname like "
				+ "\"%s\"", foodName));
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
            	foodItemID = rs.getInt("fooditemid");
            	foodName = rs.getString("foodname");
            	category = rs.getString("category");
            	healthValue = rs.getInt("healthvalue");
            	prepTime = rs.getInt("preptime");
            } else {
            	return null;
            }
            
            /** SQL Statement 2, Select Record from Recipe Table */
    		strBfr.append(String.format("SELECT * FROM recipe WHERE fooditemid "
    				+ "== %d ORDER BY recipeid;", foodItemID));
    		query = strBfr.toString();
    		strBfr.setLength(0);
    		
    		rs = stmt.executeQuery(query);
    		while(rs.next()) {
    			recipe.add(rs.getString("steptext"));
    		}
    		
    		/** SQL Statement 3, Select Record from Ingredients Table */
    		strBfr.append(String.format("SELECT * FROM ingredients WHERE fooditemid "
    				+ "== %d;", foodItemID));
    		query = strBfr.toString();
    		strBfr.setLength(0);
    		
    		rs = stmt.executeQuery(query);
    		while(rs.next()) {
    			ingredients.add(rs.getString("ingredient"));
    		}            
			
		} catch (SQLException e) {
			/** Error Output */
	        System.err.println(e.getMessage());
	        return null;
	    }
		
		/** Create FoodItem Object */
		FoodItem foodItem = new FoodItem(foodName, category, healthValue, prepTime,
				recipe, ingredients);
		
		/** If Successful, Return True */
		return foodItem;
	}
	
	public FoodItem retrieveFoodItemData(int foodItemID) {
		/** Localize Variables */
		String foodName = "";

		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Select Record from FoodItems Table */
		strBfr.append(String.format("SELECT * FROM fooditems WHERE fooditemid "
				+ "== %d", foodItemID));
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
            	foodName = rs.getString("foodname");
            } else {
            	return null;
            }
            
		} catch (SQLException e) {
			/** Error Output */
	        System.err.println(e.getMessage());
	        return null;
	    }
		
		FoodItem foodItem = retrieveFoodItemData(foodName);
				
		/** If Successful, Return True */
		return foodItem;
	}

	public boolean updateFoodItemData(FoodItem foodItem) {
		deleteFoodItemData(foodItem);
		if(!createFoodItemData(foodItem)) {
			return false;
		}
		
		return true;
	}

	public boolean deleteFoodItemData(FoodItem foodItem) {
		String foodName = foodItem.getFoodName();
		
		/** Re-usable String Buffer for SQL Statement instantiation */ 
		StringBuffer strBfr = new StringBuffer();
		
		/** SQL Statement 1, Delete Record from FoodItems Table */
		strBfr.append(String.format("DELETE FROM fooditems WHERE foodname like "
				+ "\"%s\";", foodName));
		String sql1 = strBfr.toString();
		strBfr.setLength(0);
		
		/** Database Cleanup 
		 * BUG -- SQLite Database Table Configured to ON DELETE CASCADE, however 
		 * cascade is not properly working, therefore manual DELETE Statements
		 * complete database cleanup tasks 
		 */
		String sql2 = "DELETE FROM recipe WHERE fooditemid NOT IN (SELECT "
				+ "DISTINCT fooditemid FROM fooditems);";
		String sql3 = "DELETE FROM ingredients WHERE fooditemid NOT IN (SELECT "
				+ "DISTINCT fooditemid FROM fooditems);";
		String sql4 = "DELETE FROM mealfoodlist WHERE fooditemid NOT IN (SELECT "
				+ "DISTINCT fooditemid FROM fooditems);";
		
		/** SQL Statement 4, Select Record from FoodItems Table */
		strBfr.append(String.format("SELECT * FROM fooditems WHERE foodname like "
				+ "\"%s\";", foodName));
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
			stmt.addBatch(sql4);
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
