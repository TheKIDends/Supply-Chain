package com.supplychain.userservice.model;

import com.supplychain.userservice.data.Admin;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
//@NoArgsConstructor
public class AdminDTO extends UserDTO {

    public static AdminDTO entityToDTO(Admin admin){
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(admin, adminDTO);
        return adminDTO;
    }
    public static Admin dtoToEntity(AdminDTO dto){
        Admin admin = new Admin();
        BeanUtils.copyProperties(dto, admin);
        return admin;
    }
    public static AdminDTO convertUserDTOToAdminDTO(UserDTO userDTO) {
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(userDTO, adminDTO);
        return adminDTO;
    }

}