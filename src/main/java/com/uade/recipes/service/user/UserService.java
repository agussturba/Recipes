package com.uade.recipes.service.user;

import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.model.User;
import com.uade.recipes.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserByEmailAndAlias(String email, String alias) throws UserNotFoundException;

    User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User getUserByAliasAndPassword(String alias, String password) throws UserNotFoundException;

    User getUserByAlias(String userName) throws UserNotFoundException;

    User getUserById(Integer userId) throws UserNotFoundException;

    User saveUser(UserVo userVo) throws EmailExistsException, UserNameExistsException;

    User updateUser(UserVo userVo) throws UserNotFoundException, EmailExistsException, UserNameExistsException;

    void changePassword(String email, String password) throws UserNotFoundException;

    User validateUserRegistration(String email) throws UserNotFoundException;

    void saveAllUsers(List<User> users);

    void confirmEmail(String email) throws UserNotFoundException;

    void isRegistryComplete(String email) throws UserNotFoundException, RegistrationProcessIncompleteException;

    String saveUserPhoto(Integer userId, MultipartFile image) throws UserNotFoundException, IOException;

    void deleteUserPhoto(Integer userId) throws UserNotFoundException, IOException;

    List<User> getUsersByPartialUserName(String username) throws UserNotFoundException;
}
