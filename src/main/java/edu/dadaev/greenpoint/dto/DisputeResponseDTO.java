package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.enumerated.DisputeStatus;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link edu.dadaev.greenpoint.entity.Dispute}
 */
public record DisputeResponseDTO(@JsonSerialize(using = ToStringSerializer.class) Long id,
                                 @JsonSerialize(using = ToStringSerializer.class) Long reservationId,
                                 @JsonSerialize(using = ToStringSerializer.class) Long resourceId,
                                 String resourceName,
                                 UserResponseDTO renter,
                                 UserResponseDTO owner,
                                 String description,
                                 DisputeStatus status,
                                 String image,
                                 Instant createdAt) implements Serializable {
}