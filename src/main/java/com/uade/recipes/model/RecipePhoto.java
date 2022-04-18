package com.uade.recipes.model;

import com.uade.recipes.vo.RecipePhotoVo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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


}
