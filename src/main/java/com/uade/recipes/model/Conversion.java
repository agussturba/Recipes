package com.uade.recipes.model;

import com.uade.recipes.vo.ConversionVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
    private double conversionFactor;

    public ConversionVo toVO() {
        ConversionVo vo = new ConversionVo();
        vo.setId(id);
        vo.setSourceUnitId(sourceUnit.getId());
        vo.setTargetUnitId(targetUnit.getId());
        vo.setConversionFactor(conversionFactor);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Conversion that = (Conversion) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
