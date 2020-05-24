package com.sushil.numgen.util;

public class Constants {

    private Constants() {
        //throw new
    }


    //Validation Messages, It can be enhanced with placeholders to read it from property files.
    public static final String REG_EX_ONLY_NUM = "^[0-9]*$";
    public static final String REG_EX_UUID = "/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/";
    public static final String UUID_VAL_MSG = "Value of UUID is not correct.";
    public static final String STEP_VAL_MSG = "Value of Step can not be blank in request payload";
    public static final String GOAl_VAL_MSG = "Value of Goal can not be blank in request payload";
    public static final String NUM_VAL_MSG = "Only numbers are allowed";

    //
    public static final String OUTPUT_FILE_PATH = "/tmp/%s_output.txt";

    public static final String APPLICATION_JSON = "application/json";


    //Request Constants
    public static final String GOAL = "Goal";
    public static final String STEP = "Step";
    public static final String TASK = "task";
    public static final String RESULT = "result";
    public static final String ACTION = "action";
    public static final String GET_NUMLIST = "get_numlist";


}
