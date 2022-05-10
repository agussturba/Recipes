package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.InvalidRoleException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.User_Addition;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.persistance.User_AdditionRepository;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final User_AdditionRepository user_AdditionRepository;

    public UserServiceImplementation(UserRepository userRepository, User_AdditionRepository user_AdditionRepository) {
        this.userRepository = userRepository;
        this.user_AdditionRepository = user_AdditionRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*@Override
    public User getUserByEmailAndPassword(String userName, String password) throws UserNotFoundException {
        return userRepository.findByEmailAndPassword(userName, password).orElseThrow(UserNotFoundException::new);
    }*/

    @Override
    public User getUserByAlias(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveOrUpdateUser(UserVo userVo, String role) throws UserNameExistsException, EmailExistsException, InvalidRoleException {
        existsUser(userVo);
        userVo.setRole(role);
        userVo.setEnabled(true);
        User user = userRepository.save(userVo.toModel());
        User_Addition user_addition = new User_Addition(user, userVo.getPassword());
        user_AdditionRepository.save(user_addition);
        return user;
    }

    @Override
    public void changePassword(String email, String password) {
        User user = this.getUserByEmail(email);
        User_Addition user_addition = user_AdditionRepository.findByUser(user);
        user_addition.setPassword(password);
        user_AdditionRepository.save(user_addition);
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

    private void saveAdditionalData(String password, User user) {
        User_Addition user_addition = new User_Addition(user, password);
        user_AdditionRepository.save(user_addition);
    }
}
