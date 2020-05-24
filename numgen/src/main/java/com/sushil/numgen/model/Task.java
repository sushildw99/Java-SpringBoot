package com.sushil.numgen.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import static com.sushil.numgen.util.Constants.TASK;

@Component
public class Task implements BaseResponse {

    @JsonProperty(TASK)
    private String task;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[task=");
        builder.append(task);
        builder.append("]");
        return builder.toString();

    }

}
