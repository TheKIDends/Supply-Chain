package com.supplychain.accountservice.command.model;

import com.supplychain.accountservice.command.data.Account;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private boolean enabled;
    private String role;
    private String designation;

    private String token;
    private String refreshtoken;


    public static AccountDTO entityToDTO(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .fullName(account.getFullName())
                .enabled(account.isEnabled())
                .role(account.getRole())
                .designation(account.getDesignation())
                .build();
    }
    public static Account dtoToEntity(AccountDTO dto){
        return Account.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .fullName(dto.getFullName())
                .enabled(dto.isEnabled())
                .role(dto.getRole())
                .designation(dto.getDesignation())
                .build();
    }
}