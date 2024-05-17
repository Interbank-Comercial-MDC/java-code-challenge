package com.interbank.anti.fraud.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.interbank.anti.fraud")
public class AntiFraudApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AntiFraudApplication.class)
                .initializers()
                .run(args);
    }

}
