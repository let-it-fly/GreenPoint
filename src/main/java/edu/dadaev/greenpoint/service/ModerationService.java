package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.*;
import edu.dadaev.greenpoint.entity.Dispute;
import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.enumerated.DisputeStatus;
import edu.dadaev.greenpoint.enumerated.ReservationStatus;
import edu.dadaev.greenpoint.enumerated.Resolution;
import edu.dadaev.greenpoint.exception.DisputeClosedException;
import edu.dadaev.greenpoint.repository.DisputeRepository;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final DisputeRepository disputeRepository;
    private final DisputeMapper disputeMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final BillingService billingService;


    @Transactional
    public ReservationResponseDTO createDispute(Long reservationId, DisputeRequestDTO disputeRequestDTO, Long userId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new EntityNotFoundException("бронирование не найдено"));
        User owner = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("владелец ресурса не найден"));
        if (!reservation.getResource().getOwner().getId().equals(userId)){
            throw new AccessDeniedException("нельзя создать спор если ты не владелец ресурса");
        }
        if (reservation.getStatus() != ReservationStatus.PENDING_RETURN){
            throw new IllegalStateException("нельзя создать спор когда арендатор не вернул товар");
        }
        String imageKey = storageService.upload(disputeRequestDTO.image());

        Dispute dispute = new Dispute();
        dispute.setImage(imageKey);
        dispute.setReservation(reservation);
        dispute.setStatus(DisputeStatus.OPEN);
        dispute.setOwner(owner);
        dispute.setRenter(reservation.getUser());
        dispute.setResource(reservation.getResource());
        dispute.setDescription(disputeRequestDTO.description());

        disputeRepository.save(dispute);

        return reservationMapper.toDto(reservation);

    }

    public List<DisputeResponseDTO> getDisputes(){
        return disputeRepository.findAll().stream().map(disputeMapper::toDto).toList();
    }

    public void resolveDispute(Long disputeId, ResolutionRequestDTO resolutionRequestDTO){
        Dispute dispute = disputeRepository.findById(disputeId).orElseThrow(()-> new EntityNotFoundException("спор не найден"));
        if (dispute.getStatus() != DisputeStatus.OPEN){
            throw new DisputeClosedException();
        }
        Reservation reservation = dispute.getReservation();
        Resource resource = dispute.getResource();

        if (resolutionRequestDTO.resolution() == Resolution.FAVOR_OWNER){
            billingService.payMoney(dispute.getOwner().getId(), reservation.getTotalAmount());
            dispute.setStatus(DisputeStatus.CLOSED);
        }
        else {
            billingService.payMoney(reservation.getUser().getId(), resource.getSecurityDeposit());
            dispute.setStatus(DisputeStatus.CLOSED);

        }
    }
}
