package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;

import java.util.List;

public class DishVo {
     Integer id;
     String name;
     List<String> labels;

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public List<String> getLabels() {
          return labels;
     }

     public void setLabels(List<String> labels) {
          this.labels = labels;
     }

     public Dish toModel(){
          Dish dish = new Dish();
          dish.setId(getId());
          dish.setName(this.name);
          dish.setLabels(this.labels);
          return dish;
     }

}
