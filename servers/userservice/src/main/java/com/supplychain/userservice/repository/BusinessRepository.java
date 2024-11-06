package com.supplychain.userservice.repository;

import com.supplychain.userservice.data.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    Business findByUsername(String username);
    Business findBusinessById(String id);
    Business findByPhoneNumber(String phoneNumber);
}

