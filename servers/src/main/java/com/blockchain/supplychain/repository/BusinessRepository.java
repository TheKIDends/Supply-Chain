package com.blockchain.supplychain.repository;

import com.blockchain.supplychain.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, String> {

    Business findByUsername(String username);
    Business findBusinessById(String id);
}

