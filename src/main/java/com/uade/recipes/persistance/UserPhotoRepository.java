package com.uade.recipes.persistance;

import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPhotoRepository extends CrudRepository<UserPhoto,Integer> {
    Optional<UserPhoto> findByUser(User user);
}
