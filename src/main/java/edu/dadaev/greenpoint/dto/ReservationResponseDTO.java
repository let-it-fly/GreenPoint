package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.enumerated.ReservationStatus;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponseDTO(@JsonSerialize(using = ToStringSerializer.class) Long id,
                                     @JsonSerialize(using = ToStringSerializer.class) Long resourceId,
                                     @JsonSerialize(using = ToStringSerializer.class) LocalDate startDate,
                                     @JsonSerialize(using = ToStringSerializer.class) LocalDate endDate,
                                     BigDecimal totalAmount,
                                     ReservationStatus status) implements Serializable {
}