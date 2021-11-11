package com.foodmenu.model.services.exceptions;

public class MenuItemServiceException extends Exception {

	private static final long serialVersionUID = 1234567L;
	
	public MenuItemServiceException(final String eMessage)  {
		super(eMessage);
	}
	
	public MenuItemServiceException(final String eMessage, final Throwable eNestedException)  {
		super(eMessage, eNestedException);
	}

}
