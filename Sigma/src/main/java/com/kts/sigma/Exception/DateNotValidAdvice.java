package com.kts.sigma.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DateNotValidAdvice {

    @ResponseBody
    @ExceptionHandler(DateNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //TODO promeniti kasnije httpStatus
    String dateNotValid(DateNotValidException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DateNotValidOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //TODO promeniti kasnije httpStatus
    String datesOrderNotValid(DateNotValidOrderException ex) {
        return ex.getMessage();
    }
}
