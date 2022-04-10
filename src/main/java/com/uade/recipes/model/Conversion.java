package com.uade.recipes.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Unit sourceUnit;
    @OneToOne
    private Unit targetUnit;
    private double conversionFactor; // Estara bien?

}
