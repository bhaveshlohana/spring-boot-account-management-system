package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
//     (Transaction ID, Transaction reference number, Date Time,
//    Type (Debit/Credit), SubType (Cash/Transfer), Current Balance
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer transactionId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long transactionReferenceNumber;
    private Long refNumber;
    private LocalDateTime transactionDate;
    @Column(columnDefinition = "enum('Debit', 'Credit')")
    private String type;
    @Column(columnDefinition = "enum('Cash', 'Transfer')")
    private String subType;
    @ManyToOne
    private Account account;

}