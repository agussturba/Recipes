package com.uade.recipes.service.type;

import com.uade.recipes.exceptions.typeExceptions.TypeNotFountException;
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
        return typeRepository.findByDescription(description);
    }

    @Override
    public Type getTypeById(Integer idType) {
        return typeRepository.findById(idType).orElseThrow(TypeNotFountException::new);
    }

    @Override
    public Type saveOrUpdateType(TypeVo typeVo) {
        return typeRepository.save(typeVo.toModel());
    }

    @Override
    public List<Type> getTypesByIdList(List<Integer> types) {
        return (List<Type>) typeRepository.findAllById(types);
    }
}
