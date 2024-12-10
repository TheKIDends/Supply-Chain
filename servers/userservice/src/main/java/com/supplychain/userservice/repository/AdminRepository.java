package com.supplychain.userservice.repository;

import com.supplychain.userservice.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUsername(String username);
    Admin findAdminById(String id);
    Admin findByPhoneNumber(String phoneNumber);
}

