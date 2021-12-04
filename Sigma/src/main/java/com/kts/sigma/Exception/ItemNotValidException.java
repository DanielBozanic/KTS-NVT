package com.kts.sigma.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemNotValidException extends RuntimeException {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ItemNotFoundException.class);
	
	  public ItemNotValidException(Integer id, String objName, String atribute) {
	    super(objName + " with id " + id + " has a non valid atribute " + atribute);
	    LOGGER.error("Cound not find object with id {}", id);
	  }

}
