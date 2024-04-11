package com.example.timetracker;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TimeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}
	@Bean
	public Faker getFaker() {
		return new Faker();
	}

}
