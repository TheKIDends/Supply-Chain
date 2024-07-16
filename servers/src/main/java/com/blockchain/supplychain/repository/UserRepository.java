package com.blockchain.supplychain.repository;

import com.blockchain.supplychain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersById(Long id);
    User findUsersByEmail(String email);
}