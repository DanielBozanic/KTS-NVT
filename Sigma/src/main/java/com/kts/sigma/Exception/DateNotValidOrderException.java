package com.kts.sigma.Exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateNotValidOrderException extends RuntimeException{

	private static final Logger LOGGER=LoggerFactory.getLogger(DateNotValidOrderException.class);
	
	
    public DateNotValidOrderException(LocalDateTime startMonth, LocalDateTime endMonth) {
        super("The given start date " + startMonth.toString() + " can't be after the given end date" + startMonth.toString());
        LOGGER.error("Date not valid");
    }
}
