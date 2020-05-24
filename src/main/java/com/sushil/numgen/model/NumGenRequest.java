package com.sushil.numgen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.sushil.numgen.util.Constants.*;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public class NumGenRequest {


    @JsonProperty(GOAL)
    @NotBlank(message = GOAl_VAL_MSG)
    @Pattern(regexp = REG_EX_ONLY_NUM, message = NUM_VAL_MSG)
    private String goal;

    @JsonProperty(STEP)
    @NotBlank(message = STEP_VAL_MSG)
    @Pattern(regexp = REG_EX_ONLY_NUM, message = NUM_VAL_MSG)
    private String step;


    public int getGoalVal() {
        return Integer.parseInt(getGoal());
    }

    public int getStepVal() {
        return Integer.parseInt(getStep());
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[goal=");
        builder.append(goal);
        builder.append(", step=");
        builder.append(step);
        builder.append("]");
        return builder.toString();
    }
}
