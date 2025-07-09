package com.skylab.skyl_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SkylAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkylAppApplication.class, args);
	}

}
