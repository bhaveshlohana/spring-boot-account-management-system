package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountAccountNumber(int accountNumber);

//    @Query("SELECT t.* FROM transaction as t WHERE account_account_number=:accountNumber AND DATE(t.transaction_date) BETWEEN :startDate AND DATE_ADD(:endDate, INTERVAL 1 DAY)")
//    List<Transaction> findByTransactionDateBetween(@Param("accountNumber") int accountNumber, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
