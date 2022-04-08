package com.uade.recipes.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "convertions")
@Data
public class Convertions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Unity sourceUnity;
    @OneToOne
    private Unity targetUnity;
    private double convertionFactor; // Estara bien?

}
