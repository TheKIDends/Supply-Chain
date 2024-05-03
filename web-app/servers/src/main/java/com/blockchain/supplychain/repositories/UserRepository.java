package com.blockchain.supplychain.repositories;

import com.blockchain.supplychain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersByUserID(Long userID);
    User findUsersByEmail(String email);
    List<User> findUsersByPhoneNumber(String phoneNumber);
}