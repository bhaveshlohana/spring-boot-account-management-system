package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerId(int customerId);
}
