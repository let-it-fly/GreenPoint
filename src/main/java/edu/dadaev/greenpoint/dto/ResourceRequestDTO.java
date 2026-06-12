package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Resource}
 */
public record ResourceRequestDTO(String name, BigDecimal price, BigDecimal amount, MultipartFile image) implements Serializable {
}