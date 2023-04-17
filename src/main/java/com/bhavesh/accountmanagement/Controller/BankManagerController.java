package com.bhavesh.accountmanagement.Controller;

import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Service.BankManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankManagerController {
    @Autowired
    BankManagerService bankManagerService;

    @GetMapping("/find-account/{panCardNumber}")
    public String findCustomerByPanCardNumber(@PathVariable int panCardNumber) {
        return bankManagerService.findCustomerByPanCardNumber(panCardNumber);
    }

    @PostMapping("/create-new-account")
    public String createNewAccount(@RequestBody Customer customer) {
        return bankManagerService.saveNewAccount(customer);
    }
}
