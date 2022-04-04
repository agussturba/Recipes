package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String type;

}
