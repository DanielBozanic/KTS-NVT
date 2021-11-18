package com.kts.sigma.Exception;

import java.time.LocalDateTime;

public class DateNotValidException extends RuntimeException {

    public DateNotValidException(LocalDateTime date) {
        super("The given date is not valid " + date.toString());
    }
}
