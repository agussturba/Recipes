package com.uade.recipes.service.multimedia;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.vo.MultimediaVo;

import java.util.List;

public interface MultimediaService {
    Multimedia getMultimediaById(Integer id);

    List<Multimedia> getAllMultimedia();

    Multimedia getMultimediaByInstructionId(Integer instructionId) throws InstructionNotFoundException;

    Multimedia saveOrUpdateMultimedia(MultimediaVo multimediaVo) throws InstructionNotFoundException;
}
