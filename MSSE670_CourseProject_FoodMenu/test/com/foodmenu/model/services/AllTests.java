package com.foodmenu.model.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FoodItemSvcImplTest.class, MenuItemSvcImplTest.class, DayMenuSvcImplTest.class, UserSvcImplTest.class })
public class AllTests {

}
