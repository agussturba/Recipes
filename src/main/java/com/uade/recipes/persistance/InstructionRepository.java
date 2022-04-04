package com.uade.recipes.persistance;

import com.uade.recipes.model.Instruction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends CrudRepository<Instruction,Integer> {
}
