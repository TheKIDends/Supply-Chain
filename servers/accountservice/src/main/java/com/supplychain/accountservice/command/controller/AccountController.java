package com.supplychain.accountservice.command.controller;

import com.supplychain.accountservice.command.data.Account;
import com.supplychain.accountservice.command.model.AccountDTO;
import com.supplychain.accountservice.command.service.AccountService;
import com.supplychain.accountservice.enumeration.AccountRole;
import com.supplychain.accountservice.enumeration.Designation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.base-path}")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/list-user")
    public List<Account> getAllUser() {
        return accountService.getAllUser();
    }

    @PostMapping("/register")
    public AccountDTO register(@RequestBody AccountDTO dto) {
        if (Objects.equals(dto.getRole(), AccountRole.BUSINESS))
            dto.setDesignation(Designation.MANAGER);

        return accountService.saveUser(dto);
    }

    @PostMapping("/login")
    public AccountDTO login(@RequestBody AccountDTO dto) {
        return accountService.login(dto.getPhoneNumber(), dto.getPassword());
    }
}