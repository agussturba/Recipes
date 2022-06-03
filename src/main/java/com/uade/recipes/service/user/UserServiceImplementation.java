package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.RegistrationProcessIncompleteException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.email.EmailSenderImplementation;
import com.uade.recipes.service.email.EmailSenderService;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final EmailSenderService emailSender;

    public UserServiceImplementation(UserRepository userRepository, EmailSenderService emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmailAndAlias(String email, String alias) throws UserNotFoundException {
        return userRepository.findByEmailAndUserName(email, alias).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmailAndPassword(String userName, String password) throws UserNotFoundException {
        return userRepository.findByEmailAndPassword(userName, password).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User validateUserRegistration(String email) throws UserNotFoundException {
        return userRepository.findByEmailAndRegistrationTimestampGreaterThanAndRegistrationTimestampLessThanEqual(email, LocalDateTime.now().minusDays(1), LocalDateTime.now()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void confirmEmail(String email) throws UserNotFoundException {
        User user = validateUserRegistration(email);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User getUserByAlias(String userName) throws UserNotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveOrUpdateUser(UserVo userVo, String role) throws UserNameExistsException, EmailExistsException{
        checkEmailExistence(userVo.getEmail());
        checkAliasExistence(userVo.getUserName());
        userVo.setPassword("");
        userVo.setRole(role);
        userVo.setEnabled(false);
        userVo.setRegistrationTimestamp(LocalDateTime.now());
        User user = userRepository.save(userVo.toModel());
        emailSender.sendSimpleEmail(userVo.getEmail(), "Hola " + userVo.getUserName() + ",\nEste es un mensaje para verificar tu correo electrónico.\nHaz Click en el link para validar tu correo: https://tasty-hub.herokuapp.com/api/user/email/confirmation?email=" + userVo.getEmail(), "Correo de verificación");
        return user;
    }

    @Override
    public void changePassword(String email, String password) throws UserNotFoundException {
        User user = this.getUserByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void isRegistryComplete(String email) throws UserNotFoundException, RegistrationProcessIncompleteException {
        User user = getUserByEmail(email);
        if (!user.isEnabled())
            throw new RegistrationProcessIncompleteException();

    }

    @Override
    public User getUserByAliasAndPassword(String alias, String password) throws UserNotFoundException {
        User user = getUserByAlias(alias);
        if (user.getPassword().equals(password)) return user;
        throw new UserNotFoundException();
    }

    private void checkEmailExistence(String email) throws EmailExistsException {
        try {
            getUserByEmail(email);
            throw new EmailExistsException();
        } catch (UserNotFoundException ignored) {
        }

    }

    private void checkAliasExistence(String alias) throws UserNameExistsException {
        try {
            getUserByAlias(alias);
            throw new UserNameExistsException();
        } catch (UserNotFoundException ignored) {
        }
    }


}
