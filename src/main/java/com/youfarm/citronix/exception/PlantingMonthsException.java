package com.youfarm.citronix.exception;

import lombok.Getter;

public class PlantingMonthsException extends RuntimeException {

    @Getter
    private final String field;
    private final String message;

    public PlantingMonthsException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
