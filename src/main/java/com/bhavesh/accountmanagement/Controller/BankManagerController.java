package com.bhavesh.accountmanagement.Controller;

import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Service.BankManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class BankManagerController {
    @Autowired
    BankManagerService bankManagerService;

    private static final Logger logger = LoggerFactory.getLogger(BankManagerController.class);

    @GetMapping("/find-account/{panCardNumber}")
    public String findCustomerByPanCardNumber(@PathVariable int panCardNumber) {
        logger.info("findCustomerByPanCardNumber - Finding Account By Pancard {}", panCardNumber);
        return bankManagerService.findCustomerByPanCardNumber(panCardNumber);
    }

    @PostMapping("/create-new-account")
    public String createNewAccount(@RequestBody Customer customer) {
        logger.info("createNewAccount - Manager creating new account.");
        return bankManagerService.saveNewAccount(customer);
    }
}
