package com.kts.sigma.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER=LoggerFactory.getLogger(ItemExistsException.class);
	
	public ItemExistsException(String message) {
	    super(message);
        LOGGER.error(message);
	 }
}
