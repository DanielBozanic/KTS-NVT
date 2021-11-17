package com.kts.sigma.Exception;

public class ItemNotFoundException extends RuntimeException {

	  public ItemNotFoundException(Integer id, String objName) {
	    super("Could not find " + objName + " with id " + id);
	  }
	}
