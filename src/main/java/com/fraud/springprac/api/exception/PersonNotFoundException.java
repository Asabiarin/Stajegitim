package com.fraud.springprac.api.exception;

import java.io.Serial;

public class PersonNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PersonNotFoundException(String message) {
        super(message);
    }
}
