package edu.dadaev.greenpoint.service.reservation;

import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.enumerated.ReservationStatus;
import edu.dadaev.greenpoint.enumerated.ResourceStatus;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.service.BillingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationStateService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationCancellationService reservationCancellationService;
    private final BillingService billingService;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void activate(Long reservationId){
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId).orElseThrow(EntityNotFoundException::new);
        Resource resource = resourceRepository.findByIdForUpdate(reservation.getResource().getId()).orElseThrow(EntityNotFoundException::new);
        if (resource.getStatus() != ResourceStatus.AVAILABLE){
            reservationCancellationService.cancelReservationAndRefundMoney(reservation);
            return; // выбросить checked exception вместо return
        }
        resource.setStatus(ResourceStatus.NOT_AVAILABLE);
        reservation.setStatus(ReservationStatus.IN_USE);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processOverdue(Long reservationId){
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId).orElseThrow(EntityNotFoundException::new);
        reservation.setStatus(ReservationStatus.OVERDUE);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processExpiredOverdue(Long reservationId){
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId).orElseThrow(EntityNotFoundException::new);
        reservation.setStatus(ReservationStatus.TERMINATED_WITH_PENALTY);
        billingService.payMoney(reservation.getResource().getOwner().getId(), reservation.getTotalAmount());
    }


}
