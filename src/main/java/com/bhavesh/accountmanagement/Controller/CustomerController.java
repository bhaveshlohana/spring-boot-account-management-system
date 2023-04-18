package com.bhavesh.accountmanagement.Controller;

import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Transaction;
import com.bhavesh.accountmanagement.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/get-all-linked-accounts/{userId}")
    public List<Account> getAllLinkedAccounts(@PathVariable int userId) {
        logger.info("Fetching all accounts linked to user with id {}.", userId);
        return customerService.getAllLinkedAccounts(userId);
    }

    @GetMapping("/view-mini-statement/{userId}/{accountNumber}")
    public List<Transaction> getMiniStatement(@PathVariable int userId, @PathVariable int accountNumber) {
        logger.info("Viewing mini statement of Account Number {} belonging to user with id {}.", accountNumber, userId);
        return customerService.getMiniStatement(userId, accountNumber);
    }

    @GetMapping("/view-date-specific-statement/{userId}/{accountNumber}/{startDate}/{endDate}")
    public List<Transaction> getDateSpecificStatement(@PathVariable int userId, @PathVariable int accountNumber, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        logger.info("Viewing transaction statement between date {} and {} of Account Number {} belonging to user with id {}.", startDate, endDate, accountNumber, userId);
        return customerService.getDateSpecificStatement(userId, accountNumber, startDate, endDate);
    }

    @GetMapping("/amount-withdrawn-today/{userId}/{accountNumber}")
    public double getAmountWithdrawnToday(@PathVariable int accountNumber) {
        logger.info("Viewing amount withdrawn by Account Number {}.", accountNumber);
        return customerService.getAmountWithdrawnToday(accountNumber);
    }

    @PostMapping("/cash-deposit/{userId}/{accountNumber}")
    public String cashDeposit(@PathVariable int userId, @PathVariable int accountNumber, @RequestBody Transaction transaction) {
        logger.info("Cash deposit in account number {}.", accountNumber);
        return customerService.cashDeposit(userId, accountNumber, transaction);
    }

    @PostMapping("/cash-withdrawal/{userId}/{accountNumber}")
    public String cashWithdrawal(@PathVariable int userId, @PathVariable int accountNumber, @RequestBody Transaction transaction) {
        logger.info("Cash withdrawal from account number {}.", accountNumber);
        return customerService.cashWithdrawal(userId, accountNumber, transaction);
    }

    @PostMapping("/account-transfer/{userId}/{accountNumber}")
    public String accountTransfer(@PathVariable int userId, @PathVariable int accountNumber, @RequestBody Transaction transaction) {
        logger.info("Cash transfer through account by user {} via account number {}.", userId, accountNumber);
        return customerService.accountTransfer(userId, accountNumber, transaction);
    }


}
