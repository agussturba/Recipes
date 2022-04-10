package com.uade.recipes.service.userPhoto;

import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.persistance.UserPhotoRepository;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.vo.UserPhotoVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserPhotoServiceImplementation implements UserPhotoService{
    private final UserPhotoRepository userPhotoRepository;
    private final UserRepository userRepository;

    public UserPhotoServiceImplementation(UserPhotoRepository userPhotoRepository, UserRepository userRepository) {
        this.userPhotoRepository = userPhotoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPhoto getUserPhotoById(Integer id) {
        return userPhotoRepository.findById(id).orElseThrow(UserPhotoNotFoundException::new);
    }

    @Override
    public List<UserPhoto> getAllUserPhotos() {
        return (List<UserPhoto>) userPhotoRepository.findAll();
    }

    @Override
    public UserPhoto getUserPhotoByUserId(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userPhotoRepository.findByUser(user);
    }

    @Override
    public UserPhoto saveOrUpdateUserPhoto(UserPhotoVo userPhotoVo) {
        User user = userRepository.findById(userPhotoVo.getUserId()).orElseThrow(UserPhotoNotFoundException::new);
        return userPhotoRepository.save(userPhotoVo.toModel(user));
    }
}
