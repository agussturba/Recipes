package com.uade.recipes.utilities;

import com.uade.recipes.model.Ingredient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class SaveDataDB {
    public List<String> getListOfIngredients() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Scanner sc = new Scanner(new File(classLoader.getResource("ingredientesTraducidos.csv").getFile()));
        List<String> ingredientList = new ArrayList<>();
        sc.useDelimiter(";");
        AtomicInteger contador = new AtomicInteger(1);
        while (sc.hasNext() && contador.get() != 100) {
            ingredientList.add(formatString(sc.next()));
            contador.getAndIncrement();
        }
        sc.close();
        return ingredientList;
    }

    public void saveListIngredients() throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://tasty-hub.herokuapp.com/api/ingredients");
        List<String> ingredientList = getListOfIngredients();
        for (String ingredientName : ingredientList) {
            httppost.setEntity(new StringEntity(ingredientJson(ingredientName)));
            httpclient.execute(httppost);
        }


    }

    private String ingredientJson(String name) {
        return "{\"name\": \" " + name + "\"," +
                "\n  \"dividable\": true," +
                "}";
    }

    private static String formatString(String name) {
        name = name.replaceAll("^\"|\"$", "");
        name = StringUtils.remove(name, '"');
        name = name.trim();
        ;
        return StringUtils.capitalize(name);
    }

}
