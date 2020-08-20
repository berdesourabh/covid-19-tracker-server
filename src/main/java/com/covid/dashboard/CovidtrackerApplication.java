package com.covid.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CovidtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidtrackerApplication.class, args);
	}

}
