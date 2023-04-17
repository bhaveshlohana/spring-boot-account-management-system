package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountNumber;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;
    private double balance;

}