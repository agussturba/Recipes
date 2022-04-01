package com.uade.recipes.persistance;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionRepository extends MongoRepository<Instruction,Integer> {
    List<Instruction> findByRecipe(Recipe recipe);
}
