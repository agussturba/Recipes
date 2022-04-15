package com.uade.recipes.validations;

import com.uade.recipes.exceptions.userExceptions.InvalidEmailException;
import com.uade.recipes.exceptions.userExceptions.InvalidPasswordException;
import com.uade.recipes.exceptions.userExceptions.InvalidRoleException;
import com.uade.recipes.vo.UserVo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.uade.recipes.validations.GeneralValidations.isValidPassword;
import static com.uade.recipes.validations.GeneralValidations.validateEmail;

public class UsersValidations {


    private static boolean isValidRole(String role) {
        return role == "INVITADO" || role == "ALUMNO";
    }

    public static void validateUserData(UserVo userVo) throws InvalidRoleException, InvalidPasswordException, InvalidEmailException {
        if (!isValidRole(userVo.getRole())){
            throw new InvalidRoleException();
        }
        if(!isValidPassword(userVo.getPassword())){
            throw new InvalidPasswordException();
        }
        validateEmail(userVo.getEmail());
    }
}
