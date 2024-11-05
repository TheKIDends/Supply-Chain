package com.supplychain.userservice.command.model;

import com.supplychain.userservice.command.data.User;
import lombok.*;

@Data
@Builder
@Getter
@Setter
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


    public static UserDTO entityToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .enabled(user.isEnabled())
                .role(user.getRole())
                .designation(user.getDesignation())
                .build();
    }
    public static User dtoToEntity(UserDTO dto){
        return User.builder()
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