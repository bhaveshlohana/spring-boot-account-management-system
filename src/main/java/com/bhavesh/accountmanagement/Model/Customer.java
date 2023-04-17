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
    private int customerId;
    private int PanCardNumber;
    private int AadhaarNumber;
    private String Name;
    private String Address;
    private String Email;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date DOB;
}