package com.uade.recipes.model;

import com.uade.recipes.vo.ConversionVo;
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
    @Column(nullable = false)
    private double conversionFactor; // Estara bien?

    public ConversionVo toVO() {
        ConversionVo vo = new ConversionVo();
        vo.setSourceUnitId(sourceUnit.getId());
        vo.setTargetUnitId(targetUnit.getId());
        vo.setConversionFactor(conversionFactor);

        return vo;
    }

}
