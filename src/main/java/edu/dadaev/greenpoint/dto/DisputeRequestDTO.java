package edu.dadaev.greenpoint.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


public record DisputeRequestDTO(String description, MultipartFile image) implements Serializable {
}