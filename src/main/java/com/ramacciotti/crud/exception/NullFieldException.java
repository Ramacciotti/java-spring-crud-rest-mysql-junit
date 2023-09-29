package com.ramacciotti.crud.exception;

import java.io.Serial;

public class NullFieldException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NullFieldException(String message) {
        super(message);
    }

}
