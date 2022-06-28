package com.uade.recipes.service.user;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.userExceptions.EmailExistsException;
import com.uade.recipes.exceptions.userExceptions.RegistrationProcessIncompleteException;
import com.uade.recipes.exceptions.userExceptions.UserNameExistsException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.email.EmailSenderService;
import com.uade.recipes.utilities.CloudinaryUtil;
import com.uade.recipes.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
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
    public void saveAllUsers(List<User> users) {
        for (User user : users) {
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
    public List<String> getSuggestedAliasList(String currentAlias) {
        List<String> suggestedAliasList = new ArrayList<>();
        Integer randomNumber = 1;
        while (suggestedAliasList.size()!=5){
            String newSuggestedAlias = currentAlias+randomNumber;
            try {
                this.getUserByAlias(newSuggestedAlias);
                randomNumber++;
            }catch (UserNotFoundException e){
                suggestedAliasList.add(newSuggestedAlias);
                randomNumber++;
            }
        }
        return suggestedAliasList;
    }

    @Override
    public void isRegistryComplete(String email) throws UserNotFoundException, RegistrationProcessIncompleteException {
        User user = getUserByEmail(email);
        if (!user.isEnabled())
            throw new RegistrationProcessIncompleteException();
    }

    @Override
    public String saveUserPhoto(Integer userId, MultipartFile image) throws UserNotFoundException, IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Map uploadResult = saveUserPhotoToCloudinary(image, userId);
        user.setAvatar((String) uploadResult.get("url"));
        userRepository.save(user);
        return user.getAvatar();
    }

    @Override
    public void deleteUserPhoto(Integer userId) throws UserNotFoundException, IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<String> url = Arrays.asList(user.getAvatar().split("/"));
        String filename = url.get(url.size() - 1);
        String public_id = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        user.setAvatar("https://res.cloudinary.com/fransiciliano/image/upload/v1656009088/default_avatar.jpg");
        userRepository.save(user);

    }

    @Override
    public List<User> getUsersByPartialUserName(String username) throws UserNotFoundException {
        return userRepository.findByUserNameContainingIgnoreCase(username).orElseThrow(UserNotFoundException::new);

    }

    private Map saveUserPhotoToCloudinary(MultipartFile image, Integer userId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.asMap("public_id", "user_" + userId + "_avatar"));
        return uploadResult;
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
    public User updateUser(UserVo userVo) throws UserNotFoundException, EmailExistsException, UserNameExistsException {
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
        if (userVo.getAvatar() != null) {
            user.setAvatar(userVo.getAvatar());
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
