package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Transaction;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toEntity(TransactionResponseDTO transactionResponseDto);

    TransactionResponseDTO toDto(Transaction transaction);


}