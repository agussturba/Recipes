package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Type;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DishVo {
    Integer id;
    String name;
    List<Integer> typesIdList;


    public Dish toModel(Set<Type> types) {
        Dish dish = new Dish();
        dish.setId(getId());
        dish.setName(this.getName());
        dish.setTypes(types);
        return dish;
    }

}
