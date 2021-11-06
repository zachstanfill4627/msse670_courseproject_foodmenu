package com.foodmenu.model.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.services.fooditemservice.FoodItemSvcImpl;

public class FoodItemSvcImplTest {

	private final String TestClass = "FoodItemService";
	
	@Test
	public void testFoodItemCreate() {
		FoodItemSvcImpl impl = new FoodItemSvcImpl();
		
		String foodName = "meatloaf";
		String category = "American";
		int healthValue = 3;
		int prepTime = 90;
		ArrayList<String> recipe = new ArrayList<String>() {{
			add("Preheat oven to 350 degrees F (175 degrees C).");
			add("In a large bowl, combine the beef, egg, onion, milk and bread OR cracker crumbs. Season with salt and pepper to taste and place in a lightly greased 9x5-inch loaf pan, or form into a loaf and place in a lightly greased 9x13-inch baking dish.");
			add("In a separate small bowl, combine the brown sugar, mustard and ketchup. Mix well and pour over the meatloaf.");
			add("Bake at 350 degrees F (175 degrees C) for 1 hour.");
		}};
		ArrayList<String> ingredients = new ArrayList<String>() {{
			add("ground beef");
			add("bread crumbs");
			add("chopped onions");
			add("salt & pepper");
			add("1 egg");
			add("1 cup milk");
		}};
		
		FoodItem foodItem = new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients);
		
		assertTrue ("testUser created", impl.createFoodItemData(foodItem));
		   System.out.println(TestClass + ".testFoodItemCreate PASSED");
		
	}

}
