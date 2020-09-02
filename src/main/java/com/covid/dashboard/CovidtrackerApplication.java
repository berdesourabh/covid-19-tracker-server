package com.covid.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class CovidtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidtrackerApplication.class, args);
	}


	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}

}
