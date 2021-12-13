package com.kts.sigma.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PastDateAdvice {

	  @ResponseBody
	  @ExceptionHandler(PastDateException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String pastDateHandler(PastDateException ex) {
	    return ex.getMessage();
	  }
}
