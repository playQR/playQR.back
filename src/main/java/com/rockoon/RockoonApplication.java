package com.rockoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RockoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RockoonApplication.class, args);
	}

}
