package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.InvalidEmailException;
import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.validations.UsersValidations;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;


    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveOrUpdateUser(UserVo user, String role) throws UserNameExistsException, EmailExistsException, InvalidPasswordException, InvalidRoleException, InvalidEmailException {
        existsUser(user);
        user.setRole(role);
        UsersValidations.validateUserData(user);
        return userRepository.save(user.toModel());
    }

    @Override
    public User getUserById(Integer idUser) {
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
