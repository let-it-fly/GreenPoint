package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Transaction;
import edu.dadaev.greenpoint.enumerated.TransactionStatus;
import edu.dadaev.greenpoint.enumerated.TransactionType;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Transaction}
 */
public record TransactionResponseDTO(@JsonSerialize(using = ToStringSerializer.class) Long id, TransactionStatus status, TransactionType type,
                                     Instant createdAt) implements Serializable {
}