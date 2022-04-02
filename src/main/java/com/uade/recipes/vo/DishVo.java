package com.uade.recipes.vo;

import java.util.List;

public class DishVo {
     Integer id;
     String name;
     List<String> type;

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
          return type;
     }

     public void setType(List<String> type) {
          this.type = type;
     }
}
