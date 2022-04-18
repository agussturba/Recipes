package com.uade.recipes.model;

import com.uade.recipes.vo.TypeVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "type")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String description;

    public Type(String description) {
        this.description = description;
    }

    public TypeVo toVO() {
        TypeVo vo = new TypeVo();
        vo.setId(id);
        vo.setDescription(description);
        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Type type = (Type) o;
        return id != null && Objects.equals(id, type.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
