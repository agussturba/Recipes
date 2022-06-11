package com.uade.recipes;

import com.uade.recipes.utilities.SaveDataDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class RecipesApplication {


	public static void main(String[] args) throws IOException {
		SaveDataDB saveDataDB = new SaveDataDB();
		saveDataDB.saveListIngredients();
	//	SpringApplication.run(RecipesApplication.class, args);
	}

}
