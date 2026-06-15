package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.DisputeRequestDTO;
import edu.dadaev.greenpoint.dto.DisputeResponseDTO;
import edu.dadaev.greenpoint.dto.ReservationResponseDTO;
import edu.dadaev.greenpoint.enumerated.Resolution;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.ModerationService;
import edu.dadaev.greenpoint.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModerationController {
    private final ModerationService moderationService;

    @PostMapping("/reservations/{reservationId}/disputes")
    public ResponseEntity<ReservationResponseDTO> createDispute(@PathVariable("reservationId") Long reservationId, @ModelAttribute DisputeRequestDTO disputeRequestDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(moderationService.createDispute(reservationId, disputeRequestDTO, userDetails.getId()));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/moderation/disputes")
    public ResponseEntity<List<DisputeResponseDTO>> getDisputes(){
        return ResponseEntity.ok(moderationService.getDisputes());
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderation/disputes/{disputeId}/resolve")
    public void resolveDispute(@PathVariable("disputeId") Long disputeId, @RequestBody Resolution resolutionEnum){
        moderationService.resolveDispute(disputeId, resolutionEnum);

    }


}
