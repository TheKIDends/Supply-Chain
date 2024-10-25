package com.supplychain.accountservice.command.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.supplychain.accountservice.command.data.Account;
import com.supplychain.accountservice.command.model.AccountDTO;
import com.supplychain.accountservice.command.repository.AccountRepository;
import com.supplychain.accountservice.enumeration.Designation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Account> getAllUser() {
        return accountRepository.findAll();
    }

    public AccountDTO saveUser(AccountDTO accountDTO) {
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        return AccountDTO.entityToDTO(accountRepository.save(AccountDTO.dtoToEntity(accountDTO)));
    }

    public AccountDTO login(String phoneNumber, String password) {
        Account account = accountRepository.findAccountByPhoneNumber(phoneNumber);
        AccountDTO dto = new AccountDTO();
        if (account != null) {
            BeanUtils.copyProperties(account, dto);
            if (passwordEncoder.matches(password, dto.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                String accessToken = JWT.create()
                        .withSubject(account.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);

                String refreshtoken = JWT.create()
                        .withSubject(account.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                dto.setToken(accessToken);
                dto.setRefreshtoken(refreshtoken);
            }
        }
        return dto;
    }
}