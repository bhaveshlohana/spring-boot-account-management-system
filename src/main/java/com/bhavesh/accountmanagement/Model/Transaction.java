package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(unique = true)
    private long transactionReferenceNumber;
    private long refNumber;
    private LocalDateTime transactionDate;
    @Column(columnDefinition = "enum('Debit', 'Credit')")
    private String type;
    @Column(columnDefinition = "enum('Cash', 'Transfer')")
    private String subType;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Account account;

}