package com.bhavesh.accountmanagement.Service;

import com.bhavesh.accountmanagement.DAO.AccountRepository;
import com.bhavesh.accountmanagement.DAO.CustomerRepository;
import com.bhavesh.accountmanagement.DAO.TransactionRepository;
import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Customer getCustomerDetails(int userId) {
        return customerRepository.findByUserUserId(userId).orElse(null);
    }

    public List<Account> getAllLinkedAccounts(int userId) {
        Customer customer = getCustomerDetails(userId);
        List<Account> accounts = new ArrayList<>();
        accountRepository.findByCustomerCustomerId(customer.getCustomerId()).forEach(accounts::add);
//        userId is the same as customerId
//        accountRepository.findByCustomerCustomerId(userId).forEach(accounts::add);
        return accounts;
    }

    public List<Transaction> getMiniStatement(int userId, int accountNumber) {
//        Customer customer = getCustomerDetails(userId);
//        List<Account> accounts = getAllLinkedAccounts(userId);
        List<Transaction> transactions = transactionRepository.findByAccountAccountNumber(accountNumber);
        Collections.sort(transactions, (t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
        return transactions.subList(0, Math.min(transactions.size(), 5));

    }

    public List<Transaction> getDateSpecificStatement(int userId, int accountNumber, LocalDate startDate, LocalDate endDate) {
        endDate = endDate.plusDays(1);
        List<Transaction> transactions = transactionRepository.findByAccountAccountNumber(accountNumber);
//        System.out.println("Unfiltered transactions");
//        transactions.forEach(t -> System.out.println(t.toString()));
        LocalDate finalEndDate = endDate;
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getTransactionDate().toLocalDate().isAfter(startDate) && t.getTransactionDate().toLocalDate().isBefore(finalEndDate))
                .collect(Collectors.toList());
//        System.out.println("Filtered transactions");
//        filteredTransactions.forEach(t -> System.out.println(t.toString()));
//        List<Transaction> filteredTransactions = transactionRepository.findByTransactionDateBetween(accountNumber, startDate, endDate);
        return filteredTransactions;

    }

    public String cashDeposit(int userId, int accountNumber, Transaction transaction) {
        Customer customer = getCustomerDetails(userId);
        Optional<Account> accountOptional  = accountRepository.findById(accountNumber);

        if  (accountOptional.isPresent() && transaction.getAmount()>0) {
//            Transaction newTransaction = new Transaction();
            Account account = accountOptional.get();
            long transactionReferenceNumber = generateTransactionReferenceNumber();
            transaction.setTransactionReferenceNumber(transactionReferenceNumber);
            LocalDateTime current = generateCurrentLocalDateTime();
            transaction.setTransactionDate(current);
            transaction.setType("Credit");
            transaction.setSubType("Cash");
            double amount = transaction.getAmount();
            double balance = account.getBalance();
            double newBalance = balance + amount;
            transaction.setBalance(newBalance);
            account.setBalance(newBalance);
            Account savedAccount = accountRepository.save(account);
            transaction.setAccount(savedAccount);
            Transaction savedTransaction = transactionRepository.save(transaction);
            return "Transaction Completed. Details: " + savedTransaction.toString();
        }
        return "Account " + accountNumber + " does not exist!!";
    }

    public String cashWithdrawal(int userId, int accountNumber, Transaction transaction) {
//        Customer customer = getCustomerDetails(userId);
        Optional<Account> accountOptional  = accountRepository.findById(accountNumber);

        if  (accountOptional.isPresent() && transaction.getAmount()>0) {
//            List<Transaction> transactionsOccurredToday = getDateSpecificStatement(userId, accountNumber,
//            LocalDate.now().atStartOfDay().toLocalDate(),
//            LocalDate.now().atTime(LocalTime.MAX).toLocalDate());
//            double totalWithdrawal = transactionsOccurredToday.stream()
//                    .filter(t -> t.getType().equals("Debit") && t.getSubType().equals("Cash"))
//                    .mapToDouble(Transaction::getAmount)
//                    .sum();

            double amountWithdrawnToday = getAmountWithdrawnToday(accountNumber);
            System.out.println("Amount withdrawn today: "+amountWithdrawnToday);
            // Check if withdrawal amount exceeds daily limit
            if (amountWithdrawnToday == 10000) {
                throw new RuntimeException("Withdrawal limit exceeded");
            }
            if (amountWithdrawnToday + transaction.getAmount() > 10000) {
                double amountThatCanBeWithdrawnToday = 10000 - amountWithdrawnToday;
                return "The amount you are withdrawing way too much as you are close to exceed the daily withdrawal limit. You can only withdraw ₹" + amountThatCanBeWithdrawnToday + " today.";
            }
//            Transaction newTransaction = new Transaction();
            Account account = accountOptional.get();
            long transactionReferenceNumber = generateTransactionReferenceNumber();
            transaction.setTransactionReferenceNumber(transactionReferenceNumber);
            LocalDateTime current = generateCurrentLocalDateTime();
            transaction.setTransactionDate(current);
            transaction.setType("Debit");
            transaction.setSubType("Cash");
            double amount = transaction.getAmount();
            double balance = account.getBalance();
            double newBalance = balance - amount;
            if(newBalance < 1000) {
                return "Transaction Failed. Insufficient balance! Please try again with lesser amount.";
            }
            transaction.setBalance(newBalance);
            account.setBalance(newBalance);
            Account savedAccount = accountRepository.save(account);
            transaction.setAccount(savedAccount);
            Transaction savedTransaction = transactionRepository.save(transaction);
            return "Transaction Completed. Details: " + savedTransaction.toString();
        }
        return "Account " + accountNumber + " does not exist!!";
    }

    public String accountTransfer(int userId, int fromAccountNumber, Transaction fromTransaction) {
        Customer customer = getCustomerDetails(userId);
        Optional<Account> fromAccountOptional  = accountRepository.findById(fromAccountNumber);
        Optional<Account> toAccountOptional = accountRepository.findById(fromTransaction.getToAccount());

        if  (fromAccountOptional.isPresent() && toAccountOptional.isPresent() && fromTransaction.getAmount()>0) {
//            Transaction newTransaction = new Transaction();
            Account fromAccount = fromAccountOptional.get();
            long fromTransactionReferenceNumber = generateTransactionReferenceNumber();
            fromTransaction.setTransactionReferenceNumber(fromTransactionReferenceNumber);
            LocalDateTime fromTransactionCurrent = generateCurrentLocalDateTime();
            fromTransaction.setTransactionDate(fromTransactionCurrent);
            fromTransaction.setType("Debit");
            fromTransaction.setSubType("Transfer");
            double fromAmount = fromTransaction.getAmount();
            double fromBalance = fromAccount.getBalance();
            double fromNewBalance = fromBalance - fromAmount;
            if(fromNewBalance < 1000) {
                return "Transaction Failed. Insufficient balance! Please try again with lesser amount.";
            }
            fromTransaction.setBalance(fromNewBalance);
            fromAccount.setBalance(fromNewBalance);
            Account savedFromAccount = accountRepository.save(fromAccount);
            fromTransaction.setAccount(savedFromAccount);
            Transaction savedFromTransaction = transactionRepository.save(fromTransaction);

            Transaction toTransaction = new Transaction();
            Account toAccount = toAccountOptional.get();
            long toTransactionReferenceNumber = generateTransactionReferenceNumber();
            toTransaction.setTransactionReferenceNumber(toTransactionReferenceNumber);
            LocalDateTime toTransactionCurrent = generateCurrentLocalDateTime();
            toTransaction.setTransactionDate(toTransactionCurrent);
            toTransaction.setType("Credit");
            toTransaction.setSubType("Transfer");
            double toAmount = fromTransaction.getAmount();
            double toBalance = toAccount.getBalance();
            double toNewBalance = toBalance + toAmount;
            toTransaction.setAmount(toAmount);
            toTransaction.setBalance(toNewBalance);
            toAccount.setBalance(toNewBalance);
            Account savedToAccount = accountRepository.save(toAccount);
            toTransaction.setAccount(savedToAccount);
            Transaction savedToTransaction = transactionRepository.save(toTransaction);

            return "Transaction Completed. \n Details of Sender Transaction: " + savedFromTransaction.toString() + "\n Details of Receiver Transaction: " + savedToTransaction.toString() ;
        } else if (fromAccountOptional.isEmpty()) {
            return "Sender Account " + fromAccountNumber + " does not exist!!";
        } else if (toAccountOptional.isEmpty()) {
            return "Receiver Account " + fromTransaction.getToAccount() + " does not exist!!";
        } else if (fromTransaction.getAmount()<0) {
            return "Amount is not sufficient to transfer! Add more amount.";
        }
        return "Encountered an error. Please try again later.";
    }

    public long generateTransactionReferenceNumber() {
        return ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
    }

    public LocalDateTime generateCurrentLocalDateTime(){
        return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

    public double getAmountWithdrawnToday(int accountNumber) {
        LocalDate currentDate = LocalDate.now().atStartOfDay().toLocalDate();
        List<Transaction> transactions = transactionRepository.findByAccountAccountNumber(accountNumber);
        double amountWithdrawnToday = transactions.stream()
                .filter(t -> t.getTransactionDate().toLocalDate().isEqual(currentDate) && t.getType().equals("Debit") && t.getSubType().equals("Cash"))
                .mapToDouble(Transaction::getAmount)
                .sum();
        return amountWithdrawnToday;
    }
}
