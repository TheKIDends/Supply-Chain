package com.supplychain.commonservice.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
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

    private String address;
    private String businessLicenseNumber;
    private String directorName;
    private String directorIDNumber;
}