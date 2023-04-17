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
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

            Account account = new Account();
            account.setCustomer(customer);
            account.setBalance(0);
            accountRepository.save(account);

            User user = new User();
            user.setUserId(customer.getCustomerId());
            user.setPassword("Temporary1234");

            Role role = new Role();
            role.setRoleId(2);
            user.setRole(role);
            userRepository.save(user);

            customer.setUser(user);
            customerRepository.save(customer);

            return "You are our new customer now! Your account has been created with $0 balance. Your User ID is " + user.getUserId() + "and your password is " + user.getPassword() + ". You are requested to change the password quickly.";

        } else {
            Account account = new Account();
            account.setCustomer(customer);
            account.setBalance(0);
            accountRepository.save(account);
            return "You are our existing customer! Your account has been created with $0 balance.";
        }

    }

}
