package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.Transaction;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toEntity(TransactionResponseDTO transactionResponseDto);

    TransactionResponseDTO toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionResponseDTO transactionResponseDto, @MappingTarget Transaction transaction);
}