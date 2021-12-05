package com.foodmenu.model.business.exceptions;

public class UserPrivilegesException extends Exception {

	private static final long serialVersionUID = 1234567L;
	
	public UserPrivilegesException(final String eMessage)  {
		super(eMessage);
	}
	
	public UserPrivilegesException(final String eMessage, final Throwable eNestedException)  {
		super(eMessage, eNestedException);
	}

}
