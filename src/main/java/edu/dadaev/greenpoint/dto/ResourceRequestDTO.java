package edu.dadaev.greenpoint.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;


public record ResourceRequestDTO(String name,@Positive @NotNull @Digits(integer = 8, fraction = 2) BigDecimal price, @Positive @NotNull @Digits(integer = 8, fraction = 2) BigDecimal securityDeposit, MultipartFile image) implements Serializable {
}