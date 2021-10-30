/**
 * 
 */
package com.foodmenu.model.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * @author zach
 *
 */
public class ScheduledDayTest {
	
	private final String TestClass = "DayMenu";

	private DayMenu dayMenu;

	@Before
	public void setup() {
		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		ArrayList<MenuItem> menuList = new ArrayList<MenuItem> ();
		String menuName = "";
		int complexityValue = 0;
		String foodName = "";
		String category = "";
		int healthValue = 0;
		int prepTime = 0;
		ArrayList<String> recipe = new ArrayList<String>();
		ArrayList <String> ingredients = new ArrayList<String>();
		
		foodName = "Cherrios Cereal";
		category = "Other";
		healthValue = 3;
		prepTime = 5;
		recipe = new ArrayList<String>() {{
			add("Pour Cereal into Bowl.");
			add("Pour Milk into Bowl.");
		}};
		ingredients = new ArrayList<String>() {{
			add("Cereal");
			add("Milk");
		}};
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
		
		menuName = "Cherrios Breakfast";
		complexityValue = 2;
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		foodList.clear();
		
		foodName = "McDonalds Cheeseburger";
		category = "American";
		healthValue = 1;
		prepTime = 0;
		recipe = new ArrayList<String>() {{
			add("Drive to McDonalds");
			add("Order Cheeseburger");
			add("Pay at 1st Window");
			add("Collect food at second window");
		}};
		ingredients = new ArrayList<String>() {{
			add("Money");
			add("Car+gas");
		}};
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
		
		foodName = "McDonalds Fries";
		category = "American";
		healthValue = 1;
		prepTime = 0;
		recipe = new ArrayList<String>() {{
			add("Drive to McDonalds");
			add("Order Fries");
			add("Pay at 1st Window");
			add("Collect food at second window");
		}};
		ingredients = new ArrayList<String>() {{
			add("Money");
			add("Car+gas");
		}};
		
		menuName = "McDonalds Burger & Fries";
		complexityValue = 1;
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		foodList.clear();		
		
		
		foodName = "Pot Roast";
		category = "American";
		healthValue = 4;
		prepTime = 180;
		recipe = new ArrayList<String>() {{
			add("Preheat the oven to 275 degrees F.");
			add("Generously salt and pepper the chuck roast.");
			add("Heat the olive oil in large pot or Dutch oven over medium-high heat. Add the halved onions to the pot, browning them on both sides. Remove the onions to a plate.");
			add("Throw the carrots into the same very hot pot and toss them around a bit until slightly browned, about a minute or so. Reserve the carrots with the onions.");
			add("If needed, add a bit more olive oil to the very hot pot. Place the meat in the pot and sear it for about a minute on all sides until it is nice and brown all over. Remove the roast to a plate.");
			add("With the burner still on high, use either red wine or beef broth (about 1 cup) to deglaze the pot, scraping the bottom with a whisk. Place the roast back into the pot and add enough beef stock to cover the meat halfway.");
			add("Add in the onions and the carrots, along with the fresh herbs.");
			add("Put the lid on, then roast for 3 hours for a 3-pound roast. For a 4 to 5-pound roast, plan on 4 hours. The roast is ready when it's fall-apart tender.");
		}};
		ingredients = new ArrayList<String>() {{
			add("Potatoes");
			add("Carrots");
			add("Black Pepper");
			add("3 lbs chuck roast");
			add("Onions");
		}} ; 
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
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
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
		
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
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));		
		
		menuName = "Pot Roast Dinner with Salad and Ice Cream";
		complexityValue = 6; 
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		dayMenu = new DayMenu(menuList);
	}
	
	@Test
	public void testValidate() {
		Calendar date = Calendar.getInstance();
		date.set(2021, 10, 29);
		
		ScheduledDay scheduledDay = new ScheduledDay(date, dayMenu);
		
		assertTrue ("scheduledDay validates",scheduledDay.validate());
			System.out.println(TestClass + ".testValidate PASSED");
	}

}
