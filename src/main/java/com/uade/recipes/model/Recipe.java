package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    private String name;
    private String description;
    private Integer duration;
    @OneToOne
    private RecipePhoto recipePhoto;
    @OneToOne
    private User user;
    private Integer peopleAmount;
    private Double portions;
    @OneToOne
    private Type type;

    public RecipeVo toVO() {
        RecipeVo vo = new RecipeVo();
        vo.setId(id);
        vo.setName(name);
        vo.setDescription(description);
        vo.setUserId(user.getId());
        vo.setPeopleAmount(peopleAmount);
        vo.setPortions(portions);
        vo.setTypeId(type.getId());
        return vo;
    }
    public Integer getUserId(){
        return user.getId();
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
