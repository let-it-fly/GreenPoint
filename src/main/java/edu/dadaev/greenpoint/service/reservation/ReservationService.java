package edu.dadaev.greenpoint.service.reservation;

import edu.dadaev.greenpoint.dto.*;
import edu.dadaev.greenpoint.entity.Dispute;
import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.enumerated.DisputeStatus;
import edu.dadaev.greenpoint.enumerated.ReservationStatus;
import edu.dadaev.greenpoint.enumerated.ResourceStatus;
import edu.dadaev.greenpoint.repository.DisputeRepository;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import edu.dadaev.greenpoint.service.BillingService;
import edu.dadaev.greenpoint.service.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final BillingService billingService;
    private final ReservationStateService reservationStateService;

    public List<ReservationResponseDTO> reservations(Long id){
        return reservationRepository.findAllByUserId(id).stream().map(reservationMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsForResource(Long resourceId){
        return reservationRepository.findAllByResourceId(resourceId ).stream().map(reservationMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getOnwedReservationsForUser(Long userId){
        return reservationRepository.findAllOwnedReservationsByUserId(userId).stream().map(reservationMapper::toDto).toList();
    }

    @Transactional
    public void initiateReturn(Long reservationId, Long renterId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Бронирование не найдено"));

        if (reservation.getUser().getId().equals(renterId)){
            throw new AccessDeniedException("Вы не являетесь арендатором ресурса");
        }

        if (reservation.getStatus() != ReservationStatus.IN_USE){
            throw new IllegalStateException("Неверный статус бронирования");
        }

        reservation.setStatus(ReservationStatus.PENDING_RETURN);
    }

    @Transactional
    public void confirmReturn(Long reservationId, Long ownerId){
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId).orElseThrow(() -> new EntityNotFoundException("Бронирование не найдено"));

        if (!reservation.getResource().getOwner().getId().equals(ownerId)){
            throw new AccessDeniedException("Вы не являетесь владельцем ресурса");
        }

        if (reservation.getStatus() != ReservationStatus.PENDING_RETURN){
            throw new IllegalStateException("Неверный статус бронирования");
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        Resource resource = reservation.getResource();
        resource.setStatus(ResourceStatus.AVAILABLE);
        BigDecimal securityDeposit = resource.getSecurityDeposit();

        BigDecimal totalAmountWithoutSecurityDeposit = reservation.getTotalAmount().subtract(securityDeposit);

        billingService.payMoney(ownerId, totalAmountWithoutSecurityDeposit);
        billingService.payMoney(reservation.getUser().getId(), securityDeposit);

    }



    @Transactional
    public ReservationResponseDTO createReservation(CreateReservationDTO reservationDTO, Long userId){
        if (!reservationDTO.startDate().isAfter(LocalDate.now())){
            throw new RuntimeException();// придумать исключение
        }
        if(!reservationRepository.findConfirmedReservations(reservationDTO.resourceId(), reservationDTO.startDate().minusDays(1), reservationDTO.endDate().plusDays(1)).isEmpty()){
            //придумать какое то исключение
            throw new RuntimeException();
        }
        Resource resource = resourceRepository.findById(reservationDTO.resourceId()).orElseThrow(RuntimeException::new);//
//        if(resource.getStatus() != ResourceStatus.AVAILABLE){
//            throw new IllegalArgumentException();//придумать исключение
//        }
        if (resource.getOwner().getId().equals(userId)){
            throw new RuntimeException(); // придумать исключение: нелзя заброинировать свой ресурс
        }

        User renter = userRepository.findById(userId).orElseThrow(RuntimeException::new); //

        BigDecimal totalAmount = billingService.calculatePrice(resource.getPrice(), resource.getSecurityDeposit(), reservationDTO.startDate(), reservationDTO.endDate());
        BigDecimal availableBalance = renter.getBalance();
        if (availableBalance.compareTo(totalAmount) < 0){
            throw new RuntimeException(); //придумать исключение
        }
        Reservation reservation = new Reservation();
        reservation.setResource(resource);
        reservation.setUser(renter);
        reservation.setTotalAmount(totalAmount);
        reservation.setStartDate(reservationDTO.startDate());
        reservation.setEndDate(reservationDTO.endDate());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        reservationRepository.save(reservation);

        billingService.chargeMoney(userId, totalAmount);

        return reservationMapper.toDto(reservation);
    }






    @Scheduled(cron = "0 0 0 * * *")
    public void processDaily(){
        LocalDate today = LocalDate.now();

        List<Long> idsToActivate = reservationRepository.findIdsToActivate(today);
        for (Long id : idsToActivate){
            try {
                reservationStateService.activate(id);
            }catch (Exception e){
                System.out.println("бронь: "+ id + " не активирована");
            }

        }

        List<Long> expiredIds = reservationRepository.findExpiredIds(today);
        for (Long id : expiredIds){
            try {
                reservationStateService.processOverdue(id);
            }
            catch (Exception e){
                System.out.println("бронь "+ id + "не отмечена просроченной");
            }
        }

        List<Long> expiredOverdues = reservationRepository.findExpiredOverdues(today.minusDays(1));
        for (Long id: expiredOverdues){
            try {
                reservationStateService.processExpiredOverdue(id);
            }
            catch (Exception e){
                System.out.println("Бронь " + id + " не закончена со штрафом");
            }
        }

    }
}
