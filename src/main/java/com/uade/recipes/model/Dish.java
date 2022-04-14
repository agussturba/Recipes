package com.uade.recipes.model;

import com.uade.recipes.vo.DishVo;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @OneToMany
    private Set<Type> types;

    public DishVo toVO(){
        DishVo vo = new DishVo();
        vo.setName(name);
        List<Integer> list = new ArrayList<>();
        for (Type type : types){
            list.add(type.getId());
        }
        vo.setTypesIdList(list);

        return vo;
    }

}
