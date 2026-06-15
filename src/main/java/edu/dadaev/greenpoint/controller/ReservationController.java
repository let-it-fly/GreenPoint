package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.CreateReservationDTO;
import edu.dadaev.greenpoint.dto.DisputeRequestDTO;
import edu.dadaev.greenpoint.dto.ReservationResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.reservation.ReservationCancellationService;
import edu.dadaev.greenpoint.service.reservation.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationCancellationService reservationCancellationService;


    @GetMapping("/reservations/me")
    public List<ReservationResponseDTO> reservations(@AuthenticationPrincipal CustomUserDetails userDetails){
        return reservationService.reservations(userDetails.getId());

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody CreateReservationDTO createReservationDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        ReservationResponseDTO reservation = reservationService.createReservation(createReservationDTO, userDetails.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping("/resources/{resourceId}/reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getResourceReservations(@PathVariable("resourceId") Long resourceId){
        return ResponseEntity.ok(reservationService.getReservationsForResource(resourceId));
    }

    @GetMapping("/reservations/owned")
    public ResponseEntity<List<ReservationResponseDTO>> getOwnedReservationsForUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(reservationService.getOnwedReservationsForUser(userDetails.getId()));
    }

    @PostMapping("/reservations/{reservationId}/initiate-return")
    public void initiateReturn(@PathVariable("reservationId") Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails){
        reservationService.initiateReturn(reservationId, userDetails.getId());
    }

    @PostMapping("/reservations/{reservationId}/confirm-return")
    public void confirmReturn(@PathVariable("reservationId") Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails){
        reservationService.confirmReturn(reservationId, userDetails.getId());
    }
    @PostMapping("/reservations/{reservationId}/cancel")
    public void cancelReservation(@PathVariable("reservationId") Long reservationId, @AuthenticationPrincipal CustomUserDetails userDetails){
        reservationCancellationService.cancelReservationBeforeActivation(reservationId,userDetails.getId());
    }






}
