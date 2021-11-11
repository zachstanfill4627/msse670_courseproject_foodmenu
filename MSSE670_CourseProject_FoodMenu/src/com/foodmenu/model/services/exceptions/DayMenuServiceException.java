package com.foodmenu.model.services.exceptions;

public class DayMenuServiceException extends Exception {

	private static final long serialVersionUID = 1234567L;
	
	public DayMenuServiceException(final String eMessage)  {
		super(eMessage);
	}
	
	public DayMenuServiceException(final String eMessage, final Throwable eNestedException)  {
		super(eMessage, eNestedException);
	}

}
