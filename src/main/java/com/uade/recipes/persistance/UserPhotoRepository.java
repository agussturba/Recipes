package com.uade.recipes.persistance;

import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserPhotoRepository extends CrudRepository<UserPhoto,Integer> {
    UserPhoto findByUser(User user);
}
