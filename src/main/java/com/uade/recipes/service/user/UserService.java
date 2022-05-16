package com.uade.recipes.service.user;

import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.vo.UserVo;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserByEmailAndAlias(String email, String alias) throws UserNotFoundException;

    User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User getUserByAlias(String userName) throws UserNotFoundException;

    User getUserById(Integer userId) throws UserNotFoundException;

    User saveOrUpdateUser(UserVo user, String role) throws InvalidEmailException, InvalidPasswordException, InvalidRoleException, UserNameExistsException, EmailExistsException, UserNotFoundException, UserPhotoNotFoundException;

    void changePassword(String email, String password) throws UserNotFoundException;

    User validateUserRegistration(String email) throws UserNotFoundException;

    User createNewUser(String alias, String email, String role) throws UserNameExistsException, EmailExistsException;

    void confirmEmail(String email) throws UserNotFoundException;

}
