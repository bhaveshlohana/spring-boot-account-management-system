package com.bhavesh.accountmanagement.Service;

import com.bhavesh.accountmanagement.DAO.AccountRepository;
import com.bhavesh.accountmanagement.DAO.CustomerRepository;
import com.bhavesh.accountmanagement.DAO.UserRepository;
import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Model.Role;
import com.bhavesh.accountmanagement.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class BankManagerService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    public String findCustomerByPanCardNumber(int panCardNumber) {
        Optional<Customer> customer = customerRepository.findByPanCardNumber(panCardNumber);
        if (customer.isEmpty()) {
            return "Customer with the given Pan Card Number does not exists.";
        }
        return "Customer Exists!";
    }

    public String saveNewAccount(Customer customer) {
        Optional<Customer> customerCheck = customerRepository.findByPanCardNumber(customer.getPanCardNumber());
        if (customerCheck.isEmpty()) {
            System.out.println("Here in saveNewAccount");

            System.out.println("Initialising new User object");
            User user = new User();
            user.setUserId(customer.getCustomerId());
            user.setPassword("Temporary1234");

            System.out.println("Initialising new Role object");
            Role role = new Role();
            role.setRoleId(2);
            user.setRole(role);

            userRepository.save(user);
            System.out.println("Saving new user");
            customer.setUser(user);


            Account account = new Account();
            System.out.println("After new Account()");
            account.setCustomer(customer);
            System.out.println("After Set Customer");
            account.setBalance(0);
            System.out.println("After Set Balance");
            accountRepository.save(account);
            System.out.println(account);
            System.out.println("Saving new account");


            customerRepository.save(customer);
            System.out.println("Saving new customer");

            return "You are our new customer now! Your account has been created with $0 balance. Your User ID is " + user.getUserId() + "and your password is " + user.getPassword() + ". You are requested to change the password quickly.";

        } else {
            Customer tempCustomer = customerRepository.findById(customer.getCustomerId()).get();
            Account account = new Account();
            account.setCustomer(tempCustomer);
            account.setBalance(0);
            accountRepository.save(account);
            return "You are our existing customer! Your account has been created with $0 balance.";
        }

    }

}
