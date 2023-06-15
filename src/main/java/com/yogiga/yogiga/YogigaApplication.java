package com.yogiga.yogiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YogigaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YogigaApplication.class, args);
	}

}
