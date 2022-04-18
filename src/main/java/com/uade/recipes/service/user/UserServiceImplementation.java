package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UserRepository;
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
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByEmailAndPassword(String userName, String password) throws UserNotFoundException {
        return userRepository.findByEmailAndPassword(userName,password).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByAlias(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveOrUpdateUser(UserVo user, String role) throws UserNameExistsException, EmailExistsException {
        existsUser(user);
        user.setRole(role);
        return userRepository.save(user.toModel());
    }

    @Override
    public void changePassword(String email, String password) {
        User user = this.getUserByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }



    private void existsUser(UserVo userVo) throws UserNameExistsException, EmailExistsException {
        if (this.getUserByAlias(userVo.getUserName()) != null) {
            throw new UserNameExistsException();
        }
        if (this.getUserByEmail(userVo.getEmail()) != null) {
            throw new EmailExistsException();
        }
    }

}
