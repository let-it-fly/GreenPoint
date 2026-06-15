package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Dispute;
import edu.dadaev.greenpoint.util.StorageLinkBuilder;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = StorageLinkBuilder.class)
public interface DisputeMapper {

    @Mapping(target = "image", ignore = true)
    Dispute toEntity(DisputeRequestDTO disputeRequestDTO);

    @Mapping(source = "reservation.id", target = "reservationId")
    @Mapping(source = "resource.id", target = "resourceId")
    @Mapping(source = "resource.name", target = "resourceName")
    @Mapping(source = "dispute.image", target = "image", qualifiedByName = "buildFullUrl")
    DisputeResponseDTO toDto(Dispute dispute);


}