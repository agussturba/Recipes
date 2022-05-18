package com.uade.recipes.service.userPhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.persistance.UserPhotoRepository;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.utilities.CloudinaryUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
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
    public UserPhoto saveUserPhoto(Integer userId, MultipartFile image) throws IOException, UserNotFoundException {//TODO CLEAN CODE
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserPhoto userPhoto = getOrCreateUserPhotoByUserId(user);

        Map uploadResult = saveUserPhotoToCloudinary(image, userId);
        userPhoto.setPhotoUrl((String) uploadResult.get("url"));
        userPhoto.setExtension(StringUtils.getFilenameExtension(image.getOriginalFilename()));

        UserPhoto photo = userPhotoRepository.save(userPhoto);
        user.setAvatar(photo);
        userRepository.save(user);
        return photo;

    }

    @Override
    public void deleteUserPhoto(Integer photoId) throws UserPhotoNotFoundException, IOException {
        UserPhoto userPhoto = this.getUserPhotoById(photoId);
        List<String> url = Arrays.asList(userPhoto.getPhotoUrl().split("/"));
        String filename = url.get(url.size() - 1);
        String public_id = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        userPhotoRepository.delete(userPhoto);
    }


    private Map saveUserPhotoToCloudinary(MultipartFile image, Integer userId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.asMap("public_id", "user_" + userId + "_avatar"));
        return uploadResult;
    }

    private UserPhoto getOrCreateUserPhotoByUserId(User user) {
        if (user.getAvatar() == null) {
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setUser(user);
            return userPhoto;
        } else {
            return user.getAvatar();
        }
    }
}
