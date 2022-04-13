package com.uade.recipes.service.type;

import com.uade.recipes.model.Type;
import com.uade.recipes.vo.TypeVo;

import java.util.List;

public interface TypeService {
    List<Type> getAllTypes();

    Type getTypeByDescription(String description);

    Type getTypeById(Integer idType);

    Type saveOrUpdateType(TypeVo typeVo);

    List<Type> getTypesByIdList(List<Integer> types);
}
