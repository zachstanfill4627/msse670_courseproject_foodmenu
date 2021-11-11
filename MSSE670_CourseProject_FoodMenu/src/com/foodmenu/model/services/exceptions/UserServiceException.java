package com.foodmenu.model.services.exceptions;

public class UserServiceException extends Exception {

	private static final long serialVersionUID = 1234567L;
	
	public UserServiceException(final String eMessage)  {
		super(eMessage);
	}
	
	public UserServiceException(final String eMessage, final Throwable eNestedException)  {
		super(eMessage, eNestedException);
	}

}
