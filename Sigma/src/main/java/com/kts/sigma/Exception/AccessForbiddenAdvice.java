package com.kts.sigma.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AccessForbiddenAdvice {

  @ResponseBody
  @ExceptionHandler(AccessForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String employeeNotFoundHandler(AccessForbiddenException ex) {
    return ex.getMessage();
  }
}