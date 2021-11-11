package com.foodmenu.model.business.exceptions;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
@SuppressWarnings("serial")
public class ServiceLoadException extends Exception {
	
	public ServiceLoadException(final String svcMessage, final Throwable svcNestedException)
    {
        super(svcMessage, svcNestedException);
    }

}
