package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Resource;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Resource}
 */
public record ResourceRequestDTO(String name, BigDecimal price, Integer amount) implements Serializable {
}