package com.uade.recipes.vo;

import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import lombok.Data;

@Data
public class UserPhotoVo {
     Integer id;
     Integer userId;
     String photoUrl;
     String extension;
     public UserPhoto toModel(User user){
         UserPhoto userPhoto = new UserPhoto();
         userPhoto.setUser(user);
         userPhoto.setId(id);
         userPhoto.setPhotoUrl(photoUrl);
         userPhoto.setExtension(extension);
         return userPhoto;
     }
}