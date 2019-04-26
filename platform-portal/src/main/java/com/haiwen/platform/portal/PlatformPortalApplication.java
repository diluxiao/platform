package com.haiwen.platform.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.haiwen.platform.*"})
public class PlatformPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformPortalApplication.class, args);
    }

}
