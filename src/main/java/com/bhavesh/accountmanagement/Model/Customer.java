package com.bhavesh.accountmanagement.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer")
public class Customer {
    //(Customer ID, PAN Card, Aadhar Number, Name, Postal Address,
    // Email, PAN Card, Date of Birth)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;
    @Column(nullable = false, unique = true)
    private int panCardNumber;
    @Column(nullable = false, unique = true)
    private int aadhaarNumber;
    private String name;
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dob;
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;

}

