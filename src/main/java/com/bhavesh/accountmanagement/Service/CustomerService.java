package com.bhavesh.accountmanagement.Service;

import com.bhavesh.accountmanagement.DAO.AccountRepository;
import com.bhavesh.accountmanagement.DAO.CustomerRepository;
import com.bhavesh.accountmanagement.DAO.TransactionRepository;
import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Customer getCustomerDetails(int userId) {
        return customerRepository.findByUserId(userId).get();
    }

    public List<Account> getAllLinkedAccounts(int userId) {
        Customer customer = getCustomerDetails(userId);
        List<Account> accounts = new ArrayList<>();
        accountRepository.findByCustomerId(customer.getCustomerId()).forEach(accounts::add);
        return accounts;
    }

    public List<Transaction> getMiniStatement(int userId, int accountNumber) {
//        Customer customer = getCustomerDetails(userId);
//        List<Account> accounts = getAllLinkedAccounts(userId);
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
        Collections.sort(transactions, (t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
        return transactions.subList(0, Math.min(transactions.size(), 5));

    }

    public List<Transaction> getDateSpecificStatement(int userId, int accountNumber, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);

        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getTransactionDate().isAfter(startDate.atStartOfDay()) && t.getTransactionDate().isBefore(endDate.atStartOfDay()))
                .collect(Collectors.toList());

        return filteredTransactions;

    }
}
