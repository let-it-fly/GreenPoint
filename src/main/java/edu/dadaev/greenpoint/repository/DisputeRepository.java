package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Dispute;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    @EntityGraph(attributePaths = {"reservation", "resource", "owner", "renter"} )
    @Override
    Optional<Dispute> findById(Long Long);
}
