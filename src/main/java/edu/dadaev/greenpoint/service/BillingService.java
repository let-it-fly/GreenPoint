package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.dto.TransactionMapper;
import edu.dadaev.greenpoint.dto.TransactionResponseDTO;
import edu.dadaev.greenpoint.repository.TransactionRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillingService {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private UserRepository userRepository;

    public List<TransactionResponseDTO> getTransactions(Long id){
        return transactionRepository.findByUserId(id).stream().map(transactionMapper::toDto).toList();
    }

    public Optional<SummaryDTO> getUserSummary(Long userId){
        return userRepository.getUserSummary(userId);
    }
}
