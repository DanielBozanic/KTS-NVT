package com.kts.sigma.Exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateNotValidOrderException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(DateNotValidOrderException.class);
	
    public DateNotValidOrderException(LocalDateTime startMonth, LocalDateTime endMonth) {
        super("The given start date " + startMonth.format(DateTimeFormatter.ISO_LOCAL_DATE) + " can't be after the given end date " + endMonth.format(DateTimeFormatter.ISO_LOCAL_DATE));
        LOGGER.error("Date not valid");
    }
}
