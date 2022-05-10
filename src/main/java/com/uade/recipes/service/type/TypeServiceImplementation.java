package com.uade.recipes.service.type;

import com.uade.recipes.exceptions.typeExceptions.TypeDescriptionExistsException;
import com.uade.recipes.exceptions.typeExceptions.TypeNotFoundException;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.TypeRepository;
import com.uade.recipes.vo.TypeVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImplementation implements TypeService {
    private final TypeRepository typeRepository;

    public TypeServiceImplementation(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> getAllTypes() {
        return (List<Type>) typeRepository.findAll();
    }

    @Override
    public Type getTypeByDescription(String description) {
        return typeRepository.findByDescription(description).orElseThrow(TypeNotFoundException::new);
    }

    @Override
    public Type getTypeById(Integer idType) {
        return typeRepository.findById(idType).orElseThrow(TypeNotFoundException::new);
    }

    @Override
    public Type saveOrUpdateType(TypeVo typeVo) {
        if (typeVo.getId() != null) {
            typeExists(typeVo);
        }
        return typeRepository.save(typeVo.toModel());
    }

    @Override
    public List<Type> getTypesByIdList(List<Integer> types) {
        return (List<Type>) typeRepository.findAllById(types);
    }

    private void typeExists(TypeVo typeVo) {
        try {
            this.getTypeByDescription(typeVo.getDescription());
            throw new TypeDescriptionExistsException();
        } catch (TypeNotFoundException ignored) {
        }
    }
}

