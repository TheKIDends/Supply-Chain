package com.supplychain.userservice.repository;

import com.supplychain.userservice.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    User findUserByUsername(String username);
    User findUserByPhoneNumber(String phoneNumber);
}

