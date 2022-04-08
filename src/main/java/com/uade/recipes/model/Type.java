package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="type")
@Data
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String description;

}
