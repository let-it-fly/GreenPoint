package edu.dadaev.greenpoint.dto;


import java.io.Serializable;


public record UpdateUserDTO(String firstName, String lastName) implements Serializable {
}