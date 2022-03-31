package com.uade.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RecipesApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}

}
