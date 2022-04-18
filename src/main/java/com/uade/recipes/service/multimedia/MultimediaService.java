package com.uade.recipes.service.multimedia;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Multimedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MultimediaService {
    Multimedia getMultimediaById(Integer id);

    List<Multimedia> getAllMultimedia();

    Multimedia getMultimediaByInstructionId(Integer instructionId) throws InstructionNotFoundException;

    Iterable<Multimedia> saveMultimedia(Integer instructionId, List<MultipartFile> multimedia) throws InstructionNotFoundException, IOException;

    void deleteMultimedia(Integer multimediaId) throws IOException;
}
