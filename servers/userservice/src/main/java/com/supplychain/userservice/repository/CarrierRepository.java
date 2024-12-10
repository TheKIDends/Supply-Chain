package com.supplychain.userservice.repository;

import com.supplychain.userservice.data.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, String> {
    Carrier findByUsername(String username);
    Carrier findCarrierById(String id);
    Carrier findByPhoneNumber(String phoneNumber);
}

