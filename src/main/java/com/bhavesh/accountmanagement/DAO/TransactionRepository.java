package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}