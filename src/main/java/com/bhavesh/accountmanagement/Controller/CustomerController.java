package com.bhavesh.accountmanagement.Controller;

import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Transaction;
import com.bhavesh.accountmanagement.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @GetMapping("/get-all-linked-accounts/{userId}")
    public List<Account> getAllLinkedAccounts(@PathVariable int userId) {
        return customerService.getAllLinkedAccounts(userId);
    }

    @GetMapping("/view-mini-statement/{userId}/{accountNumber}")
    public List<Transaction> getMiniStatement(@PathVariable int userId, @PathVariable int accountNumber) {
        return customerService.getMiniStatement(userId, accountNumber);
    }

    @GetMapping("/view-mini-statement/{userId}/{accountNumber}/{startDate}/{endDate}")
    public List<Transaction> getDateSpecificStatement(@PathVariable int userId, @PathVariable int accountNumber, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return customerService.getDateSpecificStatement(userId, accountNumber, startDate, endDate);
    }
}
