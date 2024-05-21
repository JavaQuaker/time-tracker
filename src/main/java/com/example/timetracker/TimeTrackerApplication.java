package com.example.timetracker;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Calendar;

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
	@Bean
	public Calendar calendar() {
		return Calendar.getInstance();
	}

}
