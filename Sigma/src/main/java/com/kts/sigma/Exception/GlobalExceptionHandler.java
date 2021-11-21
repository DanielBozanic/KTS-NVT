package com.kts.sigma.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RuntimeException.class);
	
	@ExceptionHandler(RuntimeException.class)
    public void getExceptionPage(Exception e) {
            LOGGER.error(e.toString());
    }
}
