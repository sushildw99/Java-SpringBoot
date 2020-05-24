package com.sushil.numgen.exception;

public class NumgenException extends RuntimeException {
    private static final long serialVersionUID = 4670566892176569359L;

    private final int errorCode;
    private final String errorMessage;


    public NumgenException(String message, Throwable cause, int errorCode, String errorMessage) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
