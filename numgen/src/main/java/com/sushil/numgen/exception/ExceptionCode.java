package com.sushil.numgen.exception;

public enum ExceptionCode {

    PROCESSING_EXCEPTION(ErrorCode.ERR_1001, "There is an Error while processing the request."),
    BAD_REQUEST(ErrorCode.ERR_1002, "Please check query params and other inputs, This request is not as expected.");

    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
