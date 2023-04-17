package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "transaction")
public class Transaction {
//     (Transaction ID, Transaction reference number, Date Time,
//    Type (Debit/Credit), SubType (Cash/Transfer), Current Balance
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer transactionId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, unique = true)
    private long transactionReferenceNumber;
    private LocalDateTime transactionDate;
    @Column(columnDefinition = "enum('Debit', 'Credit')")
    private String type;
    @Column(columnDefinition = "enum('Cash', 'Transfer')")
    private String subType;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private double balance;
//    @Column(nullable = false)
    private int toAccount;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Account account;

}