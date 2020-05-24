package com.sushil.numgen.model;

import com.sushil.numgen.exception.ExceptionCode;

public class ErrorResponse implements BaseResponse {

    private final int errorCode;
    private final String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.errorCode = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[errorCode=");
        builder.append(errorCode);
        builder.append(", message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }
}
