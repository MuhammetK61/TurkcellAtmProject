package com.example.TurkcellAtmProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//websecurity exclude
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class })
public class TurkcellAtmProjectApplication {
	
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		System.setProperty("java.awt.headless", "false"); // Disables headless
		SpringApplication.run(TurkcellAtmProjectApplication.class, args);
		
	}
}
