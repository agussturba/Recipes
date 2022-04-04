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

    private String description;
    private String photo;
    private String video;


}
