package com.uade.recipes.persistance;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Multimedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultimediaRepository extends CrudRepository<Multimedia, Integer> {
    List<Multimedia> findByInstruction(Instruction instruction);
}
