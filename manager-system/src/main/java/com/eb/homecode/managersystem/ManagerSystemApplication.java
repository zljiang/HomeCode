package com.eb.homecode.managersystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagerSystemApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ManagerSystemApplication.class, args);
        LOGGER.info("ManagerSystemApplication start...");
    }

}
