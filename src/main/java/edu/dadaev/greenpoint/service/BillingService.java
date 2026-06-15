package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.dto.TransactionMapper;
import edu.dadaev.greenpoint.dto.TransactionResponseDTO;
import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.Transaction;
import edu.dadaev.greenpoint.enumerated.TransactionStatus;
import edu.dadaev.greenpoint.enumerated.TransactionType;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.TransactionRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public BigDecimal calculatePrice(BigDecimal resourcePricePerDay, BigDecimal amount, LocalDate startDate, LocalDate endDate){
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if(days <= 0){
            throw new IllegalArgumentException("invalid date range");
        }

        return resourcePricePerDay.multiply(BigDecimal.valueOf(days)).add(amount);
    }

    @Transactional
    public void chargeMoney(Long userId, BigDecimal amount){
        int rowsUpdated = userRepository.decreaseBalance(userId, amount);
        if (rowsUpdated == 0){
            saveTransaction(userId,amount, TransactionStatus.FAILED,TransactionType.RENT_SPENT);
            throw new RuntimeException(); // придумать другое исключение
        }
        saveTransaction(userId,amount, TransactionStatus.SUCCESS,TransactionType.RENT_SPENT);
    }

    @Transactional
    public void payMoney(Long userId, BigDecimal amount){
        int rowsUpdated = userRepository.incrementBalance(userId, amount);
        if (rowsUpdated  == 0){
            saveTransaction(userId,amount,TransactionStatus.FAILED,TransactionType.RENT_EARN);
            throw new RuntimeException();//придумать исключение
        }
        saveTransaction(userId,amount, TransactionStatus.SUCCESS, TransactionType.RENT_EARN);
    }

    @Transactional
    public void depositMoney(Long userId, BigDecimal amount){
        int rowsUpdated = userRepository.incrementBalance(userId, amount);
        if (rowsUpdated == 0){
            saveTransaction(userId,amount,TransactionStatus.FAILED,TransactionType.DEPOSIT);
            throw new RuntimeException(); // придумать исключение
        }
        saveTransaction(userId,amount,TransactionStatus.SUCCESS,TransactionType.DEPOSIT);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransaction(Long userId, BigDecimal amount, TransactionStatus status, TransactionType type){
        Transaction transaction = new Transaction();
        transaction.setStatus(status);
        transaction.setAmount(amount);
        transaction.setUser(userRepository.getReferenceById(userId));
        transaction.setType(type);
        transactionRepository.save(transaction);
    }

}
