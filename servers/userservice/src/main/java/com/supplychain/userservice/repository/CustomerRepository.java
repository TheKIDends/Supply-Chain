package com.supplychain.userservice.repository;

import com.supplychain.userservice.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByUsername(String username);
    Customer findCustomerById(String id);
    Customer findByPhoneNumber(String phoneNumber);
}

