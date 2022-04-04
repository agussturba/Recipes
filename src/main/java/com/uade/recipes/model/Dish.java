package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @ElementCollection
    private List<String> labels;

}
