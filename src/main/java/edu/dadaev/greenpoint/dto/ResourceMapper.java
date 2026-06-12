package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.service.StorageService;
import edu.dadaev.greenpoint.util.StorageLinkBuilder;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StorageLinkBuilder.class})
public interface ResourceMapper {


    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "buildFullUrl")
    ResourceResponseDTO toDTO(Resource resource);

    @Mapping(target = "image", ignore = true)
    Resource toEntity(ResourceRequestDTO resourceRequestDto);



}
