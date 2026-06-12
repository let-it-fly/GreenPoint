package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.enumerated.ResourceStatus;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ResourceResponseDTO(@JsonSerialize(using = ToStringSerializer.class) Long id,
                                  String name,
                                  BigDecimal price,
                                  BigDecimal amount,
                                  ResourceStatus status,
                                  String imageUrl,
                                  @JsonSerialize(using = ToStringSerializer.class) Long ownerId) {}
