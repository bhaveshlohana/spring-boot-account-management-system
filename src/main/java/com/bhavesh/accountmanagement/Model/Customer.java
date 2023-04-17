package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    //(Customer ID, PAN Card, Aadhar Number, Name, Postal Address,
    // Email, PAN Card, Date of Birth)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int customerId;
    @Column(nullable = false, unique = true)
    private int PanCardNumber;
    @Column(nullable = false, unique = true)
    private int AadhaarNumber;
    private String Name;
    private String Address;
    @Column(nullable = false, unique = true)
    private String Email;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date DOB;
}