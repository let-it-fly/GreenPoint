package edu.dadaev.greenpoint.dto;

import java.math.BigDecimal;

public record SummaryDTO(BigDecimal balance, Long heldAmount) {
}
