package com.foodmenu.model.services.fooditemservice;

import java.sql.Connection;
import java.sql.DriverManager;
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
		
		
		
		return null;
	}

	public boolean updateFoodItemData() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteFoodItemData() {
		// TODO Auto-generated method stub
		return false;
	}

}
