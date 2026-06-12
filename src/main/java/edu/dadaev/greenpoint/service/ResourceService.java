package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.ResourceMapper;
import edu.dadaev.greenpoint.dto.ResourceResponseDTO;
import edu.dadaev.greenpoint.dto.ResourceRequestDTO;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepo;
    private final ResourceMapper resourceMapper;
    private final StorageService storageService;
    private final UserRepository userRepository;


    public List<ResourceResponseDTO> getResources(){
        return resourceRepo.findAll().stream().map(resourceMapper::toDTO).toList();
    }

    public ResourceResponseDTO createResourse(ResourceRequestDTO resourceRequestDTO, Long ownerId){
        Resource entity = resourceMapper.toEntity(resourceRequestDTO);
        MultipartFile image = resourceRequestDTO.image();
        if(image != null){
            String imageKey = storageService.upload(image);
            entity.setImage(imageKey);
        }
        User ownerProxy = userRepository.getReferenceById(ownerId);
        entity.setOwner(ownerProxy);

        resourceRepo.save(entity);
        return resourceMapper.toDTO(entity);
    }

}
