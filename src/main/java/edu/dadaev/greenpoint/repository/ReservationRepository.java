package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long id);

    @EntityGraph(attributePaths = "resource")
    List<Reservation> findAllByResourceId(Long id);


    @Query("""
    SELECT COUNT(r) > 0
    FROM Reservation r
    WHERE r.startDate <= :endDate AND r.endDate >= :startDate
    AND r.resource.id = :resourceId
    AND r.status = 'HOLD'
    """)
    boolean isFree(Long resourceId, LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT r
    FROM Reservation r
    JOIN FETCH r.resource res
    WHERE res.owner.id = :id
""")
    List<Reservation> findAllOwnedReservationsByUserId(Long id);




}
