/**
 * 
 */
package com.foodmenu.model.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Zach Stanfill
 *
 */
public class DayMenuTest {
	
	private final String TestClass = "DayMenu";

	@Test
	public void test() {
		
		String foodName = "Pot Roast";
		String category = "American";
		int healthValue = 4;
		int prepTime = 180;
		ArrayList<String> recipe = new ArrayList<String>() {{
			add("Preheat the oven to 275 degrees F.");
			add("Generously salt and pepper the chuck roast.");
			add("Heat the olive oil in large pot or Dutch oven over medium-high heat. Add the halved onions to the pot, browning them on both sides. Remove the onions to a plate.");
			add("Throw the carrots into the same very hot pot and toss them around a bit until slightly browned, about a minute or so. Reserve the carrots with the onions.");
			add("If needed, add a bit more olive oil to the very hot pot. Place the meat in the pot and sear it for about a minute on all sides until it is nice and brown all over. Remove the roast to a plate.");
			add("With the burner still on high, use either red wine or beef broth (about 1 cup) to deglaze the pot, scraping the bottom with a whisk. Place the roast back into the pot and add enough beef stock to cover the meat halfway.");
			add("Add in the onions and the carrots, along with the fresh herbs.");
			add("Put the lid on, then roast for 3 hours for a 3-pound roast. For a 4 to 5-pound roast, plan on 4 hours. The roast is ready when it's fall-apart tender.");
		}};
		ArrayList <String> ingredients = new ArrayList<String>() {{
			add("Potatoes");
			add("Carrots");
			add("Black Pepper");
			add("3 lbs chuck roast");
			add("Onions");
		}} ; 
		
		FoodItem item1 = new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients);
			
		recipe.clear();
		ingredients.clear();
		
		foodName = "House Style Salad";
		category = "Other";
		healthValue = 8;
		prepTime = 15;
		recipe = new ArrayList<String>() {{
			add("Wash Vegitables with water.");
			add("Air dry / Spin dry Vegitables.");
			add("Cut Tomatoes and Onions. Shave Carrots into large bowl.");
			add("Mix all vegitables in a large bowl.");
			add("Add Salad Dressing.");
		}};
		ingredients = new ArrayList<String>() {{
			add("Lettuce");
			add("Tomatoes");
			add("Onions");
			add("Carrots");
			add("Salad Dressing (Ranch)");
		}} ;
		
		FoodItem item2 = new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients);
		
		recipe.clear();
		ingredients.clear();
		
		foodName = "Ice Cream with Hot Fudge";
		category = "Other";
		healthValue = 2;
		prepTime = 5;
		recipe = new ArrayList<String>() {{
			add("Scoop Ice Cream into individual bowl.");
			add("Add Hot Fudge.");
		}};
		ingredients = new ArrayList<String>() {{
			add("Vanilla Ice Cream");
			add("Hot Fudge");
		}} ;
		
		FoodItem item3 = new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients);
		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>() {{
			add(item1);
			add(item2);
			add(item3);
		}}; 
		
		String menuName = "Pot Roast Dinner with Salad and Ice Cream";
				int complexityValue = 2; 
		
		MenuItem menuItem1 = new MenuItem(menuName, foodList, complexityValue);
				
		ArrayList<MenuItem> menuList = new ArrayList<MenuItem> () {{
			add(menuItem1);
			add(menuItem1);
			add(menuItem1);
		}};
		
		DayMenu dayMenu = new DayMenu(menuList);
		
		assertTrue ("menuItem1 validates",dayMenu.validate());
			System.out.println(TestClass + ".testValidate PASSED");
	}

}
