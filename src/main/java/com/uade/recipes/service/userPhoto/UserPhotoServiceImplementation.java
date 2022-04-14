package com.uade.recipes.service.userPhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.persistance.UserPhotoRepository;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.utilities.CloudinaryUtil;
import com.uade.recipes.vo.UserPhotoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class UserPhotoServiceImplementation implements UserPhotoService {
    private final UserPhotoRepository userPhotoRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

    public UserPhotoServiceImplementation(UserPhotoRepository userPhotoRepository, UserRepository userRepository) {
        this.userPhotoRepository = userPhotoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPhoto getUserPhotoById(Integer id) throws UserPhotoNotFoundException {
        return userPhotoRepository.findById(id).orElseThrow(UserPhotoNotFoundException::new);
    }

    @Override
    public List<UserPhoto> getAllUserPhotos() {
        return (List<UserPhoto>) userPhotoRepository.findAll();
    }

    @Override
    public UserPhoto getUserPhotoByUserId(Integer userId) throws UserNotFoundException, UserPhotoNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userPhotoRepository.findByUser(user).orElseThrow(UserPhotoNotFoundException::new);
    }

    @Override
    public UserPhoto saveOrUpdateUserPhoto(Integer userId, MultipartFile image) throws IOException, UserPhotoNotFoundException, UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserPhoto userPhoto;
        try {
             userPhoto = this.getUserPhotoByUserId(userId);
        } catch (UserPhotoNotFoundException | UserNotFoundException e) {
            userPhoto = new UserPhoto();
            userPhoto.setUser(user);
        }

        Map uploadResult = saveUserToCloudinary(image, userId);
        userPhoto.setPhotoUrl((String) uploadResult.get("url"));
        userPhoto.setExtension(StringUtils.getFilenameExtension(image.getOriginalFilename()));
        return userPhotoRepository.save(userPhoto);
    }

    private Map saveUserToCloudinary (MultipartFile image, Integer userId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.asMap("public_id", "user_" + userId + "_avatar"));
        return uploadResult;
    }
}
