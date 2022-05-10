package com.uade.recipes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
}
