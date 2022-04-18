package com.uade.recipes.model;

import com.uade.recipes.vo.DishVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToMany
    @ToString.Exclude
    private Set<Type> types;

    public Dish(String name, Set<Type> typesTest) {
        this.name = name;
        this.types = typesTest;
    }

    public DishVo toVO() {
        DishVo vo = new DishVo();
        vo.setId(id);
        vo.setName(name);
        List<Integer> list = new ArrayList<>();
        for (Type type : types) {
            list.add(type.getId());
        }
        vo.setTypesIdList(list);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dish dish = (Dish) o;
        return id != null && Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
