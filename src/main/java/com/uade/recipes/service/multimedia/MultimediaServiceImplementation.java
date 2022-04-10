package com.uade.recipes.service.multimedia;

import com.uade.recipes.exceptions.MultimediaNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.persistance.MultimediaRepository;
import com.uade.recipes.service.instruction.InstructionService;
import com.uade.recipes.vo.MultimediaVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MultimediaServiceImplementation implements MultimediaService {
    private final MultimediaRepository multimediaRepository;
    private final InstructionService instructionService;

    public MultimediaServiceImplementation(MultimediaRepository multimediaRepository, InstructionService instructionService) {
        this.multimediaRepository = multimediaRepository;
        this.instructionService = instructionService;
    }

    @Override
    public Multimedia getMultimediaById(Integer id) {
        return multimediaRepository.findById(id).orElseThrow(MultimediaNotFoundException::new);
    }

    @Override
    public List<Multimedia> getAllMultimedia() {
        return (List<Multimedia>) multimediaRepository.findAll();
    }

    @Override
    public Multimedia getMultimediaByInstructionId(Integer instructionId) throws InstructionNotFoundException {
        Instruction instruction = instructionService.getInstructionById(instructionId);
        return multimediaRepository.findByInstruction(instruction);
    }

    @Override
    public Multimedia saveOrUpdateMultimedia(MultimediaVo multimediaVo) throws InstructionNotFoundException {
        Instruction instruction = instructionService.getInstructionById(multimediaVo.getInstructionId());
        return multimediaRepository.save(multimediaVo.toModel(instruction));
    }
}
