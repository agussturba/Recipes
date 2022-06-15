package com.uade.recipes.persistance;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionRepository extends CrudRepository<Instruction, Integer> {
    List<Instruction> findByRecipe(Recipe recipe);

    Instruction findByRecipeAndNumberOfStep(Recipe recipe, Integer numberOfStep);

    Instruction findFirstByRecipeOrderByNumberOfStepDesc(Recipe recipe);
}
