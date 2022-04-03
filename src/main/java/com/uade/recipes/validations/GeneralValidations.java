package com.uade.recipes.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidations {
    public static boolean containsNumber(String string) {
        Pattern pattern = Pattern.compile("(.)*(\\d)(.)*");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
