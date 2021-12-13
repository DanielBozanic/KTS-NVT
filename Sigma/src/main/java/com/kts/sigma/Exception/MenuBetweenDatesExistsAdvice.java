package com.kts.sigma.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MenuBetweenDatesExistsAdvice {

	  @ResponseBody
	  @ExceptionHandler(MenuBetweenDatesExistsException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String menuBetweenDatesExistsHandler(MenuBetweenDatesExistsException ex) {
	    return ex.getMessage();
	  }
}
