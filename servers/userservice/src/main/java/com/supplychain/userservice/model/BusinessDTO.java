package com.supplychain.userservice.model;

import com.supplychain.userservice.data.Business;
import com.supplychain.userservice.data.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO extends UserDTO {
    private String address;
    private String businessLicenseNumber;
    private String directorName;
    private String directorIDNumber;


    public static BusinessDTO entityToDTO(Business business){
        return BusinessDTO.builder()
                .id(business.getId())
                .username(business.getUsername())
                .password(business.getPassword())
                .email(business.getEmail())
                .phoneNumber(business.getPhoneNumber())
                .fullName(business.getFullName())
                .enabled(business.isEnabled())
                .role(business.getRole())
                .designation(business.getDesignation())
                .address(business.getAddress())
                .businessLicenseNumber(business.getBusinessLicenseNumber())
                .directorName(business.getDirectorName())
                .directorIDNumber(business.getDirectorIDNumber())
                .build();
    }
    public static Business dtoToEntity(BusinessDTO dto){
        return Business.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .fullName(dto.getFullName())
                .enabled(dto.isEnabled())
                .role(dto.getRole())
                .designation(dto.getDesignation())
                .address(dto.getAddress())
                .businessLicenseNumber(dto.getBusinessLicenseNumber())
                .directorName(dto.getDirectorName())
                .directorIDNumber(dto.getDirectorIDNumber())
                .build();
    }
    public static BusinessDTO convertUserDTOToBusinessDTO(UserDTO userDTO) {
        return BusinessDTO.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .fullName(userDTO.getFullName())
                .enabled(userDTO.isEnabled())
                .role(userDTO.getRole())
                .designation(userDTO.getDesignation())
                .build();
    }

}