package com.coursera.scms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScmsApplication.class, args);
		System.out.println("Started ScmsApplication...");
	}
}
