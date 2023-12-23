package com.fernandez.fixtures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NpmApplication {

	public static void main(String[] args) {
		SpringApplication.run(NpmApplication.class, args);
	}
}
