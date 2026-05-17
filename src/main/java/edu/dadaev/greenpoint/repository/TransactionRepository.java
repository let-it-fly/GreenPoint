package edu.dadaev.greenpoint.repository;

import edu.dadaev.greenpoint.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long id);


}
