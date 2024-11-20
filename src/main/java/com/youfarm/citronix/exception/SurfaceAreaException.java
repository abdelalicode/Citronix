package com.youfarm.citronix.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SurfaceAreaException extends RuntimeException {

    public SurfaceAreaException(String message) {
        super(message);
    }
}
