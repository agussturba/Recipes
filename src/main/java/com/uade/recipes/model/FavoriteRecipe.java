package com.uade.recipes.model;

import com.uade.recipes.vo.FavoriteRecipeVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FavoriteRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Recipe recipe;

    public FavoriteRecipe(Recipe recipe, User user) {
        this.recipe = recipe;
        this.user = user;
    }
    public FavoriteRecipeVo toVo(){
        FavoriteRecipeVo favoriteRecipeVo = new FavoriteRecipeVo();
        favoriteRecipeVo.setId(id);
        favoriteRecipeVo.setRecipeId(recipe.getId());
        favoriteRecipeVo.setUserId(user.getId());
        return favoriteRecipeVo;
    }
}
