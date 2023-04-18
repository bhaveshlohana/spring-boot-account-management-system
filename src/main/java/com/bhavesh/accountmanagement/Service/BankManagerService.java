package com.bhavesh.accountmanagement.Service;

import com.bhavesh.accountmanagement.DAO.AccountRepository;
import com.bhavesh.accountmanagement.DAO.CustomerRepository;
import com.bhavesh.accountmanagement.DAO.RoleRepository;
import com.bhavesh.accountmanagement.DAO.UserRepository;
import com.bhavesh.accountmanagement.Model.Account;
import com.bhavesh.accountmanagement.Model.Customer;
import com.bhavesh.accountmanagement.Model.Role;
import com.bhavesh.accountmanagement.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(BankManagerService.class);

    public String findCustomerByPanCardNumber(int panCardNumber) {
        Optional<Customer> customer = customerRepository.findByPanCardNumber(panCardNumber);
        if (customer.isEmpty()) {
            return "Customer with the Pan Card number " + panCardNumber + " does not exists.";
        }
        return "Customer with PanCard number " + panCardNumber + " Exists!";
    }

    public String saveNewAccount(Customer customer) {
        Optional<Customer> customerCheck = customerRepository.findByPanCardNumber(customer.getPanCardNumber());
        if (customerCheck.isEmpty()) {

            System.out.println("Initialising new User object");
            User user = new User();
//            user.setUserId(customer.getCustomerId());
            user.setPassword("Temporary1234");

            System.out.println("Initialising new Role object");
            Role role = roleRepository.findById(2).get();
            user.setRole(role);

            System.out.println("Saving new user");
            User savedUser = userRepository.save(user);
            System.out.println("Setting new user");
            customer.setUser(user);
            customer.setCustomerId(savedUser.getUserId());
            Customer savedCustomer = customerRepository.save(customer);
            System.out.println("Saving new customer");

            Account account = new Account();
            System.out.println("After new Account()");
            account.setCustomer(savedCustomer);
            System.out.println("After Set Customer");
            account.setBalance(0);
            System.out.println("After Set Balance");
            System.out.println(account);
            accountRepository.save(account);
            System.out.println("Saving new account");

            return "You are our new customer now! Your account has been created with ₹0 balance. Your User ID is " + user.getUserId() + " and your password is " + user.getPassword() + ". You are requested to change the password quickly.";

        } else {
            Customer tempCustomer;
            if(customer.getCustomerId() > 0) {
                tempCustomer = customerRepository.findById(customer.getCustomerId()).get();
            } else {
                tempCustomer = customerRepository.findByPanCardNumber(customer.getPanCardNumber()).get();
            }

            Account account = new Account();
            account.setCustomer(tempCustomer);
            account.setBalance(0);
            accountRepository.save(account);
            return "You are our existing customer! Your account has been created with $0 balance.";
        }

    }

}
