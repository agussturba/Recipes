package com.uade.recipes.service.user;


import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.RegistrationProcessIncompleteException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.email.EmailSenderService;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final UserPhotoService userPhotoService;
    private final EmailSenderService emailSender;

    public UserServiceImplementation(UserRepository userRepository, UserPhotoService userPhotoService, EmailSenderService emailSender) {
        this.userRepository = userRepository;
        this.userPhotoService = userPhotoService;
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
    public void saveAllUsers(List<User> users) {
        for (User user:users) {
            user.setRegistrationTimestamp(LocalDateTime.now());
        }
        userRepository.saveAll(users);
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

    @Override
    public User saveUser(UserVo userVo) throws EmailExistsException, UserNameExistsException {
        checkEmailExistence(userVo.getEmail());
        checkAliasExistence(userVo);
        userVo.setRegistrationTimestamp(LocalDateTime.now());
        emailSender.sendSimpleEmail(userVo.getEmail(), "Hola " + userVo.getUserName() + ",\nEste es un mensaje para verificar tu correo electrónico.\nHaz Click en el link para validar tu correo: https://tasty-hub.herokuapp.com/api/user/email/confirmation?email=" + userVo.getEmail(), "Correo de verificación");
        return userRepository.save(userVo.toModel());
    }

    @Override
    public User updateUser(UserVo userVo) throws UserNotFoundException, UserPhotoNotFoundException, EmailExistsException, UserNameExistsException {
        User user = this.getUserById(userVo.getId());
        userVo.setEnabled(true);
        userVo.setRole(user.getRole());
        userVo.setRegistrationTimestamp(user.getRegistrationTimestamp());
        if (userVo.getUserName() != null) {
            checkAliasExistence(userVo);
            user.setUserName(userVo.getUserName());
        }
        if (userVo.getPassword() != null) {
            user.setPassword(userVo.getPassword());
        }
        if (userVo.getName() != null) {
            user.setName(userVo.getName());
        }
        if (userVo.getEmail() != null) {
            checkEmailExistence(userVo.getEmail());
            user.setName(userVo.getEmail());
        }
        if (userVo.getAvatarId() != null) {
            UserPhoto photo = userPhotoService.getUserPhotoById(userVo.getAvatarId());
            user.setAvatar(photo);
        }
        return userRepository.save(user);

    }

    private void checkAliasExistence(UserVo userVo) throws UserNameExistsException {
        try {
            if (userVo.getId() != null) {
                getUserByAlias(userVo.getUserName());
                throw new UserNameExistsException();
            }
        } catch (UserNotFoundException ignored) {
        }
    }


}
