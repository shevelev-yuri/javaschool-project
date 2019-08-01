package com.tsystems.ecm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ECMException extends RuntimeException {

    public ECMException() {
    }

    public ECMException(String message) {
        super(message);
    }
}
