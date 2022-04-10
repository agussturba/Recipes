package com.uade.recipes.vo;

import com.uade.recipes.model.Type;
import lombok.Data;

@Data
public class TypeVo {
    Integer id;
    String description;
    public Type toModel(){
        Type type = new Type();
        type.setDescription(description);
        type.setId(id);
        return type;
    }
}
