package edu.dadaev.greenpoint.service.reservation;

import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.enumerated.ReservationStatus;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.service.BillingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationCancellationService {
    private final BillingService billingService;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void cancelReservationAndRefundMoney(Reservation reservation){
        User user = reservation.getUser();
        reservation.setStatus(ReservationStatus.CANCELED);
        billingService.payMoney(user.getId(), reservation.getTotalAmount());
    }

    @Transactional
    public void cancelReservationBeforeActivation(Long reservationId, Long userId){
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId).orElseThrow(EntityNotFoundException::new);
        if (!reservation.getUser().getId().equals(userId)){
            throw new AccessDeniedException("бронь не ваша");
        }
        if (reservation.getStatus() != ReservationStatus.CONFIRMED){
            throw new RuntimeException();
        }
        User user = reservation.getUser();
        reservation.setStatus(ReservationStatus.CANCELED);
        billingService.payMoney(user.getId(), reservation.getTotalAmount());
    }
}
