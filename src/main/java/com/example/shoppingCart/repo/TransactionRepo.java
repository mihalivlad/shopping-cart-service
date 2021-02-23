package com.example.shoppingCart.repo;

import com.example.shoppingCart.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();
    List<Transaction> findAllByOrderByTransactionDateDesc();
    List<Transaction> findAllByUsername(String username);

}
