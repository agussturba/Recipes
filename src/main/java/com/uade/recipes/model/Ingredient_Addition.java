package com.uade.recipes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Ingredient_Addition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Ingredient ingredient;
    @OneToOne
    private Type type;
    /**
     * Atributo para verificar que el ingrediento puede ser divisible.Que el ingrediente puede ser la mitadd
     * de dicho elemento.IE un tomate es divisble porque podes trabajar con medio tomate pero un huevo no es
     * divisible.
     */
    private boolean dividable;

    public Ingredient_Addition(Ingredient ingredient, Type type, boolean dividable) {
        this.ingredient = ingredient;
        this.type = type;
        this.dividable = dividable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
