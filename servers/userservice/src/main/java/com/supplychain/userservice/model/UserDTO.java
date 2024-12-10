package com.supplychain.userservice.model;

import com.supplychain.userservice.data.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private boolean enabled;
    private String role;
    private String designation;

    private String accessToken;
    private String refreshToken;

    private String address;
    private String businessLicenseNumber;
    private String managerName;

}