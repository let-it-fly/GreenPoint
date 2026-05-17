package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.CreateReservationDTO;
import edu.dadaev.greenpoint.dto.ReservationResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ReservationController {
    private ReservationService reservationService;

    @GetMapping("/reservations/me")
    public List<ReservationResponseDTO> reservations(@AuthenticationPrincipal CustomUserDetails userDetails){
        return reservationService.reservations(userDetails.getId());

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody CreateReservationDTO createReservationDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        ReservationResponseDTO reservation = reservationService.createReservation(createReservationDTO, userDetails.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }
}
