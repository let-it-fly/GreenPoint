package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.DepositRequestDTO;
import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.dto.TransactionResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.BillingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class BillingController {

    private BillingService billingService;


    @GetMapping("billing/transactions")
    public ResponseEntity<Slice<TransactionResponseDTO>> getTransactions(@AuthenticationPrincipal CustomUserDetails userDetails, Pageable pageable){
        Slice<TransactionResponseDTO> transactions = billingService.getTransactions(userDetails.getId(), pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("billing/summary")
    public ResponseEntity<SummaryDTO> getUserSummary(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getId();
        return billingService.getUserSummary(userId).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("billing/deposit")
    public void deposit(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody DepositRequestDTO amount){
        billingService.depositMoney(userDetails.getId(), amount);

    }

}
