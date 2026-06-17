package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Slice<Transaction> findByUserId(Long id, Pageable pageable);


}
