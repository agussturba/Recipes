package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeRatingVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecipeRating that = (RecipeRating) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}