package com.sushil.numgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumgenApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumgenApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NumgenApplication.class, args);
        LOGGER.info("************* NumgenApplication Started ******************");
    }


}
