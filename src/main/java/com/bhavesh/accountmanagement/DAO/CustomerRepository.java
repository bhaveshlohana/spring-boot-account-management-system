package com.bhavesh.accountmanagement.DAO;

import com.bhavesh.accountmanagement.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByPanCardNumber(int panCardNumber);
}
