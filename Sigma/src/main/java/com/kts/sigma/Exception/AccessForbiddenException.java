package com.kts.sigma.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessForbiddenException extends RuntimeException {

	private static final Logger LOGGER=LoggerFactory.getLogger(RuntimeException.class);
	
	  public AccessForbiddenException() {
	    super("Access Forbidden, Invalid Code");
	    LOGGER.error("Access Forbidden, Invalid Code");
	  }
	}
