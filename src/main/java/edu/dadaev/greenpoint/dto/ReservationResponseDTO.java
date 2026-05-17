package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.enumerated.ReservationPaymentStatus;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDate;

public record ReservationResponseDTO(@JsonSerialize(using = ToStringSerializer.class) Long id,
                                     @JsonSerialize(using = ToStringSerializer.class) Long resourceId,
                                     @JsonSerialize(using = ToStringSerializer.class) LocalDate startDate,
                                     @JsonSerialize(using = ToStringSerializer.class) LocalDate endDate,
                                     int totalAmount,
                                     ReservationPaymentStatus status) implements Serializable {
}