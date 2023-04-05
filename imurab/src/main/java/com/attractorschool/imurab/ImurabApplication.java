package com.attractorschool.imurab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ImurabApplication {

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(ImurabApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}

