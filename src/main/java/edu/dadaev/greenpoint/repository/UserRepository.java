package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.dto.SummaryDTO;
import edu.dadaev.greenpoint.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);


    @Query("""
    SELECT new edu.dadaev.greenpoint.dto.SummaryDTO(u.balance)
    FROM app_user u
    WHERE u.id = :userId
""")
    Optional<SummaryDTO> getUserSummary(@Param("userId")Long userId);

}
