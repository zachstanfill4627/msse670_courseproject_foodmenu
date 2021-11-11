package com.foodmenu.model.services;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.daymenuservice.DayMenuSvcImpl;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.model.services.fooditemservice.FoodItemSvcImpl;
import com.foodmenu.model.services.menuitemservice.MenuItemSvcImpl;

public class DayMenuSvcImplTest {
	
	private final String TestClass = "DayMenuService";
	
	private FoodItemSvcImpl foodImpl = new FoodItemSvcImpl();
	private MenuItemSvcImpl menuImpl = new MenuItemSvcImpl();
	
	private ArrayList<FoodItem> masterFoodList = new ArrayList<FoodItem>();
	private ArrayList<MenuItem> masterMenuList = new ArrayList<MenuItem>();
	
	private DayMenu dayMenu;
		
	@Before
	public void setup() {
		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
		
		String menuName = "";
		int complexityValue = 0;
		
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
		
		foodList.forEach(foodItem -> {
			try {
				foodImpl.createFoodItemData(foodItem);
			} catch (FoodItemServiceException e) {
				e.printStackTrace();
			}
			masterFoodList.add(foodItem);
		});
		
		menuName = "Bacon, Eggs, and Toast";
		complexityValue = 4;
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		foodList.clear();
		
		foodName = "Top Ramen Noodles";
		category = "Asian";
		healthValue = 1;
		prepTime = 5;
		recipe = new ArrayList<String>() {{
			add("Open Package (Noodles & Seasoning) into Bowl");
			add("Add 2 Cups Water to Bowl");
			add("Microwave 3:30 Minutes");
			add("Sir & Drain off excess water");
		}};
		ingredients = new ArrayList<String>() {{
			add("Top Ramen Noodles");
			add("water");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodList.forEach(foodItem -> {
			try {
				foodImpl.createFoodItemData(foodItem);
			} catch (FoodItemServiceException e) {
				e.printStackTrace();
			}
			masterFoodList.add(foodItem);
		});
		
		menuName = "Ramen Noodles";
		complexityValue = 1;
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		foodList.clear();
		
		foodName = "Steak";
		category = "American";
		healthValue = 3;
		prepTime = 30;
		recipe = new ArrayList<String>() {{
			add("Turn Stove Burner on Med-High");
			add("Place Steak in Pan");
			add("Cook until Desired Completeness");
		}};
		ingredients = new ArrayList<String>() {{
			add("steak");
			add("marinade");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodName = "Green Beans";
		category = "American";
		healthValue = 7;
		prepTime = 3;
		recipe = new ArrayList<String>() {{
			add("Open Green Beans Can and dump contents into bowl");
			add("Microwave 3-5 minutes");
			add("Remove from Microwave and Serve");
		}};
		ingredients = new ArrayList<String>() {{
			add("Canned Green Beans");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodName = "Baked Potato";
		category = "American";
		healthValue = 5;
		prepTime = 6;
		recipe = new ArrayList<String>() {{
			add("Place washed potatoes into seamer bin in microwave");
			add("Microwave 3-5 minutes");
			add("Remove from Microwave and Serve");
		}};
		ingredients = new ArrayList<String>() {{
			add("Potatoes");
		}} ;
		
		foodList.add(new FoodItem(foodName, category, healthValue, 
				prepTime, recipe, ingredients));
			
		recipe.clear();
		ingredients.clear();
		
		foodList.forEach(foodItem -> {
			try {
				foodImpl.createFoodItemData(foodItem);
			} catch (FoodItemServiceException e) {
				e.printStackTrace();
			}
			masterFoodList.add(foodItem);
		});
		
		menuName = "Steak, Green Beans, and Potatoes";
		complexityValue = 3;
		
		menuList.add(new MenuItem(menuName, foodList, complexityValue));
		
		foodList.clear();
		
		menuList.forEach(menuItem -> {
			try {
				menuImpl.createMenuItemData(menuItem);
			} catch (MenuItemServiceException e) {
				e.printStackTrace();
			}
			masterMenuList.add(menuItem);
		}); 

		Calendar date = Calendar.getInstance();
		date.set(2021, 11, 06);
		
		dayMenu = new DayMenu(date, menuList);
		
		/**
		//Serialize sender/receiver data into new Envelope1.obj		
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data/testObjectFiles/dayMenu_20211106.obj"));
			os.writeObject(dayMenu);
			os.close();
		} catch (IOException ex) {
		  ex.printStackTrace();
		}
		*/
		
	}
	
	@Test
	public void testDayMenuCrud() throws DayMenuServiceException, MenuItemServiceException, FoodItemServiceException {
		testDayMenuCreate();
		testDayMenuRetrieve();
		testDayMenuUpdate();
		testDayMenuDelete();
	}
	
	public void testDayMenuCreate() throws DayMenuServiceException {
		DayMenuSvcImpl dayMenuImpl = new DayMenuSvcImpl();
		
		assertTrue ("dayMenu Created", dayMenuImpl.createDayMenuData(dayMenu));
			System.out.println(TestClass + ".testDayMenuCreate PASSED");
	}
	
	public void testDayMenuRetrieve() throws DayMenuServiceException {
		DayMenuSvcImpl dayMenuImpl = new DayMenuSvcImpl();
		
		Calendar date = Calendar.getInstance();
		date.set(2021, 11, 06);		
		
		assertTrue ("dayMenu Retrieved", dayMenuImpl.retrieveDayMenuData(date).validate());
			System.out.println(TestClass + ".testDayMenuRetrieve PASSED");	
	}
	
	public void testDayMenuUpdate() throws DayMenuServiceException, MenuItemServiceException, FoodItemServiceException {
		DayMenuSvcImpl dayMenuImpl = new DayMenuSvcImpl();
		
		Calendar date = Calendar.getInstance();
		date.set(2021, 11, 06);		
		
		DayMenu dayMenuLocal = dayMenuImpl.retrieveDayMenuData(date);
		
		ArrayList<MenuItem> mealList = dayMenuLocal.getMenuList();
		
		mealList.remove(1);
		
		mealList.add(menuImpl.retrieveMenuItemData(2));
		
		dayMenuLocal.setMenuList(mealList);		
				
		assertTrue ("testDayMenu updated", dayMenuImpl.updateDayMenuData(dayMenuLocal));
		   System.out.println(TestClass + ".testDayMenuUpdate PASSED");
	}
	
	public void testDayMenuDelete() throws DayMenuServiceException {
		DayMenuSvcImpl dayMenuImpl = new DayMenuSvcImpl();

		assertTrue ("dayMenu Deleted", dayMenuImpl.deleteDayMenuData(dayMenu));
			System.out.println(TestClass + ".testDayMenuDelete PASSED");	
	}
	
	@After
	public void cleanup() {
		masterMenuList.forEach(menu -> {
			try {
				menuImpl.deleteMenuItemData(menu);
			} catch (MenuItemServiceException e) {

				e.printStackTrace();
			}
		});
		masterFoodList.forEach(food -> {
			try {
				foodImpl.deleteFoodItemData(food);
			} catch (FoodItemServiceException e) {
				e.printStackTrace();
			}
		});
	}
}
