package com.foodmenu.model.services.daymenuservice;

import java.util.Calendar;

import com.foodmenu.model.domain.DayMenu;

public interface IDayMenuService {
	
	public final String NAME = "IFoodItemService";
	
	public boolean createDayMenuData(DayMenu dayMenu);
	public DayMenu retrieveDayMenuData(Calendar date);
	public boolean updateDayMenuData(DayMenu dayMenu);
	public boolean deleteDayMenuData(DayMenu dayMenu);
}
