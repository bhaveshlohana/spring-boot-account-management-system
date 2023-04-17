package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountAccountNumber(int accountNumber);
}
