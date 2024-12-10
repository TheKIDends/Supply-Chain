package com.supplychain.userservice.model;

import com.supplychain.userservice.data.Carrier;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CarrierDTO extends UserDTO {
    private String address;
    private String businessLicenseNumber;
    private String managerName;

    public static CarrierDTO entityToDTO(Carrier carrier){
        CarrierDTO carrierDTO = new CarrierDTO();
        BeanUtils.copyProperties(carrier, carrierDTO);
        return carrierDTO;
    }
    public static Carrier dtoToEntity(CarrierDTO dto){
        Carrier carrier = new Carrier();
        BeanUtils.copyProperties(dto, carrier);
        return carrier;
    }
    public static CarrierDTO convertUserDTOToCarrierDTO(UserDTO userDTO) {
        CarrierDTO carrierDTO = new CarrierDTO();
        BeanUtils.copyProperties(userDTO, carrierDTO);
        return carrierDTO;
    }

}