package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
 public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String description;
    private Integer duration;
    @OneToOne
    private Dish dish;
    @OneToOne
    private User user;
    @Column(nullable = false)
    private Integer peopleAmount;
    @Column(nullable = false)
    private Double portions;
    @OneToMany
    @ToString.Exclude
    private List<Type> type;

    public RecipeVo toVO() {
        RecipeVo vo = new RecipeVo();
        vo.setId(id);
        vo.setName(name);
        vo.setDescription(description);
        vo.setDishId(dish.getId());
        vo.setUserId(user.getId());
        vo.setPeopleAmount(peopleAmount);
        vo.setPortions(portions);

        List<Integer> listRecipeTypes = new ArrayList<>();
        for (Type t : type) {
            listRecipeTypes.add(t.getId());
        }
        vo.setTypeIdList(listRecipeTypes);

        return vo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
