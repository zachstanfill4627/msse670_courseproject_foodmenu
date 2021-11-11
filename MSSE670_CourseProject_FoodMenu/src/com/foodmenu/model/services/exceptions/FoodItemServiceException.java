package com.foodmenu.model.services.exceptions;

public class FoodItemServiceException extends Exception {

	private static final long serialVersionUID = 1234567L;
	
	public FoodItemServiceException(final String eMessage)  {
		super(eMessage);
	}
	
	public FoodItemServiceException(final String eMessage, final Throwable eNestedException)  {
		super(eMessage, eNestedException);
	}

}
