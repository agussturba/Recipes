package com.uade.recipes.model;

import com.uade.recipes.vo.UserPhotoVo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne(optional = true)
    private User user;
    private String photoUrl;
    private String extension;

    public UserPhotoVo toVO(){
        UserPhotoVo vo = new UserPhotoVo();
        vo.setUserId(user.getId());
        vo.setPhotoUrl(photoUrl);
        vo.setExtension(extension);

        return vo;
    }

}
