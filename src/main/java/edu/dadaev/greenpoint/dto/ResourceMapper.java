package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Resource;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResourceMapper {


    ResourceResponseDTO toDTO(Resource resource);

    Resource toEntity(ResourceRequestDTO resourceRequestDto);

}
