package com.fernandez.quart;

import com.fernandez.quart.service.UrlProcessingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QuartApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QuartApplication.class, args);
		UrlProcessingService urlProcessingService = context.getBean(UrlProcessingService.class);
		urlProcessingService.processUrls();
	}
}
