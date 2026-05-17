package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
//    @Mapping(source = "resourceId", target = "resource.id")
//    Reservation toEntity(ReservationResponseDTO reservationResponseDTO);

    @Mapping(source = "resource.id", target = "resourceId")
    ReservationResponseDTO toDto(Reservation reservation);

    @Mapping(source = "resourceId", target = "resource.id")
    Reservation toEntity(CreateReservationDTO createReservationDTO);

}