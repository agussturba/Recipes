package com.uade.recipes.model;

import com.uade.recipes.vo.DishVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @OneToMany
    private Set<Type> types;

    public Dish(String name, Set<Type> typesTest) {
        this.name = name;
        this.types = typesTest;
    }

    public DishVo toVO() {
        DishVo vo = new DishVo();
        vo.setName(name);
        List<Integer> list = new ArrayList<>();
        for (Type type : types) {
            list.add(type.getId());
        }
        vo.setTypesIdList(list);

        return vo;
    }

}
