package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishVo {
    Integer id;
    String name;
    Integer typeId;


    public Dish toModel(Type type) {
        Dish dish = new Dish();
        dish.setId(getId());
        dish.setName(this.getName());
        dish.setType(type);
        return dish;
    }

}
