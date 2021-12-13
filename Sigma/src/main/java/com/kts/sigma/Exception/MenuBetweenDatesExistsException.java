package com.kts.sigma.Exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuBetweenDatesExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MenuBetweenDatesExistsException.class);
	
    public MenuBetweenDatesExistsException(LocalDateTime start, LocalDateTime end) {
        super("An active menu between " + start.toString() + "and " + end.toString() + " already exists!");
        LOGGER.error("Invalid start and end dates for menu");
    }
}
