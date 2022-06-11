package com.uade.recipes.utilities;

import com.uade.recipes.model.Ingredient;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class SaveDataDB {
    public static List<Ingredient> getListOfIngredients() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("D:\\Documentos\\ingredientesTraducidos.csv"));
        List<Ingredient> ingredientList = new ArrayList<>();
        sc.useDelimiter(";");
        AtomicInteger contador = new AtomicInteger(1);
        while (sc.hasNext() && contador.get() != 100) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(formatString(sc.next()));
            ingredient.setDividable(true);
            ingredientList.add(ingredient);
            contador.getAndIncrement();

        }
        sc.close();
        System.out.println(ingredientList);
        return ingredientList;
    }

    private static String formatString(String name) {
        name = name.replaceAll("^\"|\"$", "");
        name = StringUtils.remove(name,'"');
        name = name.trim();;
        return StringUtils.capitalize(name);
    }

}
