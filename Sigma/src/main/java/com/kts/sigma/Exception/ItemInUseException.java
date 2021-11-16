package com.kts.sigma.Exception;

public class ItemInUseException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ItemInUseException(String message) {
	    super(message);
	  }
		
}