package com.sushil.numgen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.sushil.numgen.util.Constants.RESULT;

public class ResponseResult implements BaseResponse {

    @JsonProperty(RESULT)
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[result=");
        builder.append(result);
        builder.append("]");
        return builder.toString();

    }
}
