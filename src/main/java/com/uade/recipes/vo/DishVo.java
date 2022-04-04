package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;
import lombok.Data;

import java.util.List;

@Data
public class DishVo {
    Integer id;
    String name;
    List<String> labels;


    public Dish toModel() {
        Dish dish = new Dish();
        dish.setId(getId());
        dish.setName(this.getName());
        dish.setLabels(this.getLabels());
        return dish;
    }

}
