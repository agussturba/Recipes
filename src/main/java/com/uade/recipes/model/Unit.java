package com.uade.recipes.model;

import com.uade.recipes.vo.UnitVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SecondaryTable(name = "unit_addition", pkJoinColumns = @PrimaryKeyJoinColumn(name = "unit_id"))
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String description;

    @Column(table = "unit_addition")
    private String shortened;

    public UnitVo toVO(){
        UnitVo vo = new UnitVo();
        vo.setId(id);
        vo.setDescription(description);
        vo.setShortened(shortened);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Unit unit = (Unit) o;
        return id != null && Objects.equals(id, unit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}