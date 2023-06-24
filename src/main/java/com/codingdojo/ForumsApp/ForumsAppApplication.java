package com.codingdojo.ForumsApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication
public class ForumsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumsAppApplication.class, args);
	}

	
	
}
