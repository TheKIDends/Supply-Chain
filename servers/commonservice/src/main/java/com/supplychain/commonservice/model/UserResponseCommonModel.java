package com.supplychain.commonservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseCommonModel {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private boolean enabled;
    private String role;
    private String designation;
}