package com.supplychain.accountservice.command.repository;

import com.supplychain.accountservice.command.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountById(String id);
    Account findAccountByPhoneNumber(String phoneNumber);
}

