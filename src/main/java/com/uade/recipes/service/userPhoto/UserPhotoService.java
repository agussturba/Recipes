package com.uade.recipes.service.userPhoto;

import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.vo.UserPhotoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserPhotoService {
    UserPhoto getUserPhotoById(Integer id);

    List<UserPhoto> getAllUserPhotos();

    UserPhoto getUserPhotoByUserId(Integer userId) throws UserNotFoundException;

    UserPhoto saveOrUpdateUserPhoto(Integer id, Integer userId, MultipartFile image) throws IOException;
}
