package com.codingdojo.ForumsApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ForumsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumsAppApplication.class, args);
	}

	
	
}
