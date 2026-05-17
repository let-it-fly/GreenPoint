package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Reservation;
import org.springframework.cglib.core.Local;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record CreateReservationDTO(@JsonSerialize(using = ToStringSerializer.class) Long resourceId, LocalDate startDate,
                                   LocalDate endDate) implements Serializable {
}