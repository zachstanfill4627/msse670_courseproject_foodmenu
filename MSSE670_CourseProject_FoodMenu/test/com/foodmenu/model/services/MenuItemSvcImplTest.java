package com.foodmenu.model.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.foodmenu.model.domain.*;
import com.foodmenu.model.services.fooditemservice.FoodItemSvcImpl;
import com.foodmenu.model.services.menuitemservice.MenuItemSvcImpl;


public class MenuItemSvcImplTest {
	
	private final String TestClass = "MenuItemService";
	
	private FoodItemSvcImpl foodImpl = new FoodItemSvcImpl();
	
	private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();

	
	@Before
	public void setup() {
		
		String foodName = "Bacon";
		String category = "American";
		int healthValue = 1;
		int prepTime = 25;
		ArrayList<String> recipe = new ArrayList<String>() {{
			add("Turn Stove Burner on Med-High");
			add("Place Bacon in Pan");
			add("Cook until Desired Completeness");
		}};
		ArrayList <String> ingredients = new ArrayList<String>() {{
			add("pre-sliced bacon");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodName = "Scrambled Eggs";
		category = "American";
		healthValue = 1;
		prepTime = 15;
		recipe = new ArrayList<String>() {{
			add("Turn Stove Burner on Med-High");
			add("Crack Eggs in Pan");
			add("Cook until Desired Completeness");
		}};
		ingredients = new ArrayList<String>() {{
			add("Raw Eggs");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodName = "Toast";
		category = "American";
		healthValue = 1;
		prepTime = 3;
		recipe = new ArrayList<String>() {{
			add("Place Toast in Toaster");
			add("Set Toaster to desired level");
			add("Butter if needed");
		}};
		ingredients = new ArrayList<String>() {{
			add("Bread");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodList.forEach(foodItem -> foodImpl.createFoodItemData(foodItem));
	}
	
	@Test
	public void testMenuCRUD() {
		testMenuItemCreate();
		testMenuItemRetrieve();
		testMenuItemUpdate();
		testMenuItemDelete();
	}
	
	public void testMenuItemCreate() {
		MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
	
		String mealName = "Bacon, Eggs, and Toast";
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>() {{
			add(foodImpl.retrieveFoodItemData("Bacon"));
			add(foodImpl.retrieveFoodItemData("Scrambled Eggs"));
			add(foodImpl.retrieveFoodItemData("Toast"));
		}};
		int complexityValue = 4;
		
		MenuItem menuItem = new MenuItem(mealName, foodList, complexityValue);

		assertTrue ("testMealItem created", menuImpl.createMenuItemData(menuItem));
		   System.out.println(TestClass + ".testMealItemCreate PASSED");
	}
	
	public void testMenuItemRetrieve() {
		MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
		
		String mealName = "Bacon, Eggs, and Toast";
		
		assertTrue ("testMealItem retrieved", menuImpl.retrieveMenuItemData(mealName).validate());
		   System.out.println(TestClass + ".testMealItemRetrieve PASSED");	
	}
	
	
	public void testMenuItemUpdate() {
		MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
		
		String mealName = "Bacon, Eggs, and Toast";
		
		MenuItem menuItem = menuImpl.retrieveMenuItemData(mealName);
		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>() {{
			add(foodImpl.retrieveFoodItemData("Bacon"));
			add(foodImpl.retrieveFoodItemData("Cherrios Cereal"));
			add(foodImpl.retrieveFoodItemData("Toast"));
		}};
		int complexityValue = 2;
			
		menuItem.setFoodList(foodList);
		menuItem.setComplexityValue(complexityValue);
		
		assertTrue ("testMealItem updated", menuImpl.updateMenuItemData(menuItem));
		   System.out.println(TestClass + ".testMealItemUpdate PASSED");	
	}
	
	public void testMenuItemDelete() {
		MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
		
		String mealName = "Bacon, Eggs, and Toast";
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>() {{
			add(foodImpl.retrieveFoodItemData("Bacon"));
			add(foodImpl.retrieveFoodItemData("Scrambled Eggs"));
			add(foodImpl.retrieveFoodItemData("Toast"));
		}};
		int complexityValue = 4;
		
		MenuItem menuItem = new MenuItem(mealName, foodList, complexityValue);

		assertTrue ("testMealItem deleted", menuImpl.deleteMenuItemData(menuItem));
		   System.out.println(TestClass + ".testMealItemDelete PASSED");
	}
	
	@After
	public void cleanup() {
		foodList.forEach(food -> foodImpl.deleteFoodItemData(food));
	}
}
