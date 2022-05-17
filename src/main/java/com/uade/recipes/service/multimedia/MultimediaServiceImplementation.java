package com.uade.recipes.service.multimedia;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.multimediaExceptions.MultimediaNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.persistance.MultimediaRepository;
import com.uade.recipes.service.instruction.InstructionService;
import com.uade.recipes.utilities.CloudinaryUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MultimediaServiceImplementation implements MultimediaService {
    private final MultimediaRepository multimediaRepository;
    private final InstructionService instructionService;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

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
    public Iterable<Multimedia> saveMultimedia(Integer instructionId, List<MultipartFile> multimedia) throws InstructionNotFoundException, IOException {
        Instruction instruction = instructionService.getInstructionById(instructionId);
        List<Multimedia> multimediaList = new ArrayList<>();
        for (MultipartFile videoOrImage : multimedia) {
            Multimedia newImageOrVideo = setNewImageOrVideo(videoOrImage, instruction);
            multimediaList.add(newImageOrVideo);
        }
        return multimediaRepository.saveAll(multimediaList);
    }
    private Multimedia setNewImageOrVideo(MultipartFile videoOrImage,Instruction instruction) throws IOException {
        Multimedia newImageOrVideo = new Multimedia();
        newImageOrVideo.setInstruction(instruction);
        newImageOrVideo.setTypeContent(videoOrImage.getContentType());
        Map uploadResult = saveMultimediaToCloudinary(videoOrImage);
        newImageOrVideo.setUrlContent((String) uploadResult.get("url"));
        newImageOrVideo.setExtension(StringUtils.getFilenameExtension(videoOrImage.getOriginalFilename()));
        return newImageOrVideo;
    }

    @Override
    public void deleteMultimedia(Integer multimediaId) throws IOException {
        Multimedia multimedia = this.getMultimediaById(multimediaId);
        List<String> url = Arrays.asList(multimedia.getUrlContent().split("/"));
        String filename = url.get(url.size()-1);
        String public_id = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        multimediaRepository.delete(multimedia);
    }

    private Map saveMultimediaToCloudinary(MultipartFile multimediaFile) throws IOException {
        String type = multimediaFile.getContentType().split("/")[0];
        return  cloudinary.uploader().upload(multimediaFile.getBytes(),
                ObjectUtils.asMap("resource_type", type));

    }
}
