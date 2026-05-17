package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.ResourceMapper;
import edu.dadaev.greenpoint.dto.ResourceResponseDTO;
import edu.dadaev.greenpoint.dto.ResourceRequestDTO;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceService {

    private ResourceRepository resourceRepo;
    private final ResourceMapper resourceMapper;

    public List<ResourceResponseDTO> getResources(){
        return resourceRepo.findAll().stream().map(resourceMapper::toDTO).toList();
    }

    public ResourceResponseDTO createResourse(ResourceRequestDTO resourceRequestDTO){
        Resource entity = resourceMapper.toEntity(resourceRequestDTO);
        resourceRepo.save(entity);
        return resourceMapper.toDTO(entity);
    }

}
