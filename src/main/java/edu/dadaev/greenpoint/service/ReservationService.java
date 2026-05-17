package edu.dadaev.greenpoint.service;

import edu.dadaev.greenpoint.dto.CreateReservationDTO;
import edu.dadaev.greenpoint.dto.ReservationMapper;
import edu.dadaev.greenpoint.dto.ReservationResponseDTO;
import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import edu.dadaev.greenpoint.entity.User;
import edu.dadaev.greenpoint.repository.ReservationRepository;
import edu.dadaev.greenpoint.repository.ResourceRepository;
import edu.dadaev.greenpoint.repository.UserRepository;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ResourceRepository resourceRepository;
    private ReservationMapper reservationMapper;
    private UserRepository userRepository;
    private PriceService priceService;

    public List<ReservationResponseDTO> reservations(Long id){
        return reservationRepository.findAllByUserId(id).stream().map(reservationMapper::toDto).toList();
    }

    @Transactional
    public ReservationResponseDTO createReservation(CreateReservationDTO reservationDTO, Long userId){
        Resource resource = resourceRepository.findById(reservationDTO.resourceId()).orElseThrow(RuntimeException::new);//
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new); //

        priceService.calculate(resource, reservationDTO.startDate(), reservationDTO.endDate());

        if(!reservationRepository.isFree(reservationDTO.resourceId(), reservationDTO.startDate(), reservationDTO.endDate())){
            //придумать какое то исключение
            throw new RuntimeException();
        }


        Reservation reservation = new Reservation();
        reservation.setResource(resource);
        reservation.setUser(user);

        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }



}
