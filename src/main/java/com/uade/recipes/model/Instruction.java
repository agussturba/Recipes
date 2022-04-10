package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private Recipe recipe;
    private String description;
    private Integer numberOfStep;



}
