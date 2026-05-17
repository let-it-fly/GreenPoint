package edu.dadaev.greenpoint.dto;

import java.io.Serializable;

/**
 * DTO for {@link edu.dadaev.greenpoint.entity.User}
 */
public record UserResponseDTO(Long id, String firstName, String lastName, String email) implements Serializable {
}