package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UpdateUserRequestDTO(String firstName, String lastName) implements Serializable {
}