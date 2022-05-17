package com.uade.recipes.model;

import com.uade.recipes.vo.RecipePhotoVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class RecipePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private Recipe recipe;
    private String photoUrl;
    private String extension;

    public RecipePhotoVo toVO(){
        RecipePhotoVo vo = new RecipePhotoVo();
        vo.setId(id);
        vo.setRecipeId(recipe.getId());
        vo.setPhotoUrl(photoUrl);
        vo.setExtension(extension);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecipePhoto that = (RecipePhoto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
