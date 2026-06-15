package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Dispute;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link Dispute}
 */
public record DisputeRequestDTO(String description, MultipartFile image) implements Serializable {
}