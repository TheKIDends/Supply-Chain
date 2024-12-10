package com.supplychain.userservice.model;

import com.supplychain.userservice.data.Customer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
//@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    public static CustomerDTO entityToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    public static Customer dtoToEntity(CustomerDTO dto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        return customer;
    }
    public static CustomerDTO convertUserDTOToCustomerDTO(UserDTO userDTO) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(userDTO, customerDTO);
        return customerDTO;
    }

}