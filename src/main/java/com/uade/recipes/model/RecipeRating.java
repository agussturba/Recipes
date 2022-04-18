package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeRatingVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class RecipeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Recipe recipe;
    @OneToOne
    private User user;
    private String comments;
    @Column(nullable = false)
    private Double rating;

    public RecipeRatingVo toVO(){
        RecipeRatingVo vo = new RecipeRatingVo();
        vo.setId(id);
        vo.setRecipeId(recipe.getId());
        vo.setUserId(user.getId());
        vo.setComments(comments);
        vo.setRating(rating);

        return vo;
    }


}