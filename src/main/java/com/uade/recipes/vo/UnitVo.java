package com.uade.recipes.vo;

import com.uade.recipes.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitVo {
     Integer id;
     String description;

     public Unit toModel(){
         Unit unit=new Unit();
         unit.setDescription(description);
         unit.setId(id);
         return unit;
     }
}
