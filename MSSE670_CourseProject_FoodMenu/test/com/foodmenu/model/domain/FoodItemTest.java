/**
 * 
 */
package com.foodmenu.model.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;


/**
 * @author Zach Stanfill
 *
 */
public class FoodItemTest {
	
	private final String TestClass = "FoodItem";
	
	private String foodName = "Pot Roast";
	private String category = "American";
	private int healthValue = 4;
	private int prepTime = 180;
	private ArrayList<String> recipe = new ArrayList<String>() {{
		add("Preheat the oven to 275 degrees F.");
		add("Generously salt and pepper the chuck roast.");
		add("Heat the olive oil in large pot or Dutch oven over medium-high heat. Add the halved onions to the pot, browning them on both sides. Remove the onions to a plate.");
		add("Throw the carrots into the same very hot pot and toss them around a bit until slightly browned, about a minute or so. Reserve the carrots with the onions.");
		add("If needed, add a bit more olive oil to the very hot pot. Place the meat in the pot and sear it for about a minute on all sides until it is nice and brown all over. Remove the roast to a plate.");
		add("With the burner still on high, use either red wine or beef broth (about 1 cup) to deglaze the pot, scraping the bottom with a whisk. Place the roast back into the pot and add enough beef stock to cover the meat halfway.");
		add("Add in the onions and the carrots, along with the fresh herbs.");
		add("Put the lid on, then roast for 3 hours for a 3-pound roast. For a 4 to 5-pound roast, plan on 4 hours. The roast is ready when it's fall-apart tender.");
	}};
	private ArrayList <String> ingredients = new ArrayList<String>() {{
		add("Potatoes");
		add("Carrots");
		add("Black Pepper");
		add("3 lbs chuck roast");
		add("Onions");
	}} ;
	
	private FoodItem item1 = new FoodItem(foodName, category, healthValue, 
			prepTime, recipe, ingredients);

	@Test
	public void testValidate() { 
		assertTrue ("item1 validates",item1.validate());
			System.out.println(TestClass + ".testValidate PASSED");
	}
	
	// Comment @Test Line to Avoid Running This Test
	@Test
	public void testFoodItemToString() {
		System.out.println("####################TestMethod: " + 
				Thread.currentThread().getStackTrace()[1].getMethodName() + 
				" Begin####################");
		
		System.out.println(item1.toString());
		
		System.out.println("####################TestMethod: " + 
				Thread.currentThread().getStackTrace()[1].getMethodName() + 
				" End######################");
	}
	
	// Comment @Test Line to Avoid Running This Test
	@Test
	public void testFoodItemToSummaryString() {
		System.out.println("####################TestMethod: " + 
				Thread.currentThread().getStackTrace()[1].getMethodName() + 
				" Begin####################");
		
		System.out.println(item1.toSummaryString());
		
		System.out.println("####################TestMethod: " + 
				Thread.currentThread().getStackTrace()[1].getMethodName() + 
				" End######################");		
	}
}
