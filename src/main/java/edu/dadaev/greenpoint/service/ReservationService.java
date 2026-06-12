package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.CreateReservationDTO;
import edu.dadaev.greenpoint.dto.ReservationMapper;
import edu.dadaev.greenpoint.dto.ReservationResponseDTO;
import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.Transaction;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.enumerated.ResourceStatus;
import edu.dadaev.greenpoint.enumerated.TransactionStatus;
import edu.dadaev.greenpoint.enumerated.TransactionType;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.repository.TransactionRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final PriceService priceService;
    private final TransactionRepository transactionRepository;

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
    public ReservationResponseDTO createReservation(CreateReservationDTO reservationDTO, Long userId){
        Resource resource = resourceRepository.findById(reservationDTO.resourceId()).orElseThrow(RuntimeException::new);//
        if(resource.getStatus() != ResourceStatus.AVAILABLE){
            throw new IllegalArgumentException();//придумать исключение
        }

        User renter = userRepository.findById(userId).orElseThrow(RuntimeException::new); //

        BigDecimal totalAmount = priceService.calculate(resource, reservationDTO.startDate(), reservationDTO.endDate());
        BigDecimal availableBalance = renter.getBalance();
        if (availableBalance.compareTo(totalAmount) < 0){
            throw new RuntimeException(); //придумать исключение
        }

        if(!reservationRepository.isFree(reservationDTO.resourceId(), reservationDTO.startDate(), reservationDTO.endDate())){
            //придумать какое то исключение
            throw new RuntimeException();
        }
        Reservation reservation = new Reservation();
        reservation.setResource(resource);
        reservation.setUser(renter);
        reservation.setTotalAmount(totalAmount);

        Transaction transaction = new Transaction();
        transaction.setReservation(reservation);
        transaction.setType(TransactionType.RENT_SPENT);
        transaction.setStatus(TransactionStatus.SUCCESS);


        reservationRepository.save(reservation);
        transactionRepository.save(transaction);

        return reservationMapper.toDto(reservation);
    }



}
