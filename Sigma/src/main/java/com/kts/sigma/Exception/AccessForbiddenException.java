package com.kts.sigma.Exception;

public class AccessForbiddenException extends RuntimeException {

	  public AccessForbiddenException() {
	    super("Access Forbidden, Invalid Code");
	  }
	}
