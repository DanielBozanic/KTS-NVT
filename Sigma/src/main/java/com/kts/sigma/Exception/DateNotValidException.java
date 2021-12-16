package com.kts.sigma.Exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateNotValidException extends RuntimeException {

	private static final Logger LOGGER=LoggerFactory.getLogger(RuntimeException.class);
	
    public DateNotValidException(LocalDateTime date) {
        super("The given date is not valid " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        LOGGER.error("Date not valid");
    }
}
