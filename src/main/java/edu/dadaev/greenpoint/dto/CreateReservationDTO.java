package edu.dadaev.greenpoint.dto;

import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDate;


public record CreateReservationDTO(@JsonSerialize(using = ToStringSerializer.class) Long resourceId, LocalDate startDate,
                                   LocalDate endDate) implements Serializable {
}