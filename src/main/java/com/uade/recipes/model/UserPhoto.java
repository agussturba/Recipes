package com.uade.recipes.model;

import com.uade.recipes.vo.UserPhotoVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString

public class UserPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private User user;
    private String photoUrl;
    private String extension;

    public UserPhotoVo toVO(){
        UserPhotoVo vo = new UserPhotoVo();
        vo.setId(id);
        vo.setUserId(user.getId());
        vo.setPhotoUrl(photoUrl);
        vo.setExtension(extension);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserPhoto userPhoto = (UserPhoto) o;
        return id != null && Objects.equals(id, userPhoto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
