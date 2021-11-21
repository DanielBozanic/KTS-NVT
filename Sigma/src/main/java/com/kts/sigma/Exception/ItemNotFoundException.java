package com.kts.sigma.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemNotFoundException extends RuntimeException {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ItemNotFoundException.class);
	
	  public ItemNotFoundException(Integer id, String objName) {
	    super("Could not find " + objName + " with id " + id);
	    LOGGER.error("Cound not find object with id {}", id);
	  }
}
