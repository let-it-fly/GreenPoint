package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Reservation;
import edu.dadaev.greenpoint.entity.Resource;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT r
    FROM Resource r
    WHERE r.id = :id
""")
    Optional<Resource> findByIdForUpdate(Long id);
}
