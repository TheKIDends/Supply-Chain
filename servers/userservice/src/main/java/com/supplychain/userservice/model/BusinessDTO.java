package com.supplychain.userservice.model;

import com.supplychain.userservice.data.Business;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO extends UserDTO {
    private String address;
    private String businessLicenseNumber;
    private String managerName;

    public static BusinessDTO entityToDTO(Business business){
        BusinessDTO businessDTO = new BusinessDTO();
        BeanUtils.copyProperties(business, businessDTO);
        return businessDTO;
    }
    public static Business dtoToEntity(BusinessDTO dto){
        Business business = new Business();
        BeanUtils.copyProperties(dto, business);
        return business;
    }
    public static BusinessDTO convertUserDTOToBusinessDTO(UserDTO userDTO) {
        BusinessDTO businessDTO = new BusinessDTO();
        BeanUtils.copyProperties(userDTO, businessDTO);
        return businessDTO;
    }

}