package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);


    @Query("""
    SELECT new edu.dadaev.greenpoint.dto.SummaryDTO(u.balance)
    FROM app_user u
    WHERE u.id = :userId
""")
    Optional<SummaryDTO> getUserSummary(@Param("userId")Long userId);


    @Modifying
    @Query("""
    UPDATE app_user u
    SET u.balance = u.balance + :amount
    WHERE u.id = :userId
""")
    int incrementBalance(Long userId, BigDecimal amount);

    @Modifying
    @Query("""
    UPDATE app_user u
    SET u.balance = u.balance - :amount
    WHERE u.id = :userId AND u.balance > :amount
    """)
    int decreaseBalance(Long userId, BigDecimal amount);

}
