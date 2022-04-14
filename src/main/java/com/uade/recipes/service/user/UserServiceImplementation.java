package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import com.uade.recipes.validations.UsersValidations;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserPhotoService userPhotoService;
    private final UserRepository userRepository;

    public UserServiceImplementation(UserPhotoService userPhotoService, UserRepository userRepository) {
        this.userPhotoService = userPhotoService;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName,password);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveOrUpdateUser(UserVo user, String role) throws InvalidEmailException, InvalidPasswordException, InvalidRoleException, UserNameExistsException, EmailExistsException, UserNotFoundException, UserPhotoNotFoundException {
        existsUser(user);
        user.setRole(role);
        UsersValidations.validateUserData(user);
        UserPhoto userPhoto = userPhotoService.getUserPhotoByUserId(user.getIdUser());
        return userRepository.save(user.toModel(userPhoto));
    }

    @Override
    public User getUserById(Integer idUser) throws UserNotFoundException {
        return userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
    }

    private void existsUser(UserVo userVo) throws UserNameExistsException, EmailExistsException {
        if (this.getUserByUserName(userVo.getUserName()) != null) {
            throw new UserNameExistsException();
        }
        if (this.getUserByEmail(userVo.getEmail()) != null) {
            throw new EmailExistsException();
        }
    }

}
