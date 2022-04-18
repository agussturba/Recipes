package com.uade.recipes.vo;

import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
