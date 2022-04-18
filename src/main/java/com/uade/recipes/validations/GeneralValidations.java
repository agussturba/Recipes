package com.uade.recipes.validations;

import com.uade.recipes.exceptions.userExceptions.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidations {
    public static boolean containsNumber(String string) {
        Pattern pattern = Pattern.compile("(.)*(\\d)(.)*");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static void validateEmail(String email) throws InvalidEmailException {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (email == null) {
            throw new InvalidEmailException();
        }
        if (!matcher.matches()) {
            throw new InvalidEmailException();
        }
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
