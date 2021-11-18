package com.kts.sigma.Exception;

import java.time.LocalDateTime;

public class DateNotValidOrderException extends RuntimeException{

    public DateNotValidOrderException(LocalDateTime startMonth, LocalDateTime endMonth) {
        super("The given start date " + startMonth.toString() + " can't be after the given end date" + startMonth.toString());
    }
}
