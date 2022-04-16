package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeVo;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany
    private List<RecipePhoto> recipePhotos;
    @OneToOne
    private Dish dish;
    @OneToOne
    private User user;
    @Column(nullable = false)
    private Integer peopleAmount;
    @Column(nullable = false)
    private Double portions;
    @OneToMany
    private List<Type> type;

    public RecipeVo toVO(){
        RecipeVo vo = new RecipeVo();
        vo.setName(name);
        vo.setDescription(description);
        vo.setDishId(dish.getId());
        vo.setUserId(user.getId());
        vo.setPeopleAmount(peopleAmount);
        vo.setPortions(portions);

        List<Integer> listRecipePhotos = new ArrayList<>();
        for(RecipePhoto rp : recipePhotos){
            listRecipePhotos.add(rp.getId());
        }
        vo.setRecipePhotosIds(listRecipePhotos);

        List<Integer> listRecipeTypes = new ArrayList<>();
        for(Type t : type){
            listRecipeTypes.add(t.getId());
        }
        vo.setTypeIdList(listRecipeTypes);

        return vo;
    }



}
