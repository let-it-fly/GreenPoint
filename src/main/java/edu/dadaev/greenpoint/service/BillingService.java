package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.dto.TransactionMapper;
import edu.dadaev.greenpoint.dto.TransactionResponseDTO;
import edu.dadaev.greenpoint.repository.TransactionRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;

    public List<TransactionResponseDTO> getTransactions(Long id){
        return transactionRepository.findByUserId(id).stream().map(transactionMapper::toDto).toList();
    }

    public Optional<SummaryDTO> getUserSummary(Long userId){
        return userRepository.getUserSummary(userId);
    }
}
