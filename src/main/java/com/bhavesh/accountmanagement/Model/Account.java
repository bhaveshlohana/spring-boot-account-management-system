package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountNumber;
    @ManyToOne
    private Customer customer;
    private double balance;

}