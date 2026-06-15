package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long id);

    @EntityGraph(attributePaths = "resource")
    List<Reservation> findAllByResourceId(Long id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT r
    FROM Reservation r
    WHERE r.startDate <= :endDate AND r.endDate >= :startDate
    AND r.resource.id = :resourceId
    AND r.status IN ('CONFIRMED', 'IN_USE')
    """)
    List<Reservation> findConfirmedReservations(Long resourceId, LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT r
    FROM Reservation r
    JOIN FETCH r.resource res
    WHERE res.owner.id = :id
""")
    List<Reservation> findAllOwnedReservationsByUserId(Long id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT r
    FROM Reservation r
    WHERE r.id = :id
""")
    Optional<Reservation> findByIdForUpdate(Long id);

    @Query("""
    SELECT r.id
    FROM Reservation r
    WHERE r.startDate = :today AND r.status = 'CONFIRMED'
    """)
    List<Long> findIdsToActivate(LocalDate today);

    @Query("""
    SELECT r.id
    FROM Reservation r
    WHERE r.endDate < :today
    AND r.status = 'IN_USE'
    """)
    List<Long> findExpiredIds(LocalDate today);

    @Query("""
    SELECT r.id
    FROM Reservation r
    WHERE r.endDate < :deadline
    AND r.status = 'OVERDUE'
    """)
    List<Long> findExpiredOverdues(LocalDate deadline);







}
