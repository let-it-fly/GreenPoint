package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Resource;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResourceMapper {


    ResourceResponseDTO toDTO(Resource resource);

    @Mapping(target = "image", ignore = true)
    Resource toEntity(ResourceRequestDTO resourceRequestDto);

}
